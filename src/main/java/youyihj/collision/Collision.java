package youyihj.collision;

import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;
import youyihj.collision.block.BlockRegistrar;
import youyihj.collision.block.spawner.GemSpawner;
import youyihj.collision.block.spawner.MetalSpawner;
import youyihj.collision.fluid.FluidRegistrar;
import youyihj.collision.item.ItemRegistrar;
import youyihj.collision.item.WitherAltarWand;
import youyihj.collision.model.ModelGenerator;
import youyihj.collision.network.NetworkRegistryHandler;
import youyihj.collision.recipe.ColliderRecipe;
import youyihj.collision.recipe.ColliderRecipeRegistrar;

@Mod(modid = Collision.MODID, name = Collision.NAME, version = Collision.VERSION, dependencies = Collision.DEPENDENCIES)
public class Collision {
    public static final String MODID = "collision";
    public static final String NAME = "Collision";
    public static final String VERSION = "1.0.0";
    public static final String DEPENDENCIES = "before:crafttweaker";

    private static Logger logger;

    @EventHandler
    public void onConstruct(FMLConstructionEvent event) {
        FluidRegistry.enableUniversalBucket();
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        ItemRegistrar.registerAllPlainItem();
        ItemRegistrar.registerAllSpecialItem();
        BlockRegistrar.registerAllBlock();
        FluidRegistrar.registerAll();
        NetworkRegistryHandler.register();
        ModelGenerator.generate();
        new ColliderRecipeRegistrar();
        FMLInterModComms.sendFunctionMessage("theoneprobe", "getTheOneProbe", "youyihj.collision.compat.theoneprobe.TOPCompatHandler");
    }

    @EventHandler
    public void postinit(FMLPostInitializationEvent event) {
        WitherAltarWand.initMultiBlock();
        logger.info(String.format("%s collider recipes have registered!", ColliderRecipe.colliderRecipes.size()));
        logger.info(String.format("%s nuclei registered!", MetalSpawner.initMetalList()));
        logger.info(String.format("%s spawned gem registered!", GemSpawner.initGemList()));
    }
}
