package youyihj.collision.compat.jei;

import com.google.common.collect.Lists;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import youyihj.collision.block.ColliderBase;
import youyihj.collision.recipe.ColliderRecipeManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@JEIPlugin
public class CollisionPlugin implements IModPlugin {
    private static final List<CustomColliderWrapper> JEI_CUSTOM_COLLIDER_RECIPES = new ArrayList<>();

    @Override
    public void register(IModRegistry registry) {
        registry.addRecipes(ColliderRecipeManager.getDefaultColliderRecipes().stream().map(ColliderWrapper::new).collect(Collectors.toSet()), ColliderCategory.UID);
        registry.addRecipes(JEI_CUSTOM_COLLIDER_RECIPES, CustomColliderCategory.UID);
        for (int i = 1; i < 5; i++) {
            registry.addRecipeCatalyst(ColliderBase.getCollider(i), ColliderCategory.UID);
            registry.addRecipeCatalyst(ColliderBase.getCollider(i), CustomColliderCategory.UID);
        }
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(new ColliderCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new CustomColliderCategory(registry.getJeiHelpers().getGuiHelper()));
    }

    public static void addJEICustomColliderRecipe(int level, Ingredient[][] inputs, ItemStack out, ItemStack[][] outBlocks, int successChance) {
        Ingredient[] inputArray = new Ingredient[9];
        ItemStack[] outArray = new ItemStack[9];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int index = i + j * 3;
                if (i == 1 && j == 1) {
                    inputArray[index] = Ingredient.fromStacks(ColliderBase.getCollider(level));
                    outArray[index] = out;
                    continue;
                }
                inputArray[index] = inputs[i][j];
                outArray[index] = outBlocks[i][j];
            }
        }
        JEI_CUSTOM_COLLIDER_RECIPES.add(
                new CustomColliderWrapper(Lists.newArrayList(inputArray), level, successChance, Lists.newArrayList(outArray))
        );
    }
}
