package youyihj.collision.compat.jei;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import youyihj.collision.block.ColliderBase;
import youyihj.collision.item.ItemRegistryHandler;
import youyihj.collision.recipe.CustomColliderRecipe;

import java.util.List;

/**
 * @author youyihj
 */
public class CustomColliderWrapper implements IRecipeWrapper {
    protected final List<Ingredient> in;
    protected final List<ItemStack> out;
    protected final int level;
    protected final int successChance;

    @Override
    public void getIngredients(IIngredients ingredients) {
        out.forEach((outBlock) -> ingredients.setOutput(VanillaTypes.ITEM, outBlock));
    }

    public CustomColliderWrapper(List<Ingredient> in, int level, int successChance, List<ItemStack> outBlocks) {
        this.in = in;
        this.level = level;
        this.successChance = successChance;
        this.out = outBlocks;
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        ChanceDrawer.draw(minecraft, recipeWidth, successChance);
    }
}
