package youyihj.collision.block;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import youyihj.collision.Collision;
import youyihj.collision.tile.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@EventBusSubscriber
public class BlockRegistryHandler {
    public static final List<Block> blocks = new ArrayList<>();
    public static final HashMap<String, CollisionBlock> blockHashMap = new HashMap<>();

    @SubscribeEvent
    public static void onRegistry(RegistryEvent.Register<Block> event) {
        IForgeRegistry<Block> registry = event.getRegistry();
        for (Block block : blocks) {
            registry.register(block);
        }
        GameRegistry.registerTileEntity(TileBooster.class, new ResourceLocation(Collision.MODID, "booster"));
        GameRegistry.registerTileEntity(TileStructureBuilder.class, new ResourceLocation(Collision.MODID, "structure_builder"));
        GameRegistry.registerTileEntity(TileNeutronStorage.class, new ResourceLocation(Collision.MODID, "neutron_storage"));
        GameRegistry.registerTileEntity(TileProtonStorage.class, new ResourceLocation(Collision.MODID, "proton_storage"));
        GameRegistry.registerTileEntity(TileHarvester.class, new ResourceLocation(Collision.MODID, "harvester"));
    }
}
