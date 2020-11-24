package youyihj.collision.compat.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import youyihj.collision.util.IBlockMatcher;

import java.util.List;

/**
 * @author youyihj
 */
public class CrTBlockMatcherGetter {
    public static IBlockMatcher get(IIngredient ingredient) {
        if (ingredient == null) {
            return IBlockMatcher.AIR;
        }
        List<IItemStack> stacks = ingredient.getItems();
        if (stacks.isEmpty()) {
            CraftTweakerAPI.logError(ingredient.toCommandString() + " is empty!");
        }
        IBlockMatcher matcher = crtItemToMCBlockState(stacks.get(0));
        if (stacks.size() == 1) {
            return matcher;
        }
        for (int i = 1; i < stacks.size(); i++) {
            matcher = matcher.or(crtItemToMCBlockState(stacks.get(i)));
        }
        return matcher;
    }

    private static IBlockMatcher crtItemToMCBlockState(IItemStack stack) {
        return (state) -> (CraftTweakerMC.getBlockState(state).compare(stack.asBlock().getDefinition().getStateFromMeta(stack.getMetadata())) == 0);
    }
}
