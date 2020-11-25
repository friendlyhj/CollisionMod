package youyihj.collision.compat.jei;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author youyihj
 */
public class CustomColliderWrapper implements IRecipeWrapper {
    protected final List<Ingredient> in;
    protected final List<ItemStack> out;
    protected final int level;
    protected final int successChance;
    protected final int conversionChance;

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setOutputLists(VanillaTypes.ITEM, Collections.singletonList(out));
        ingredients.setInputLists(VanillaTypes.ITEM, in.stream()
                .filter(ingredient -> ingredient != null && ingredient != Ingredient.EMPTY)
                .map(Ingredient::getMatchingStacks)
                .map(Arrays::asList)
                .collect(Collectors.toList()));
    }

    public CustomColliderWrapper(List<Ingredient> in, int level, int successChance, int conversionChance, List<ItemStack> outBlocks) {
        this.in = in;
        this.level = level;
        this.successChance = successChance;
        this.conversionChance = conversionChance;
        this.out = outBlocks;
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        ChanceDrawer.draw(minecraft, recipeWidth, successChance, conversionChance);
    }
}
