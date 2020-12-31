package youyihj.collision.tile;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.IIntArray;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import youyihj.collision.Collision;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author youyihj
 */
public class ContainerRegistry {
    private static final DeferredRegister<ContainerType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.CONTAINERS, Collision.MODID);
    private static final Map<String, RegistryObject<ContainerType<?>>> CONTAINER_TYPES = new HashMap<>();

    public static void register(IEventBus bus) {
        REGISTER.register(bus);
    }

    public static void registerContainer(String name, Class<? extends Container> containerClass, IIntArray intArray) {
        Supplier<ContainerType<?>> containerTypeSupplier = () -> (
            IForgeContainerType.create(((windowId, inv, data) -> {
                try {
                    return containerClass.getConstructor(String.class, int.class, inv.getClass(), BlockPos.class, World.class, intArray.getClass())
                            .newInstance(name, windowId, inv, data.readBlockPos(), Minecraft.getInstance().world, intArray);
                } catch (Exception e) {
                    throw new RuntimeException("fail to register container: " + containerClass.getCanonicalName(), e);
                }
            }))
        );
        CONTAINER_TYPES.put(name, REGISTER.register(name, containerTypeSupplier));
    }

    public static ContainerType<?> getContainerType(String name) {
        return CONTAINER_TYPES.get(name).get();
    }
}
