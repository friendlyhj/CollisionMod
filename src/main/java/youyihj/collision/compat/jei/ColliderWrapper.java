package youyihj.collision.compat.jei;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import youyihj.collision.block.ColliderBase;
import youyihj.collision.block.absorber.EnumAbsorber;
import youyihj.collision.recipe.ColliderRecipe;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ColliderWrapper implements IRecipeWrapper {
    protected final List<ItemStack> in;
    protected final ItemStack out;
    protected final int level;
    protected final int successChance;

    public ColliderWrapper(ColliderRecipe recipe) {
        this.out = recipe.getOut();
        this.level = recipe.getLevel();
        this.successChance = recipe.getSuccessChance();
        ItemStack[] in = new ItemStack[9];

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                in[x + y * 3] = (x == 1 && y == 1)
                        ? ColliderBase.getCollider(level)
                        : getAbsorberItem(recipe.getInput()[x][y], level);
            }
        }
        this.in = Arrays.asList(in);
    }

    @Override
    public void getIngredients(@Nonnull IIngredients ingredients) {
        ingredients.setOutput(VanillaTypes.ITEM, out);
        ingredients.setInputLists(VanillaTypes.ITEM, Collections.singletonList(in));
    }

    private static ItemStack getAbsorberItem(EnumAbsorber absorber, int level) {
        return absorber == null ? ItemStack.EMPTY : new ItemStack(absorber.getInstanceByLevel(level));
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        ChanceDrawer.draw(minecraft, recipeWidth, successChance, 100);
    }
}
