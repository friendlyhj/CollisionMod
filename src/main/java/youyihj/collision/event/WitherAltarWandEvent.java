package youyihj.collision.event;

import net.minecraft.block.BlockSkull;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import youyihj.collision.item.WitherAltarWand;

import static youyihj.collision.item.WitherAltarWand.witherAltar;

@Mod.EventBusSubscriber
@SuppressWarnings("unused")
public class WitherAltarWandEvent {
    @SubscribeEvent
    public static void handle(AttackEntityEvent event) {
        EntityPlayer player = event.getEntityPlayer();
        Entity target = event.getTarget();
        World playerWorld = player.world;
        ItemStack item = player.getHeldItem(EnumHand.MAIN_HAND);
        if (!playerWorld.isRemote && item.getItem() instanceof WitherAltarWand && target instanceof EntityLivingBase && !(target instanceof EntityPlayer)) {
            EntityLivingBase mob = (EntityLivingBase) target;
            mob.attackEntityFrom(DamageSource.causePlayerDamage(player), 4.0f);
            if (mob.getHealth() <= 0.0f && item.hasTagCompound()) {
                NBTTagCompound tag = item.getTagCompound();
                BlockPos pos = new BlockPos(tag.getInteger("AltarPosX"), tag.getInteger("AltarPosY"), tag.getInteger("AltarPosZ"));
                World affectWorld = DimensionManager.getWorld(tag.getInteger("AltarWorldID"));
                if (affectWorld.isBlockLoaded(pos) && witherAltar.match(affectWorld, pos)) {
                    affectWorld.newExplosion(null, pos.getX() + 0.5f, pos.getY(), pos.getZ() + 0.5f, 0.5f, true, false);
                    witherAltar.getMultiblockElements().forEach(element -> affectWorld.setBlockToAir(pos.add(element.getOffset())));
                    transformSkull(affectWorld, pos);
                }
            }
        }
    }

    private static void transformSkull(World world, BlockPos corePos) {
        int n = 0;
        for (BlockPos pos : BlockPos.getAllInBox(corePos.add(-2, -1, -2), corePos.add(2, -1, 2))) {
            if (world.getBlockState(pos).getBlock() instanceof BlockSkull) {
                n++;
                world.setBlockToAir(pos);
            }
        }
        world.spawnEntity(new EntityItem(world, corePos.getX(), corePos.getY(), corePos.getZ(), new ItemStack(Items.SKULL, n, 1)));
    }
}
