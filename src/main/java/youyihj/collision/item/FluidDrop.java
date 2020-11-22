package youyihj.collision.item;

import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import youyihj.collision.util.Utils;

import javax.annotation.Nonnull;

/**
 * @author youyihj
 */
public class FluidDrop extends CollisionItem {
    public FluidDrop(BlockStaticLiquid liquid) {
        super(liquid.getRegistryName().getResourcePath() + "_drop");
        this.liquid = liquid;
    }

    private final BlockStaticLiquid liquid;

    @Override
    @Nonnull
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        pos = pos.offset(facing);
        if (Utils.hasPassableBlock(worldIn, pos)) {
            if (worldIn.isRemote) {
                return EnumActionResult.SUCCESS;
            }
            player.getHeldItem(hand).shrink(1);
            worldIn.setBlockState(pos, liquid.getDefaultState());
            worldIn.neighborChanged(pos, liquid, pos);
        }
        return EnumActionResult.PASS;
    }
}
