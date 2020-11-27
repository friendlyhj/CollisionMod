package youyihj.collision.compat.crafttweaker;

import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.item.IngredientAny;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.mc1120.game.MCGame;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import youyihj.collision.util.IBlockMatcher;

/**
 * @author youyihj
 */
public class CraftTweakerUtils {
    private static final Ingredient ANY_BLOCK = Ingredient.fromStacks(MCGame.INSTANCE.getItems().stream()
            .flatMap(itemDef -> itemDef.getSubItems().stream())
            .filter(IItemStack::isItemBlock)
            .map(CraftTweakerMC::getItemStack)
            .toArray(ItemStack[]::new));

    public static IBlockMatcher getBlockMatcher(IIngredient ingredient) {
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

    public static Ingredient getIngredientOrAnyBlock(IIngredient ingredient) {
        if (ingredient instanceof IngredientAny) {
            return ANY_BLOCK;
        }
        return CraftTweakerMC.getIngredient(ingredient);
    }
}
