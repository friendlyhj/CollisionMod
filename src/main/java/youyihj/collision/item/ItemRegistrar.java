package youyihj.collision.item;


public class ItemRegistrar {
    private static String[] plainItemsID = {
            "test_plain",
            "test_plain_2"
    };

    public static void registerAllSpecialItem() {
        new TestItem().register();
    }

    public static void registerAllPlainItem() {
        for (String plainItemID : plainItemsID) {
            new CollisionItem(plainItemID).register();
        }
    }
}
