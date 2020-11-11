package youyihj.collision.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import youyihj.collision.block.absorber.Absorber;
import youyihj.collision.block.absorber.EnumAbsorber;
import youyihj.collision.recipe.ColliderRecipe;

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
            ColliderRecipe recipe = findRecipe(worldIn, pos);
            if (recipe != null) {
                BlockPos posOffset = pos;
                while (!worldIn.canSeeSky(posOffset)) {
                    posOffset = posOffset.up();
                }
                worldIn.spawnEntity(new EntityItem(worldIn, posOffset.getX(), posOffset.getY(), posOffset.getZ(), recipe.getOut()));
                clean(worldIn, pos);
            }
        }
    }

    @Nullable
    private ColliderRecipe findRecipe(World world, BlockPos pos) {
        for (ColliderRecipe colliderRecipe : ColliderRecipe.colliderRecipes) {
            if (colliderRecipe.getLevel() == level) {
                boolean flag = true;
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        BlockPos posOffset = pos.add(i - 1, 0, j - 1);
                        if ((i == 1 && j == 1) || !flag) {
                            continue;
                        }
                        EnumAbsorber absorber = colliderRecipe.getInput()[i][j];
                        IBlockState blockState = world.getBlockState(posOffset);
                        flag = (absorber == null) ? world.isAirBlock(posOffset) : blockState == absorber.getInstanceByLevel(level).getDefaultState();
                    }
                }
                if (flag) {
                    return colliderRecipe;
                }
            }
        }
        return null;
    }

    private void clean(World world, BlockPos pos) {
        Iterable<BlockPos> poses = BlockPos.getAllInBox(pos.add(-1, 0, -1), pos.add(1, 0, 1));
        poses.forEach(pos1 -> {
            Block block = world.getBlockState(pos1).getBlock();
            if (block instanceof Absorber) {
                ((Absorber) block).transform(world, pos1);
            }
        });
    }

    private boolean start(World world, BlockPos pos, BlockPos fromPos) {
        IBlockState fromBlock = world.getBlockState(fromPos);
        return (!world.isRemote && world.isBlockPowered(pos) && fromBlock.canProvidePower() && !world.isAirBlock(fromPos));
    }
}