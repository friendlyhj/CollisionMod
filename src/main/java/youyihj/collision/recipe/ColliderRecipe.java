package youyihj.collision.recipe;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import youyihj.collision.IRegistryObject;
import youyihj.collision.block.absorber.EnumAbsorber;

import java.util.ArrayList;
import java.util.List;

public class ColliderRecipe implements IRegistryObject {
    public ColliderRecipe(int level, ItemStack out, EnumAbsorber[][] input) {
        this.level = level;
        this.out = out;
        if (input.length == 3 && input[0].length == 3) {
            this.input = input;
        } else {
            throw new IllegalArgumentException("Input argument must be 3 * 3 array!");
        }
    }
    private final int level;
    private final ItemStack out;
    private final EnumAbsorber[][] input;

    public static List<ColliderRecipe> colliderRecipes = new ArrayList<>();

    public int getLevel() {
        return level;
    }

    public ItemStack getOut() {
        return out;
    }

    public EnumAbsorber[][] getInput() {
        return input;
    }

    @Override
    public void register() {
        colliderRecipes.add(this);
    }

    public static boolean isSuchOutputExist(Item item) {
        return colliderRecipes.stream().anyMatch(recipe -> recipe.getOut().getItem() == item);
    }

    public static ColliderRecipe getRecipe(ItemStack itemStack) {
        return colliderRecipes.stream().filter(recipe -> recipe.getOut().getItem() == itemStack.getItem()).findFirst().orElse(null);
    }

    public static boolean isSuchOutputExist(ItemStack itemStack) {
        return isSuchOutputExist(itemStack.getItem());
    }

    public boolean isAdvanced() {
        return level > 2;
    }
}
