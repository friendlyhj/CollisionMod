package youyihj.collision.item;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
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
import youyihj.collision.block.Booster;
import youyihj.collision.block.CollisionBlock;
import youyihj.collision.fluid.CollisionFluid;

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

    public static List<CollisionFluid> fluids = new ArrayList<>();

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
        for (CollisionItem item : items) {
            item.getModelRLs().forEach((meta, modelResourceLocation) ->
            ModelLoader.setCustomModelResourceLocation(item, meta, modelResourceLocation));
        }
        for (ItemBlock itemBlock : itemBlocks) {
            ((CollisionBlock) itemBlock.getBlock()).getModelRLs().forEach((meta, modelRL) ->
            ModelLoader.setCustomModelResourceLocation(itemBlock, meta, modelRL));
        }
        for (CollisionFluid fluid : fluids) {
            fluid.getModelRLs().forEach((meta, model) ->
            ModelLoader.setCustomStateMapper(fluid.getBlock(), new StateMapperBase() {
                @Override
                protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
                    return model;
                }
            }));
        }
    }
}
