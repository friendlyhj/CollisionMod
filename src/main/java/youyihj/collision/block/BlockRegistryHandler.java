package youyihj.collision.block;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import youyihj.collision.core.Collision;
import youyihj.collision.tile.TileBooster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@EventBusSubscriber
@SuppressWarnings("unused")
public class BlockRegistryHandler {
    public static List<Block> blocks = new ArrayList<>();
    public static HashMap<String, CollisionBlock> blockHashMap = new HashMap<>();

    @SubscribeEvent
    public static void onRegistry(RegistryEvent.Register<Block> event) {
        IForgeRegistry<Block> registry = event.getRegistry();
        for (Block block : blocks) {
            registry.register(block);
        }
        GameRegistry.registerTileEntity(TileBooster.class, new ResourceLocation(Collision.MODID, "booster"));
    }
}
