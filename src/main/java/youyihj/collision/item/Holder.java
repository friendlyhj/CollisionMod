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

public class Holder extends CollisionItem {
    public Holder(EnumAbsorber absorber) {
        super(absorber.name().toLowerCase() + "_holder");
        this.type = absorber;
        this.setMaxDamage(100);
        this.setNoRepair();
    }

    private final EnumAbsorber type;

    private IBlockState getAbsorber() {
        return this.type.getInstance().getDefaultState();
    }

    private IBlockState getAbsorberEmpty() {
        return this.type.getTransformAbsorber().getInstance().getDefaultState();
    }

    private IBlockState getAbsorberRefined() {
        return this.type.getRefined().getDefaultState();
    }

    private IBlockState getAbsorberEmptyRefined() {
        return this.type.getTransformAbsorber().getRefined().getDefaultState();
    }

    @Override
    @Nonnull
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItem(hand);
        IBlockState state = worldIn.getBlockState(pos);
        if (state == this.getAbsorber() && stack.getItemDamage() != 0) {
            if (!worldIn.isRemote) {
                stack.damageItem(-1, player);
                worldIn.setBlockState(pos, this.getAbsorberEmpty());
            }
            return EnumActionResult.SUCCESS;
        }
        if (state == this.getAbsorberRefined() && stack.getItemDamage() >= 4) {
            if (!worldIn.isRemote) {
                stack.damageItem(-4, player);
                worldIn.setBlockState(pos, this.getAbsorberEmptyRefined());
            }
            return EnumActionResult.SUCCESS;
        }
        if (state == this.getAbsorberEmpty() && stack.getItemDamage() != this.getMaxDamage(stack)) {
            if (!worldIn.isRemote) {
                stack.damageItem(1, player);
                worldIn.setBlockState(pos, this.getAbsorber());
            }
            return EnumActionResult.SUCCESS;
        }
        if (state == this.getAbsorberEmptyRefined() && stack.getItemDamage() <= this.getMaxDamage(stack) - 4) {
            if (!worldIn.isRemote) {
                stack.damageItem(4, player);
                worldIn.setBlockState(pos, this.getAbsorberRefined());
            }
            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.PASS;
    }
}
