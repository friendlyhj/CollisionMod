package youyihj.collision.item;


import net.minecraft.init.Blocks;
import youyihj.collision.block.absorber.EnumAbsorber;

public class ItemRegistrar {
     static final String[] plainItemsID = {
            "metal_chunk",
            "mystical_gem",
            "little_ghast_drop",
            "up_shifter",
            "down_shifter"
    };

    public static void registerAllSpecialItem() {
        new Holder(EnumAbsorber.PROTON).register();
        new Holder(EnumAbsorber.NEUTRON).register();
        Nucleus.NUCLEUS.register();
        new Debugger().register();
        new WitherAltarWand().register();
        new FluidDrop(Blocks.WATER).register();
        new FluidDrop(Blocks.LAVA).register();
    }

    public static void registerAllPlainItem() {
       ItemMaterial.instance().register();
    }
}
