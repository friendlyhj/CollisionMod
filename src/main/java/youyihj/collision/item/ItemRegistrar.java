package youyihj.collision.item;


import youyihj.collision.block.absorber.EnumAbsorber;

public class ItemRegistrar {
    private static String[] plainItemsID = {
            "metal_chunk",
            "mystical_gem",
            "little_ghast_drop"
    };

    public static void registerAllSpecialItem() {
        new Holder(EnumAbsorber.PROTON).register();
        new Holder(EnumAbsorber.NEUTRON).register();
        Nucleus.NUCLEUS.register();
        new Debugger().register();
        new WitherAltarWand().register();
    }

    public static void registerAllPlainItem() {
        new ItemMaterial(plainItemsID).register();
    }
}
