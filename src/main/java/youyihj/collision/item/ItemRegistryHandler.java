package youyihj.collision.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@EventBusSubscriber
public class ItemRegistryHandler {

    public static final List<CollisionItem> items = new ArrayList<>();
    public static final HashMap<String, CollisionItem> itemHashMap = new HashMap<>();

    public static final List<ItemBlock> itemBlocks = new ArrayList<>();
    public static final HashMap<String, ItemBlock> itemBlockHashMap = new HashMap<>();

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
}
