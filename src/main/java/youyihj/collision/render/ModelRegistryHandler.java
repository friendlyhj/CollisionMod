package youyihj.collision.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import youyihj.collision.item.ItemNucleus;
import youyihj.collision.item.ItemRegistry;

/**
 * @author youyihj
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModelRegistryHandler {
    @SubscribeEvent
    public static void register(ModelBakeEvent event) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ItemRegistry.getItems().values().stream()
                .filter(ItemNucleus.class::isInstance)
                .map(ItemNucleus.class::cast)
                .forEach(itemNucleus -> {
                    itemRenderer.getItemModelMesher().register(itemNucleus, new ModelResourceLocation(itemNucleus.getRegistryName(), "inventory"));
                });
    }
}
