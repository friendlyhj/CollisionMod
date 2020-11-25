package youyihj.collision.compat.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import youyihj.collision.Collision;

import java.util.Optional;

public class ColliderCategory implements IRecipeCategory<ColliderWrapper> {
    private final IDrawable background;
    public static final String UID = "collision.collider";

    public ColliderCategory(IGuiHelper guiHelper) {
        background = guiHelper.drawableBuilder(new ResourceLocation(Collision.MODID, "textures/gui/collider.png"),
                0, 0, 112, 54).addPadding(0, 10, 0, 0).build();
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return I18n.format("jei.category.collision.collider");
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
    public void setRecipe(IRecipeLayout recipeLayout, ColliderWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup guiItems = recipeLayout.getItemStacks();
        for (int i = 0; i < 10; i++) {
            if (i == 9) {
                guiItems.init(i, false, 90, 18);
                guiItems.set(i, recipeWrapper.out);
            } else {
                int x = (i % 3) * 18;
                int y = i / 3 * 18;
                guiItems.init(i, true, x, y);
                guiItems.set(i, Optional.ofNullable(recipeWrapper.in.get(i)).orElse(ItemStack.EMPTY));
            }
        }
    }
}
