package youyihj.collision.render.color;

import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import youyihj.collision.block.BlockBase;
import youyihj.collision.block.BlockRegistry;
import youyihj.collision.item.ItemRegistry;

/**
 * @author youyihj
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ColorManager {
    @SubscribeEvent
    public static void itemColors(ColorHandlerEvent.Item event) {
        ItemColors colors = event.getItemColors();
        ItemRegistry.getItems().values().stream()
                .filter(itemBase -> itemBase instanceof IItemColorized)
                .map(itemBase -> (IItemColorized) itemBase)
                .forEach(coloredItem -> colors.register(coloredItem::getColor, coloredItem));
    }

    @SubscribeEvent
    public static void blockColors(ColorHandlerEvent.Block event) {
        BlockColors colors = event.getBlockColors();
        for (BlockBase blockBase : BlockRegistry.getBlocks().values()) {
            if (blockBase instanceof IBlockColorized) {
                IBlockColorized coloredBlock = (IBlockColorized) blockBase;
                colors.register(coloredBlock::getColor, blockBase);
            }
        }
    }
}
