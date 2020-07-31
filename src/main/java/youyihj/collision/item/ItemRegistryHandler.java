package youyihj.collision.item;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@EventBusSubscriber
@SuppressWarnings("unused")
public class ItemRegistryHandler {

    public static List<CollisionItem> items = new ArrayList<>();
    public static HashMap<String, CollisionItem> itemHashMap = new HashMap<>();

    public static List<ItemBlock> itemBlocks = new ArrayList<>();
    public static HashMap<String, ItemBlock> itemBlockHashMap = new HashMap<>();

    @SubscribeEvent
    public static void onRegistry(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();
        for (Item item : items) {
            registry.register(item);
        }
        for (ItemBlock itemBlock : itemBlocks) {
            registry.register(itemBlock);
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onModelRegistry(ModelRegistryEvent event) {
        for (Item item : items) {
            ModelLoader.setCustomModelResourceLocation(item, 0,
                    new ModelResourceLocation(item.getRegistryName(), "inventory"));
        }
        for (ItemBlock itemBlock : itemBlocks) {
            ModelLoader.setCustomModelResourceLocation(itemBlock, 0,
                    new ModelResourceLocation(itemBlock.getRegistryName(), "inventory"));
        }
    }
}
