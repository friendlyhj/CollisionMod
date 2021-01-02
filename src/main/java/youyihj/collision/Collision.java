package youyihj.collision;

import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RecipesUpdatedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import youyihj.collision.block.BlockNeutronStorage;
import youyihj.collision.block.BlockProtonStorage;
import youyihj.collision.block.BlockRegistry;
import youyihj.collision.block.BlockStructureBuilder;
import youyihj.collision.config.Configuration;
import youyihj.collision.item.ItemRegistry;
import youyihj.collision.recipe.NucleusSmeltingHandler;
import youyihj.collision.tile.*;

import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Collision.MODID)
public class Collision {

    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "collision";

    public Collision() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Configuration.config);
        eventBus.addListener(this::setup);
        eventBus.addListener(this::enqueueIMC);
        eventBus.addListener(this::processIMC);
        eventBus.addListener(this::doClientStuff);

        ItemRegistry.register(eventBus);
        BlockRegistry.register(eventBus);
        TileEntityRegistry.register(eventBus);
        ContainerRegistry.register(eventBus);
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        TileGemSpawner.initGemList();
        TileMetalSpawner.initMetalList();
    }

    @SuppressWarnings("unchecked")
    private void doClientStuff(final FMLClientSetupEvent event) {
        ScreenManager.registerFactory(((ContainerType<ContainerHarvester>) ContainerRegistry.getContainerType("harvester")), ScreenHarvester::new);
        ScreenManager.registerFactory(((ContainerType<ContainerNeutronStorage>) ContainerRegistry.getContainerType(BlockNeutronStorage.NAME)), ScreenNeutronStorage::new);
        ScreenManager.registerFactory(((ContainerType<ContainerProtonStorage>) ContainerRegistry.getContainerType(BlockProtonStorage.NAME)), ScreenProtonStorage::new);
        ScreenManager.registerFactory(((ContainerType<ContainerStructureBuilder>) ContainerRegistry.getContainerType(BlockStructureBuilder.NAME)), ScreenStructureBuilder::new);
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
    }

    private void processIMC(final InterModProcessEvent event) {
        // some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m -> m.getMessageSupplier().get()).
                collect(Collectors.toList()));
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        new NucleusSmeltingHandler(event.getServer().getRecipeManager()).register();
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void onRecipeUpdate(RecipesUpdatedEvent event) {
        new NucleusSmeltingHandler(event.getRecipeManager()).register();
    }
}
