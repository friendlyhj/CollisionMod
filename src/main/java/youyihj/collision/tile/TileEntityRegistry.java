package youyihj.collision.tile;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import youyihj.collision.Collision;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author youyihj
 */
public class TileEntityRegistry {
    private static final DeferredRegister<TileEntityType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Collision.MODID);
    private static final Map<String, RegistryObject<TileEntityType<?>>> TILE_ENTITY_TYPES = new HashMap<>();

    public static void register(IEventBus bus) {
        REGISTER.register(bus);
    }

    @SuppressWarnings("ConstantConditions")
    public static void registerTileEntity(TileEntityBound teBound) {
        Supplier<TileEntityType<?>> tileEntityTypeSupplier = () -> TileEntityType.Builder.create(() -> {
            try {
                return teBound.getTileEntity().newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException("fail to register tile entity: " + teBound.getTileEntityName(), e);
            }
        }, teBound.getBlock()).build(null);
        TILE_ENTITY_TYPES.put(teBound.getTileEntityName(), REGISTER.register(teBound.getTileEntityName(), tileEntityTypeSupplier));
    }

    public static TileEntityType<?> getTileEntityType(String name) {
        return TILE_ENTITY_TYPES.get(name).get();
    }
}
