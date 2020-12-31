package youyihj.collision.block;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import youyihj.collision.Collision;
import youyihj.collision.block.absorber.Neutron;
import youyihj.collision.block.absorber.NeutronEmpty;
import youyihj.collision.block.absorber.Proton;
import youyihj.collision.block.absorber.ProtonEmpty;
import youyihj.collision.block.spawner.BlockGemSpawner;
import youyihj.collision.block.spawner.BlockMetalSpawner;

import java.util.HashMap;
import java.util.Map;

/**
 * @author youyihj
 */
public class BlockRegistry {
    private static final DeferredRegister<Block> REGISTER = DeferredRegister.create(ForgeRegistries.BLOCKS, Collision.MODID);
    private static final Map<String, BlockBase> BLOCKS = new HashMap<>();

    public static void register(IEventBus bus) {
        InternalRegistry.registerInternal();
        REGISTER.register(bus);
    }

    public static RegistryObject<Block> registerBlock(BlockBase blockBase) {
        BLOCKS.put(blockBase.getName(), blockBase);
        return REGISTER.register(blockBase.getName(), () -> blockBase);
    }

    public static Map<String, BlockBase> getBlocks() {
        return ImmutableMap.copyOf(BLOCKS);
    }

    public static BlockBase getBlock(String name) {
        return BLOCKS.get(name);
    }

    private static class InternalRegistry {
        private static void registerInternal() {
            Neutron.INSTANCE.register();
            Neutron.Refined.INSTANCE.register();
            Proton.INSTANCE.register();
            Proton.Refined.INSTANCE.register();
            NeutronEmpty.INSTANCE.register();
            NeutronEmpty.Refined.INSTANCE.register();
            ProtonEmpty.INSTANCE.register();
            ProtonEmpty.Refined.INSTANCE.register();
            BlockMetalSpawner.INSTANCE.register();
            BlockGemSpawner.INSTANCE.register();
            new Booster().register();
            BlockHarvester.INSTANCE.register();

            for (int i = 1; i < 5; i++) {
                new ColliderBase(i).register();
            }
        }
    }
}
