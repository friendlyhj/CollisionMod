package youyihj.collision.render.color;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockDisplayReader;

import javax.annotation.Nullable;

/**
 * @author youyihj
 */
public interface IBlockColorized {
    int getColor(BlockState state, @Nullable IBlockDisplayReader world, @Nullable BlockPos pos, int tintIndex);

    Block getSelf();
}
