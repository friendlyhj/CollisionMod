package youyihj.collision.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import youyihj.collision.recipe.ColliderRecipeManager;
import youyihj.collision.recipe.CustomColliderRecipe;

import javax.annotation.Nullable;

public class ColliderBase extends CollisionBlock {
    private final int level;
    public ColliderBase(int level) {
        super(getRegistryName(level), Material.IRON);
        this.level = level;
        this.setHardness(3.0f);
        this.setResistance(50.0f);
    }

    public static String getRegistryName(int level) {
        return "collider_lv" + level;
    }

    @Override
    public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable EnumFacing side) {
        return side != null;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (start(worldIn, pos, fromPos)) {
            worldIn.newExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 0.1f, false, true);
            CustomColliderRecipe recipe = findRecipe(worldIn, pos);
            if (recipe != null) {
                BlockPos posOffset = pos;
                while (!worldIn.canSeeSky(posOffset)) {
                    posOffset = posOffset.up();
                }
                if (worldIn.rand.nextInt(100) < recipe.getSuccessChance()) {
                    worldIn.spawnEntity(new EntityItem(worldIn, posOffset.getX(), posOffset.getY(), posOffset.getZ(), recipe.getOut().copy()));
                }
                if (worldIn.rand.nextInt(100) < recipe.getConversionChance()) {
                    clean(worldIn, pos, recipe);
                }
            }
        }
    }

    @Nullable
    private CustomColliderRecipe findRecipe(World world, BlockPos pos) {
        for (CustomColliderRecipe colliderRecipe : ColliderRecipeManager.getColliderRecipes()) {
            if (colliderRecipe.getLevel() == level) {
                boolean flag = true;
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        BlockPos posOffset = pos.add(i - 1, 0, j - 1);
                        if ((i == 1 && j == 1) || !flag) {
                            continue;
                        }
                        flag = colliderRecipe.getBlocks()[i][j].test(world.getBlockState(posOffset));
                    }
                }
                if (flag) {
                    return colliderRecipe;
                }
            }
        }
        return null;
    }

    private void clean(World world, BlockPos pos, CustomColliderRecipe recipe) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i == 1 && j == 1) {
                    continue;
                }
                world.setBlockState(pos.add(i - 1, 0, j - 1), recipe.getConversionBlocks()[i][j]);
            }
        }
    }

    private boolean start(World world, BlockPos pos, BlockPos fromPos) {
        IBlockState fromBlock = world.getBlockState(fromPos);
        return (!world.isRemote && world.isBlockPowered(pos) && fromBlock.canProvidePower() && !world.isAirBlock(fromPos));
    }
}