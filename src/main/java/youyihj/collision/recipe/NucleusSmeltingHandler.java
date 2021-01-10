package youyihj.collision.recipe;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonObject;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.*;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import youyihj.collision.Collision;
import youyihj.collision.config.Configuration;
import youyihj.collision.item.ItemNucleus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author youyihj
 */
public class NucleusSmeltingHandler {
    public NucleusSmeltingHandler(RecipeManager recipeManager) {
        this.recipeManager = recipeManager;
    }

    private final RecipeManager recipeManager;

    public List<FurnaceRecipe> genRecipes() {
        List<FurnaceRecipe> temp = new ArrayList<>();
        ItemNucleus.nuclei.values().forEach(nucleusEntry -> {
            ITag<Item> nuggetTag = ItemTags.getCollection().get(new ResourceLocation("forge", "nuggets/" + nucleusEntry.getName()));
            if (nuggetTag != null) {
                JsonObject json = new JsonObject();
                JsonObject ingredient = new JsonObject();
                ingredient.addProperty("item", Collision.rl( nucleusEntry.getName() + "_nucleus").toString());
                json.add("ingredient", ingredient);
                Item nugget = nuggetTag.getAllElements().get(0);
                JsonObject result = new JsonObject();
                result.addProperty("item", nugget.getRegistryName().toString());
                result.addProperty("count", Configuration.nuggetsOutputCount.get());
                json.add("result", result);
                temp.add(IRecipeSerializer.SMELTING.read(Collision.rl(nucleusEntry.getName() + "nucleus_to_nugget"), json));
            }
        });
        return temp;
    }

    public void register() {
        Map<IRecipeType<?>, Map<ResourceLocation, IRecipe<?>>> recipes = new HashMap<>(recipeManager.recipes);
        Map<ResourceLocation, IRecipe<?>> furnaceRecipes = new HashMap<>(recipes.get(IRecipeType.SMELTING));
        genRecipes().forEach(recipe -> furnaceRecipes.put(recipe.getId(), recipe));
        recipes.put(IRecipeType.SMELTING, furnaceRecipes);
        recipeManager.recipes = ImmutableMap.copyOf(recipes);
    }
}
