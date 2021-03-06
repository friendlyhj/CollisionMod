package youyihj.collision.recipe;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * @author youyihj
 */
public enum ColliderRecipeType implements IRecipeType<ColliderRecipe> {
    INSTANCE;

    @Override
    public String toString() {
        return ColliderRecipe.ID.toString();
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RecipeTypeRegistry {
        @SubscribeEvent
        public static void register(RegistryEvent.Register<IRecipeSerializer<?>> event) {
            Registry.register(Registry.RECIPE_TYPE, ColliderRecipe.ID, INSTANCE);
            event.getRegistry().register(ColliderRecipeSerializer.INSTANCE.setRegistryName(ColliderRecipe.ID));
        }
    }
}
