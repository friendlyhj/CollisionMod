package youyihj.collision.block;

import net.minecraft.block.material.Material;
import youyihj.collision.block.absorber.Neutron;
import youyihj.collision.block.absorber.NeutronEmpty;
import youyihj.collision.block.absorber.Proton;
import youyihj.collision.block.absorber.ProtonEmpty;
import youyihj.collision.block.spawner.MetalSpawner;
import youyihj.collision.item.Nucleus;
import youyihj.collision.item.SingleNucleus;

public class BlockRegistrar {
    public static void registerAllBlock() {
        // new CollisionBlock("test_block", Material.GRASS).register();
        new ColliderBase(1).register();
        Proton.INSTANCE.register();
        ProtonEmpty.INSTANCE.register();
        Neutron.INSTANCE.register();
        NeutronEmpty.INSTANCE.register();
        new MetalSpawner().register();
        Nucleus.singleHashMap.forEach((meta, single) -> new Booster(single).register());
    }
}
