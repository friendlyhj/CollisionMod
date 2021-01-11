package youyihj.collision.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import youyihj.collision.Collision;
import youyihj.collision.block.ColliderBase;
import youyihj.collision.item.ItemNucleus;
import youyihj.collision.recipe.ColliderRecipe;
import youyihj.collision.recipe.ColliderRecipeType;

/**
 * @author youyihj
 */
@JeiPlugin
public class CollisionPlugin implements IModPlugin {
    private static final ResourceLocation UID = Collision.rl("jei_plugin");

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        for (int i = 1; i < 5; i++) {
            registration.addRecipeCatalyst(new ItemStack(ColliderBase.getItem(i)), ColliderRecipe.ID);
        }
    }

    @Override
    public ResourceLocation getPluginUid() {
        return UID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new ColliderCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(Minecraft.getInstance().world.getRecipeManager().getRecipesForType(ColliderRecipeType.INSTANCE), ColliderRecipe.ID);
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        jeiRuntime.getIngredientManager().addIngredientsAtRuntime(VanillaTypes.ITEM, ItemNucleus.getAllSubItems());
    }
}
