package youyihj.collision.compat.jei;

import com.mojang.blaze3d.matrix.MatrixStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import youyihj.collision.Collision;
import youyihj.collision.block.ColliderBase;
import youyihj.collision.block.absorber.Absorber;
import youyihj.collision.recipe.ColliderRecipe;
import youyihj.collision.util.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author youyihj
 */
public class ColliderCategory implements IRecipeCategory<ColliderRecipe> {
    private final IDrawable background;
    private final IDrawable icon;

    public ColliderCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.drawableBuilder(Collision.rl("textures/gui/collider.png"),
                0, 0, 112, 54).addPadding(0, 10, 0, 0).build();
        this.icon = guiHelper.createDrawableIngredient(new ItemStack(ColliderBase.getItem(1)));
    }

    @Override
    public ResourceLocation getUid() {
        return ColliderRecipe.ID;
    }

    @Override
    public Class<? extends ColliderRecipe> getRecipeClass() {
        return ColliderRecipe.class;
    }

    @Override
    public String getTitle() {
        return I18n.format("jei.category.collision.collider");
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void draw(ColliderRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY) {

    }

    @Override
    public void setIngredients(ColliderRecipe recipe, IIngredients iIngredients) {
        boolean hasProton = false;
        boolean hasNeutron = false;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Absorber.Type type = recipe.getIn()[i][j];
                if (type == Absorber.Type.PROTON)
                    hasProton = true;
                if (type == Absorber.Type.NEUTRON)
                    hasNeutron = true;
            }
        }
        List<ItemStack> list = new ArrayList<>(2);
        if (hasProton)
            list.add(new ItemStack(Absorber.Type.PROTON.getAbsorberByRecipe(recipe)));
        if (hasNeutron)
            list.add(new ItemStack(Absorber.Type.NEUTRON.getAbsorberByRecipe(recipe)));
        list.add(new ItemStack(ColliderBase.getItem(recipe.getLevel())));
        iIngredients.setInputLists(VanillaTypes.ITEM, Collections.singletonList(list));
        iIngredients.setOutput(VanillaTypes.ITEM, recipe.getRecipeOutput());
    }

    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, ColliderRecipe recipe, IIngredients iIngredients) {
        IGuiItemStackGroup guiItems = iRecipeLayout.getItemStacks();
        ItemStack[][] ins = Utils.map2DArray(recipe.getIn(), type -> new ItemStack(type.getAbsorberByRecipe(recipe)), ItemStack.class, ItemStack.EMPTY);
        ins[1][1] = new ItemStack(ColliderBase.getItem(recipe.getLevel()));
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (ins[i][j].isEmpty())
                    continue;
                guiItems.init(i * 3 + j, true, i * 18, j * 18);
                guiItems.set(i * 3 + j, ins[i][j]);
            }
        }
        guiItems.init(9, false, 90, 18);
        guiItems.set(9, recipe.getRecipeOutput());
    }
}
