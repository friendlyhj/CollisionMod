package youyihj.collision.color;

import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import youyihj.collision.block.BlockRegistry;
import youyihj.collision.item.ItemRegistry;

/**
 * @author youyihj
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
@OnlyIn(Dist.CLIENT)
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
        BlockRegistry.getBlocks().values().stream()
                .filter(blockBase -> blockBase instanceof IBlockColorized)
                .map(blockBase -> (IBlockColorized) blockBase)
                .forEach(coloredBlock -> colors.register(coloredBlock::getColor, coloredBlock.getSelf()));
    }
}
