package youyihj.collision.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import youyihj.collision.Collision;

public class NetworkRegistryHandler {
    public static void register() {
        NetworkRegistry.INSTANCE.registerGuiHandler(Collision.MODID, new CollisionGuiHandler());
    }
}
