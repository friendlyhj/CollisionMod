package youyihj.collision.item;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import youyihj.collision.Collision;
import youyihj.collision.block.BlockBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author youyihj
 */
public class ItemRegistry {
    private static final DeferredRegister<Item> REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, Collision.MODID);
    private static final Map<String, ItemBase> ITEMS = new HashMap<>();
    private static final List<BlockItem> BLOCK_ITEMS = new ArrayList<>();

    public static void register(IEventBus bus) {
        InternalRegistry.registerInternal();
        REGISTER.register(bus);
    }

    public static void registerBlockItem(BlockBase blockBase, RegistryObject<Block> blockRegistryObject) {
        BLOCK_ITEMS.add(blockBase.getBlockItemSupplier().get());
        REGISTER.register(blockBase.getName(), blockBase.getBlockItemSupplier());
    }

    public static void registerItem(ItemBase itemBase) {
        ITEMS.put(itemBase.getBaseName(), itemBase);
        REGISTER.register(itemBase.getBaseName(), () -> itemBase);
    }

    public static List<BlockItem> getBlockItems() {
        return ImmutableList.copyOf(BLOCK_ITEMS);
    }

    public static Map<String, ItemBase> getItems() {
        return ImmutableMap.copyOf(ITEMS);
    }

    public static Item getItem(String name) {
        return ITEMS.get(name);
    }

    private static class InternalRegistry {
        private static void registerInternal() {
            new ItemBase("metal_chunk", new Item.Properties()).register();
            new ItemBase("mystical_gem", new Item.Properties()).register();
            new ItemBase("little_ghast_drop", new Item.Properties()).register();
            new ItemBase("up_shifter", new Item.Properties().maxStackSize(4)).register();
            new ItemBase("down_shifter", new Item.Properties().maxStackSize(4)).register();
        }
    }
}
