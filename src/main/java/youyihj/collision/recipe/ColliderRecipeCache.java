package youyihj.collision.recipe;

import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.math.BlockPos;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * @author youyihj
 */
public class ColliderRecipeCache {
    private static final Map<BlockPos, ColliderRecipe> cache = new HashMap<>();

    public static Optional<ColliderRecipe> get(RecipeManager recipeManager, BlockPos pos, Predicate<ColliderRecipe> recipePredicate) {
        ColliderRecipe recipeInCache = cache.get(pos);
        if (recipeInCache != null && recipePredicate.test(recipeInCache)) {
            return Optional.of(recipeInCache);
        } else {
            Optional<ColliderRecipe> recipeFromManager = getFromRecipeManager(recipeManager, recipePredicate);
            if (recipeFromManager.isPresent()) {
                cache.put(pos, recipeFromManager.get());
                return recipeFromManager;
            }
        }
        return Optional.empty();
    }

    public static void removeCachePos(BlockPos pos) {
        cache.remove(pos);
    }

    private static Optional<ColliderRecipe> getFromRecipeManager(RecipeManager recipeManager, Predicate<ColliderRecipe> recipePredicate) {
        return recipeManager.getRecipesForType(ColliderRecipeType.INSTANCE).stream().filter(recipePredicate).findFirst();
    }
}
