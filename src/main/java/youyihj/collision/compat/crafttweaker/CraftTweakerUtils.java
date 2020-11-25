package youyihj.collision.compat.crafttweaker;

import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.item.IngredientAny;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.block.state.IBlockState;
import youyihj.collision.util.IBlockMatcher;

/**
 * @author youyihj
 */
public class CraftTweakerUtils {
    public static IBlockMatcher get(IIngredient ingredient) {
        if (ingredient instanceof IngredientAny) {
            return IBlockMatcher.Impl.acceptAll();
        }
        return IBlockMatcher.Impl.fromIngredient(CraftTweakerMC.getIngredient(ingredient));
    }

    public static IBlockState itemToState(IItemStack stack) {
        return CraftTweakerMC.getBlockState(stack.asBlock().getDefinition().getStateFromMeta(stack.getMetadata()));
    }

    public static IBlockState itemToState(IIngredient ingredient) {
        return itemToState(ingredient.getItems().get(0));
    }
}
