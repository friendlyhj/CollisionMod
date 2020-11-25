package youyihj.collision.compat.jei;

import com.google.common.collect.Lists;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import youyihj.collision.Collision;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author youyihj
 */
public class CustomColliderCategory implements IRecipeCategory<CustomColliderWrapper> {
    private final IDrawable background;
    public static final String UID = "collision.custom_collider";

    public CustomColliderCategory(IGuiHelper guiHelper) {
        background = guiHelper.drawableBuilder(new ResourceLocation(Collision.MODID, "textures/gui/custom_collider.png"),
                0, 0, 139, 54).addPadding(0, 10, 0, 0).build();
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return I18n.format("jei.category.collision.custom_collider");
    }

    @Override
    public String getModName() {
        return Collision.NAME;
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, CustomColliderWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup guiItems = recipeLayout.getItemStacks();
        for (int i = 0; i < 18; i++) {
            if (i < 9) {
                int x = (i % 3) * 18;
                int y = i / 3 * 18;
                guiItems.init(i, true, x, y);
                guiItems.set(i, Lists.newArrayList(recipeWrapper.in.get(i).getMatchingStacks()));
            } else {
                int j = i - 9;
                int x = (j % 3) * 18 + 85;
                int y = j / 3 * 18;
                guiItems.init(i, false, x, y);
                guiItems.set(i, recipeWrapper.out.get(j));
            }
        }
    }
}
