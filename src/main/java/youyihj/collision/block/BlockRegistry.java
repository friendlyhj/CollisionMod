package youyihj.collision.block;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import youyihj.collision.Collision;

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

    private static class InternalRegistry {
        private static void registerInternal() {
            new BlockBase("machine_casing", AbstractBlock.Properties.create(Material.IRON).hardnessAndResistance(5.0f, 45.0f)).register();
        }
    }
}
