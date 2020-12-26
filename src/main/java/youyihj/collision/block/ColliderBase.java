package youyihj.collision.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import youyihj.collision.recipe.ColliderRecipe;
import youyihj.collision.util.Utils;

import javax.annotation.Nullable;

/**
 * @author youyihj
 */
public class ColliderBase extends BlockBase {
    private final int level;

    public ColliderBase(int level) {
        super(getRegistryName(level), Properties.create(Material.IRON)
                .hardnessAndResistance(3.0f, 50.0f));
        this.level = level;
    }

    public static String getRegistryName(int level) {
        return "collider_lv" + level;
    }

    @Override
    public boolean canConnectRedstone(BlockState state, IBlockReader world, BlockPos pos, @Nullable Direction side) {
        return side != null;
    }

    @Override
    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        if (worldIn instanceof ServerWorld) {
            ServerWorld world = (ServerWorld) worldIn;
            if (start(world, pos, fromPos)) {
                world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 0.1f, false, Explosion.Mode.NONE);
                ColliderRecipe recipe = world.getRecipeManager().getRecipes()
                        .stream()
                        .filter(iRecipe -> iRecipe instanceof ColliderRecipe)
                        .map(iRecipe -> (ColliderRecipe) iRecipe)
                        .filter(recipe1 -> recipe1.getLevel() == level && recipe1.matches(world, pos))
                        .findFirst().orElse(null);
                if (recipe != null) {
                    convert(world, pos, recipe);
                    BlockPos posOffset = pos;
                    while (!world.isAirBlock(posOffset)) {
                        posOffset = posOffset.up();
                    }
                    Utils.spawnEntityItem(world, posOffset, recipe.getRecipeOutput());
                }
            }
        }
    }

    private void convert(World world, BlockPos pos, ColliderRecipe recipe) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                BlockPos posOffset = pos.add(i - 1, 0, j - 1);
                if ((i == 1 && j == 1) || world.isAirBlock(posOffset)) {
                    continue;
                }
                recipe.getIn()[i][j].getAbsorberByLevel(recipe.getLevel()).transform(world, posOffset);
            }
        }
    }

    private boolean start(World world, BlockPos pos, BlockPos fromPos) {
        BlockState fromBlock = world.getBlockState(fromPos);
        return (!world.isRemote && world.isBlockPowered(pos) && fromBlock.canProvidePower() && !world.isAirBlock(fromPos));
    }
}
