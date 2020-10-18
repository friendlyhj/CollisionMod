package youyihj.collision;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;
import youyihj.collision.block.BlockRegistrar;
import youyihj.collision.block.spawner.GemSpawner;
import youyihj.collision.block.spawner.MetalSpawner;
import youyihj.collision.core.IHasGeneratedModel;
import youyihj.collision.item.ItemRegistrar;
import youyihj.collision.item.WitherAltarWand;
import youyihj.collision.recipe.ColliderRecipe;
import youyihj.collision.recipe.ColliderRecipeRegistrar;

import java.util.ArrayList;
import java.util.List;

@Mod(modid = Collision.MODID, name = Collision.NAME, version = Collision.VERSION, dependencies = Collision.DEPENDENCIES)
public class Collision
{
    public static final String MODID = "collision";
    public static final String NAME = "Collision";
    public static final String VERSION = "1.0.0";
    public static final String DEPENDENCIES = "before:crafttweaker";

    private static Logger logger;

    public static List<IHasGeneratedModel> needGenerateModels = new ArrayList<>();

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        ItemRegistrar.registerAllPlainItem();
        ItemRegistrar.registerAllSpecialItem();
        BlockRegistrar.registerAllBlock();
        needGenerateModels.forEach(IHasGeneratedModel::generate);
        new ColliderRecipeRegistrar();
    }

    @EventHandler
    public void postinit(FMLPostInitializationEvent event) {
        WitherAltarWand.initMultiBlock();
        logger.info(String.format("%s collider recipes have registered!", ColliderRecipe.colliderRecipes.size()));
        logger.info(String.format("%s nuclei registered!", MetalSpawner.initMetalList()));
        logger.info(String.format("%s spawned gem registered!", GemSpawner.initGemList()));
    }
}
