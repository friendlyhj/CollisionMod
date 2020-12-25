package youyihj.collision.render;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * @author youyihj
 */
public interface INeedRenderUpdate {
    boolean needRenderUpdate(World world, BlockPos pos);

    boolean isDelay();
}
