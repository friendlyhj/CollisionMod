package youyihj.collision;

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
import youyihj.collision.recipe.ColliderRecipeManager;
import youyihj.collision.recipe.ColliderRecipeRegistrar;
import youyihj.collision.recipe.FurnaceRecipeHandler;

@Mod(modid = Collision.MODID, name = Collision.NAME, version = Collision.VERSION, dependencies = Collision.DEPENDENCIES)
public class Collision {
    public static final String MODID = "collision";
    public static final String NAME = "Collision";
    public static final String VERSION = "1.0.6";
    public static final String DEPENDENCIES = "after:crafttweaker";

    private static Logger logger;
    private static boolean disableModelGenerator;

    @EventHandler
    public void onConstruct(FMLConstructionEvent event) {
        // FluidRegistry.enableUniversalBucket();
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        checkCompatCrTVersion();
        ItemRegistrar.registerAllPlainItem();
        ItemRegistrar.registerAllSpecialItem();
        BlockRegistrar.registerAllBlock();
        FluidRegistrar.registerAll();
        NetworkRegistryHandler.register();
        if (!disableModelGenerator) {
            ModelGenerator.generate();
        }
        new ColliderRecipeRegistrar();
        FMLInterModComms.sendFunctionMessage("theoneprobe", "getTheOneProbe", "youyihj.collision.compat.theoneprobe.TOPCompatHandler");
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        WitherAltarWand.initMultiBlock();
        FurnaceRecipeHandler.registerNucleusRecipe();
        logger.info(String.format("%s collider recipes have registered!", ColliderRecipeManager.getColliderRecipes().size()));
        logger.info(String.format("%s nuclei registered!", MetalSpawner.initMetalList()));
        logger.info(String.format("%s spawned gem registered!", GemSpawner.initGemList()));
    }

    public static void disableModelGenerator() {
        Collision.disableModelGenerator = true;
    }

    private static void checkCompatCrTVersion() {
        try {
            Class<?> blockDefClass = Class.forName("crafttweaker.api.block.IBlockDefinition");
            blockDefClass.getMethod("getStateFromMeta", int.class);
        } catch (ClassNotFoundException e) {
            logger.info("Not install CraftTweaker, Skip checking");
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("The version of CraftTweaker must be 4.1.20.582 or above");
        }
    }
}
