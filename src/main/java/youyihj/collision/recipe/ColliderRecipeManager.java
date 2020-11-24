package youyihj.collision.recipe;

import com.google.common.collect.ImmutableList;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * @author youyihj
 */
public class ColliderRecipeManager {
    private static final List<CustomColliderRecipe> COLLIDER_RECIPES = new ArrayList<>();
    private static final List<ColliderRecipe> DEFAULT_COLLIDER_RECIPES = new ArrayList<>();

    public static boolean isSuchOutputExist(Item item) {
        return COLLIDER_RECIPES.stream().anyMatch(recipe -> recipe.getOut().getItem() == item);
    }

    public static CustomColliderRecipe getRecipe(ItemStack itemStack) {
        return COLLIDER_RECIPES.stream().filter(recipe -> recipe.getOut().isItemEqual(itemStack)).findFirst().orElse(null);
    }

    public static ColliderRecipe getDefaultRecipe(ItemStack itemStack) {
        return (ColliderRecipe) COLLIDER_RECIPES.stream().filter(CustomColliderRecipe::isDefault).filter(recipe -> recipe.getOut().isItemEqual(itemStack)).findFirst().orElse(null);
    }

    public static boolean isSuchOutputExist(ItemStack itemStack) {
        return isSuchOutputExist(itemStack.getItem());
    }

    public static List<CustomColliderRecipe> getColliderRecipes() {
        return ImmutableList.copyOf(COLLIDER_RECIPES);
    }

    public static List<ColliderRecipe> getDefaultColliderRecipes() {
        return ImmutableList.copyOf(DEFAULT_COLLIDER_RECIPES);
    }

    public static void addRecipe(CustomColliderRecipe recipe) {
        COLLIDER_RECIPES.add(recipe);
    }

    public static void addDefaultRecipe(ColliderRecipe recipe) {
        DEFAULT_COLLIDER_RECIPES.add(recipe);
    }

    public static void removeRecipe(ItemStack out) {
        COLLIDER_RECIPES.removeIf(recipe -> recipe.getOut().isItemEqual(out));
    }

    public static void removeAllRecipe() {
        COLLIDER_RECIPES.clear();
    }
}
