package youyihj.collision;

import com.teamacronymcoders.base.BaseModFoundation;
import com.teamacronymcoders.base.materialsystem.MaterialUser;
import com.teamacronymcoders.base.subblocksystem.SubBlockSystem;
import com.teamacronymcoders.base.util.OreDictUtils;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;
import youyihj.collision.block.BlockRegistrar;
import youyihj.collision.block.spawner.MetalSpawner;
import youyihj.collision.config.NucleusDataReader;
import youyihj.collision.creativeTab.CollisionTab;
import youyihj.collision.item.ItemRegistrar;
import youyihj.collision.item.Nucleus;
import youyihj.collision.recipe.ColliderRecipe;
import youyihj.collision.recipe.ColliderRecipeRegistrar;


@Mod(modid = Collision.MODID, name = Collision.NAME, version = Collision.VERSION, dependencies = Collision.DEPENDENCIES)
public class Collision extends BaseModFoundation<Collision> {
    public static final String MODID = "collision";
    public static final String NAME = "Collision";
    public static final String VERSION = "1.0.0";
    public static final String DEPENDENCIES = "required-after:base@[3.13.0,);required-after:jei@[4.15.0.296,)";

    private static Logger logger;

    @Mod.Instance(MODID)
    public static Collision instance;

    public Collision() {
        super(MODID, NAME, VERSION, CollisionTab.COLLISION_TAB, false);
        this.materialUser = new MaterialUser(this);
        this.subBlockSystem = new SubBlockSystem(this);
        OreDictUtils.addDefaultModId(MODID);
    }

    @EventHandler
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        super.preInit(event);
        logger.info(String.format("%s nuclei registered!", NucleusDataReader.readNucleusData()));
        ItemRegistrar.registerAllPlainItem();
        ItemRegistrar.registerAllSpecialItem();
        BlockRegistrar.registerAllBlock();
        Nucleus.register();
    }

    @EventHandler
    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }

    @EventHandler
    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
        ColliderRecipeRegistrar.registerAll();
        logger.info(String.format("%s collider recipes have registered!", ColliderRecipe.colliderRecipes.size()));
        logger.info(String.format("%s nuclei have registered to metal spawner!", MetalSpawner.initMetalList()));
    }

    @Override
    public Collision getInstance() {
        return this;
    }
}
