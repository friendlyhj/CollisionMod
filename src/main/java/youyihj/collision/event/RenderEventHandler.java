package youyihj.collision.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import youyihj.collision.block.Booster;
import youyihj.collision.tile.TileBooster;

import java.util.Map;

@Mod.EventBusSubscriber
@SideOnly(Side.CLIENT)
public class RenderEventHandler {
    @SubscribeEvent
    public static void render(TickEvent.RenderTickEvent event) {
        if (Booster.doRerender && event.phase.equals(TickEvent.Phase.START)) {
            for (Map.Entry<BlockPos, Boolean> entry : Booster.rerenderMap.entrySet()) {
                BlockPos pos = entry.getKey();
                boolean aBoolean = entry.getValue();
                Minecraft mc = Minecraft.getMinecraft();
                WorldClient world = mc.world;
                if (aBoolean) {
                    TileEntity tileEntity = world.getTileEntity(pos);
                    if (tileEntity != null && ((TileBooster) tileEntity).isFull()) {
                        mc.renderGlobal.markBlockRangeForRenderUpdate(pos.getX(), pos.getY(), pos.getZ(), pos.getX(), pos.getY(), pos.getZ());
                        Booster.rerenderMap.put(pos, false);
                    } else {
                        return;
                    }
                }
            }
            Booster.doRerender = false;
        }
    }
}
