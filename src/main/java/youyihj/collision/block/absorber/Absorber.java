package youyihj.collision.block.absorber;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import youyihj.collision.block.CollisionBlock;
import youyihj.collision.config.AbsorberConfig;

import java.util.Random;

public abstract class Absorber extends CollisionBlock {
    public Absorber(String id, boolean isEmpty) {
        super(id, Material.ROCK);
        this.setTickRandomly(isEmpty);
        this.setHardness(3.0f);
        this.setResistance(50.0f);
        this.setHarvestLevel("pickaxe", 0);
    }
    public abstract Absorber getTransformAbsorber();

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random random) {
        if (!worldIn.isRemote && worldIn.canSeeSky(pos.up()) && random.nextInt() % AbsorberConfig.absorberSpeed == 0 && work(worldIn)) {
            worldIn.setBlockState(pos, this.getTransformAbsorber().getDefaultState());
        }
    }

    private boolean work(World world) {
        return !AbsorberConfig.onlyWorkInDayTime || world.isDaytime();
    }
}
