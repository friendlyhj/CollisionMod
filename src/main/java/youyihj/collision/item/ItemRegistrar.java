package youyihj.collision.item;


public class ItemRegistrar {
    private static String[] plainItemsID = {

    };

    public static void registerAllSpecialItem() {
        new Storage("proton").register();
        new Storage("neutron").register();
        Nucleus.NUCLEUS.register();
        new Debugger().register();
    }

    public static void registerAllPlainItem() {
        for (String plainItemID : plainItemsID) {
            new CollisionItem(plainItemID).register();
        }
    }
}
