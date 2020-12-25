package youyihj.collision.render;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author youyihj
 */
@Mod.EventBusSubscriber
@OnlyIn(Dist.CLIENT)
public class RenderUpdateHandler {
    private static final LinkedList<BlockPos> renderPoses = new LinkedList<>();
    private static final List<BlockPos> failPoses = new ArrayList<>();

    public static void mark(BlockPos pos) {
        renderPoses.add(pos);
    }

    @SubscribeEvent
    public static void handle(TickEvent.RenderTickEvent event) {
        ClientWorld world = Minecraft.getInstance().world;
        if (world == null)
            return;
        if (event.phase.equals(TickEvent.Phase.START)) {
            while (!renderPoses.isEmpty()) {
                BlockPos pos = renderPoses.poll();
                Block block = world.getBlockState(pos).getBlock();
                if (block instanceof INeedRenderUpdate) {
                    INeedRenderUpdate block1 = (INeedRenderUpdate) block;
                    if (block1.needRenderUpdate(world, pos)) {
                        Minecraft.getInstance().worldRenderer.markBlockRangeForRenderUpdate(pos.getX(), pos.getY(), pos.getZ(), pos.getX(), pos.getY(), pos.getZ());
                    } else if (block1.isDelay()) {
                        failPoses.add(pos);
                    }
                }
            }
            renderPoses.addAll(failPoses);
            failPoses.clear();
        }
    }
}
