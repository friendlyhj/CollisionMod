package youyihj.collision.item;

import net.minecraft.block.BlockBone;
import net.minecraft.entity.player.EntityPlayer;
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
import youyihj.collision.multiblock.MultiblockElement;
import youyihj.collision.multiblock.SimpleMultiblock;

import javax.annotation.Nonnull;

public class WitherAltarWand extends CollisionItem {
    public WitherAltarWand() {
        super("wither_altar_wand");
        setMaxStackSize(1);
        setFull3D();
    }

    public static SimpleMultiblock witherAltar;

    public static void initMultiBlock() {
        witherAltar = new SimpleMultiblock(state -> state == BlockRegistryHandler.blockHashMap.get("wither_altar").getDefaultState());
        MultiblockElement.IBlockMatcher boneBlock = (state -> state.getBlock() instanceof BlockBone);
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
            tag.setInteger("AltarPosX", pos.getX());
            tag.setInteger("AltarPosY", pos.getY());
            tag.setInteger("AltarPosZ", pos.getZ());
            tag.setInteger("AltarPosWorld", worldIn.provider.getDimension());
            player.sendMessage(new TextComponentTranslation("message.collision.bound_altar"));
            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.PASS;
    }
}
