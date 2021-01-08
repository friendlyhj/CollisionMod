package youyihj.collision.data;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import youyihj.collision.Collision;
import youyihj.collision.block.BlockRegistry;
import youyihj.collision.item.ItemRegistry;
import youyihj.collision.util.annotation.AnnotationUtil;
import youyihj.collision.util.annotation.DisableModelGenerator;

/**
 * @author youyihj
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    private static final ResourceLocation ITEM_GENERATE = new ResourceLocation("item/generated");

    @SubscribeEvent
    public static void genData(GatherDataEvent event) {
        if (event.includeServer() || event.includeDev()) {
            event.getGenerator().addProvider(new BlockLootProvider(event.getGenerator()));
        }
        if (event.includeClient()) {
            event.getGenerator().addProvider(new BlockStateProvider(event.getGenerator(), Collision.MODID, event.getExistingFileHelper()) {
                @Override
                protected void registerStatesAndModels() {
                    BlockRegistry.getBlocks().values().stream().filter(blockBase -> AnnotationUtil.hasNotAnnotation(blockBase, DisableModelGenerator.class)).forEach(this::simpleBlock);
                }
            });
            event.getGenerator().addProvider(new ItemModelProvider(event.getGenerator(), Collision.MODID, event.getExistingFileHelper()) {
                @Override
                protected void registerModels() {
                    ItemRegistry.getBlockItems().forEach((blockItem -> {
                        String name = blockItem.getBlock().getRegistryName().getPath();
                        withExistingParent(name, new ResourceLocation(Collision.MODID, "block/" + name));
                    }));
                    ItemRegistry.getItems().values().stream().filter(itemBase -> AnnotationUtil.hasNotAnnotation(itemBase, DisableModelGenerator.class)).forEach(item -> {
                        String name = item.getRegistryName().getPath();
                        withExistingParent(name, ITEM_GENERATE).texture("layer0", "item/" + item.getTexturePath());
                    });
                }
            });
        }
    }
}
