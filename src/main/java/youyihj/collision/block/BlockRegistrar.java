package youyihj.collision.block;

import youyihj.collision.block.absorber.Neutron;
import youyihj.collision.block.absorber.NeutronEmpty;
import youyihj.collision.block.absorber.Proton;
import youyihj.collision.block.absorber.ProtonEmpty;
import youyihj.collision.block.spawner.MetalSpawner;
import youyihj.collision.item.Nucleus;

public class BlockRegistrar {
    public static void registerAllBlock() {
        // new CollisionBlock("test_block", Material.GRASS).register();
        new ColliderBase(1).register();
        Proton.INSTANCE.register();
        ProtonEmpty.INSTANCE.register();
        Neutron.INSTANCE.register();
        NeutronEmpty.INSTANCE.register();
        new MetalSpawner().register();
    }
}
