package youyihj.collision.event;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import youyihj.collision.util.INeedRenderUpdate;

import java.util.LinkedList;

@Mod.EventBusSubscriber
@SideOnly(Side.CLIENT)
public class RenderEventHandler {
    private static final LinkedList<BlockPos> renderPoses = new LinkedList<>();

    public static void mark(BlockPos pos) {
        renderPoses.addFirst(pos);
    }

    @SubscribeEvent
    public static void render(TickEvent.RenderTickEvent event) {
        if (event.phase.equals(TickEvent.Phase.START)) {
            LinkedList<BlockPos> failPoses = new LinkedList<>();
            while (!renderPoses.isEmpty()) {
                BlockPos pos = renderPoses.poll();
                WorldClient world = Minecraft.getMinecraft().world;
                Block block = world.getBlockState(pos).getBlock();
                if (block instanceof INeedRenderUpdate) {
                    INeedRenderUpdate block1 = (INeedRenderUpdate) block;
                    if (block1.needRenderUpdate(world, pos)) {
                        world.markBlockRangeForRenderUpdate(pos, pos);
                    } else if (block1.isDelay()) {
                        failPoses.addLast(pos);
                    }
                }
            }
            renderPoses.addAll(failPoses);
            failPoses.clear();
        }
    }
}
