package youyihj.collision.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SkullBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import youyihj.collision.Collision;
import youyihj.collision.config.Configuration;
import youyihj.collision.multiblock.SimpleMultiblock;
import youyihj.collision.util.Utils;

import java.util.Optional;
import java.util.function.Predicate;

/**
 * @author youyihj
 */
public class ItemWitherAltarWand extends ItemBase {
    public ItemWitherAltarWand() {
        super("wither_altar_wand", new Properties());
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "wither_altar", Configuration.witherAltarWandAttackDamage.get(), AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "wither_altar", Configuration.witherAltarWandAttackSpeed.get(), AttributeModifier.Operation.ADDITION));
        attributes = builder.build();
        Predicate<BlockState> boneBlock = state -> state.getBlock() == Blocks.BONE_BLOCK;
        witherAltar = new SimpleMultiblock(this::isWitherAltar)
                .addElement(new Vector3i(1, 0, 1), boneBlock)
                .addElement(new Vector3i(-1, 0, 1), boneBlock)
                .addElement(new Vector3i(1, 0, -1), boneBlock)
                .addElement(new Vector3i(-1, 0, -1), boneBlock);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                witherAltar.addElement(new Vector3i(i - 1, -1, j - 1), boneBlock);
            }
        }
    }

    public static final ItemWitherAltarWand INSTANCE = new ItemWitherAltarWand();
    private static final String TAG_X = "altarPosX";
    private static final String TAG_Y = "altarPosY";
    private static final String TAG_Z = "altarPosZ";
    private static final String TAG_WORLD = "altarPosWorld";
    private final Multimap<Attribute, AttributeModifier> attributes;
    private final SimpleMultiblock witherAltar;

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (target.getShouldBeDead() && stack.hasTag() && target.getEntityWorld() instanceof ServerWorld) {
            MinecraftServer server = ((ServerWorld) target.getEntityWorld()).getServer();
            CompoundNBT nbt = stack.getTag();
            String altarWorldID = nbt.getString(TAG_WORLD);
            if (!altarWorldID.isEmpty()) {
                BlockPos posAltar = new BlockPos(nbt.getInt(TAG_X), nbt.getInt(TAG_Y), nbt.getInt(TAG_Z));
                Optional.ofNullable(server.getWorld(RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation(altarWorldID))))
                        .ifPresent(world -> {
                            if (world.isAreaLoaded(posAltar, 4) && witherAltar.match(world, posAltar)) {
                                transformSkull(world, posAltar);
                                world.createExplosion(null, posAltar.getX() + 0.5f, posAltar.getY(), posAltar.getZ() + 0.5f, 0.5f, Explosion.Mode.NONE);
                                witherAltar.getMultiblockElements().forEach(element -> world.setBlockState(posAltar.add(element.getOffset()), Blocks.AIR.getDefaultState()));
                            }
                        });

            }
        }
        return true;
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        ItemStack stack = context.getItem();
        World world = context.getWorld();
        BlockPos pos = context.getPos();
        if (isWitherAltar(world.getBlockState(pos))) {
            if (world.isRemote)
                return ActionResultType.SUCCESS;
            CompoundNBT nbt = stack.getOrCreateTag();
            nbt.putInt(TAG_X, pos.getX());
            nbt.putInt(TAG_Y, pos.getY());
            nbt.putInt(TAG_Z, pos.getZ());
            nbt.putString(TAG_WORLD, world.getDimensionKey().toString());
            Optional.ofNullable(context.getPlayer()).ifPresent(playerEntity ->
                    playerEntity.sendStatusMessage(new TranslationTextComponent("message.collision.bound_altar"), true));
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.PASS;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) {
        return slot == EquipmentSlotType.MAINHAND ? attributes : ImmutableMultimap.of();
    }

    private static void transformSkull(ServerWorld world, BlockPos corePos) {
        int n = 0;
        for (BlockPos pos : BlockPos.getAllInBoxMutable(corePos.add(-2, -1, -2), corePos.add(2, -1, 2))) {
            if (world.getBlockState(pos).getBlock() instanceof SkullBlock) {
                n++;
                world.setBlockState(pos, Blocks.AIR.getDefaultState());
            }
        }
        Utils.spawnEntityItem(world, corePos, new ItemStack(Blocks.WITHER_SKELETON_SKULL, n));
    }

    private boolean isWitherAltar(BlockState state) {
        return state.getBlock().getRegistryName().equals(Collision.rl("wither_altar"));
    }
}
