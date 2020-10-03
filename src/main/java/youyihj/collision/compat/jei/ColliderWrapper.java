package youyihj.collision.compat.jei;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import youyihj.collision.recipe.ColliderRecipe;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ColliderWrapper implements IRecipeWrapper {
    protected List<ItemStack> in;
    protected ItemStack out;
    protected int level;

    @SuppressWarnings("unchecked")
    public ColliderWrapper(ColliderRecipe recipe) {
        this.out = recipe.getOut();
        this.level = recipe.getLevel();
        ItemStack[] in = new ItemStack[9];

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if (x == 1 && y == 1) continue;
                in[x + y * 3] = new ItemStack(recipe.getInput()[x][y].getInstance());
            }
        }
        this.in = Arrays.asList(in);
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setOutput(VanillaTypes.ITEM, out);
        ingredients.setInputLists(VanillaTypes.ITEM, Collections.singletonList(in));
    }
}
