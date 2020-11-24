package youyihj.collision.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;
import youyihj.collision.block.ColliderBase;
import youyihj.collision.item.ItemRegistryHandler;
import youyihj.collision.recipe.ColliderRecipeManager;

import java.util.stream.Collectors;

@JEIPlugin
public class CollisionPlugin implements IModPlugin {
    @Override
    public void register(IModRegistry registry) {
        registry.addRecipes(ColliderRecipeManager.getDefaultColliderRecipes().stream().map(ColliderWrapper::new).collect(Collectors.toSet()), ColliderCategory.UID);
        for (int i = 1; i < 5; i++) {
            registry.addRecipeCatalyst(new ItemStack(ItemRegistryHandler.itemBlockHashMap.get(ColliderBase.getRegistryName(i))), ColliderCategory.UID);
        }
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(new ColliderCategory(registry.getJeiHelpers().getGuiHelper()));
    }
}
