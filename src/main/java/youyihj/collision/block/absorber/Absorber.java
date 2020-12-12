package youyihj.collision.block.absorber;

import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;
import youyihj.collision.block.BlockBase;
import youyihj.collision.config.Configuration;

import java.util.Random;

/**
 * @author youyihj
 */
public abstract class Absorber extends BlockBase {
    private final boolean isEmpty;

    public Absorber(String name, boolean isEmpty) {
        super(name, Properties.create(Material.IRON)
                .harvestTool(ToolType.PICKAXE)
                .hardnessAndResistance(3.0f, 50.0f)
                .tickRandomly()
        );

        this.isEmpty = isEmpty;
    }

    public abstract Absorber getTransformAbsorber();

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        if (this.isEmpty && random.nextInt(Configuration.absorberSpeed.get()) == 0 && work(worldIn)) {
            worldIn.setBlockState(pos, getTransformAbsorber().getDefaultState());
        }
    }

    private boolean work(World world) {
        return !Configuration.onlyWorkInDaytime.get() || world.isDaytime();
    }
}
