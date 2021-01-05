package youyihj.collision.render;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

/**
 * @author youyihj
 */
public interface INeedRenderUpdate {
    boolean needRenderUpdate(IWorldReader world, BlockPos pos);

    boolean isDelay();
}
