package youyihj.collision.recipe;

import net.minecraft.item.ItemStack;
import youyihj.collision.block.absorber.EnumAbsorber;

import java.util.HashSet;

public class ColliderRecipe{
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

    public static HashSet<ColliderRecipe> colliderRecipes = new HashSet<>();

    public int getLevel() {
        return level;
    }

    public ItemStack getOut() {
        return out;
    }

    public EnumAbsorber[][] getInput() {
        return input;
    }

    public void register() {
        colliderRecipes.add(this);
    }
}
