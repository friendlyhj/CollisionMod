package youyihj.collision.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;
import youyihj.collision.block.ColliderBase;
import youyihj.collision.item.ItemRegistryHandler;
import youyihj.collision.recipe.ColliderRecipe;

import java.util.stream.Collectors;

@JEIPlugin
public class CollisionPlugin implements IModPlugin {
    @Override
    public void register(IModRegistry registry) {
        registry.addRecipes(ColliderRecipe.colliderRecipes.stream().map(ColliderWrapper::new).collect(Collectors.toSet()), ColliderCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(ItemRegistryHandler.itemBlockHashMap.get(ColliderBase.getRegistryName(1))), ColliderCategory.UID);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(new ColliderCategory(registry.getJeiHelpers().getGuiHelper()));
    }
}
