package youyihj.collision.item;


public class ItemRegistrar {
    private static String[] plainItemsID = {
            "metal_chunk",
            "mystical_gem",
            "little_ghast_drop"
    };

    public static void registerAllSpecialItem() {
        new Storage("proton").register();
        new Storage("neutron").register();
        Nucleus.NUCLEUS.register();
        new Debugger().register();
        new WitherAltarWand().register();
    }

    public static void registerAllPlainItem() {
        new ItemMaterial(plainItemsID).register();
    }
}
