package youyihj.collision.item;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import youyihj.collision.Configuration;
import youyihj.collision.block.absorber.EnumAbsorber;

import javax.annotation.Nonnull;

public class Storage extends CollisionItem {
    public Storage(String type) {
        super(type + "_storage");
        this.type = type;
        this.setMaxDamage(Configuration.generalConfig.storageCapacity);
        this.setNoRepair();
    }

    private String type;

    private EnumAbsorber getAbsorber() {
        return EnumAbsorber.valueOf(type.toUpperCase());
    }

    private EnumAbsorber getAbsorberEmpty() {
        return this.getAbsorber().getTransformAbsorber();
    }

    @Override
    @Nonnull
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItem(hand);
        IBlockState state = worldIn.getBlockState(pos);
        if (state == this.getAbsorber().getInstance().getDefaultState() && stack.getItemDamage() != 0) {
            if (!worldIn.isRemote) {
                stack.damageItem(-1, player);
                worldIn.setBlockState(pos, this.getAbsorberEmpty().getInstance().getDefaultState());
            }
            return EnumActionResult.SUCCESS;
        }
        if (state == this.getAbsorber().getRefined().getDefaultState() && stack.getItemDamage() >= 4) {
            if (!worldIn.isRemote) {
                stack.damageItem(-4, player);
                worldIn.setBlockState(pos, this.getAbsorberEmpty().getRefined().getDefaultState());
            }
            return EnumActionResult.SUCCESS;
        }
        if (state == this.getAbsorberEmpty().getInstance().getDefaultState() && stack.getItemDamage() != this.getMaxDamage(stack)) {
            if (!worldIn.isRemote) {
                stack.damageItem(1, player);
                worldIn.setBlockState(pos, this.getAbsorber().getInstance().getDefaultState());
            }
            return EnumActionResult.SUCCESS;
        }
        if (state == this.getAbsorberEmpty().getRefined().getDefaultState() && stack.getItemDamage() <= this.getMaxDamage(stack) - 4) {
            if (!worldIn.isRemote) {
                stack.damageItem(4, player);
                worldIn.setBlockState(pos, this.getAbsorber().getRefined().getDefaultState());
            }
            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.PASS;
    }
}
