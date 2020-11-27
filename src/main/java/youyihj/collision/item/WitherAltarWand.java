package youyihj.collision.item;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import youyihj.collision.block.BlockRegistryHandler;
import youyihj.collision.multiblock.SimpleMultiblock;
import youyihj.collision.util.IBlockMatcher;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class WitherAltarWand extends CollisionItem {
    public static final String TAG_X = "AltarPosX";
    public static final String TAG_Y = "AltarPosY";
    public static final String TAG_Z = "AltarPosZ";
    public static final String TAG_WORLD = "AltarPosWorld";

    public WitherAltarWand() {
        super("wither_altar_wand");
        setMaxStackSize(1);
        setFull3D();
    }

    public static SimpleMultiblock witherAltar;

    public static void initMultiBlock() {
        witherAltar = new SimpleMultiblock(IBlockMatcher.Impl.fromBlock(BlockRegistryHandler.blockHashMap.get("wither_altar")));
        IBlockMatcher boneBlock = IBlockMatcher.Impl.fromBlock(Blocks.BONE_BLOCK);
        witherAltar.addElement(new Vec3i(1, 0, 0), boneBlock)
                .addElement(new Vec3i(-1, 0, 0), boneBlock)
                .addElement(new Vec3i(0, 0, 1), boneBlock)
                .addElement(new Vec3i(0, 0, -1), boneBlock);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                witherAltar.addElement(new Vec3i(i - 1, -1, j - 1), boneBlock);
            }
        }
    }

    @Override
    @Nonnull
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote && witherAltar.match(worldIn, pos) && hand.equals(EnumHand.MAIN_HAND)) {
            ItemStack wand = player.getHeldItem(hand);
            NBTTagCompound tag = wand.getTagCompound();
            if (tag == null) {
                wand.setTagCompound(new NBTTagCompound());
                tag = wand.getTagCompound();
            }
            tag.setInteger(TAG_X, pos.getX());
            tag.setInteger(TAG_Y, pos.getY());
            tag.setInteger(TAG_Z, pos.getZ());
            tag.setInteger(TAG_WORLD, worldIn.provider.getDimension());
            player.sendMessage(new TextComponentTranslation("message.collision.bound_altar"));
            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.PASS;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        NBTTagCompound nbt = stack.getTagCompound();
        if (nbt != null && nbt.hasKey(TAG_X) && nbt.hasKey(TAG_Y) && nbt.hasKey(TAG_Z) && nbt.hasKey(TAG_WORLD)) {
            tooltip.add(I18n.format("tooltip.collision.wither_altar_bound",
                    nbt.getInteger(TAG_X),
                    nbt.getInteger(TAG_Y),
                    nbt.getInteger(TAG_Z),
                    nbt.getInteger(TAG_WORLD)));
        } else {
            tooltip.add(I18n.format("tooltip.collision.wither_altar_no_bound"));
        }
    }
}
