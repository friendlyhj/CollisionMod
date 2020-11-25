package youyihj.collision.recipe;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import youyihj.collision.IRegistryObject;
import youyihj.collision.util.IBlockMatcher;

import java.util.Objects;

/**
 * @author youyihj
 */
public class CustomColliderRecipe implements IRegistryObject {
    public CustomColliderRecipe(int level, ItemStack out, IBlockMatcher[][] input, IBlockState[][] conversionBlocks, int successChance) {
        this.level = level;
        this.out = out;
        for (IBlockMatcher[] iBlockMatchers : input) {
            for (IBlockMatcher iBlockMatcher : iBlockMatchers) {
                Objects.requireNonNull(iBlockMatcher);
            }
        }
        if (input.length == 3 && input[0].length == 3) {
            this.blocks = input;
        } else {
            throw new IllegalArgumentException("Input argument must be 3 * 3 array!");
        }
        if (conversionBlocks.length == 3 && conversionBlocks[0].length == 3) {
            this.conversionBlocks = conversionBlocks;
        } else {
            throw new IllegalArgumentException("Input argument must be 3 * 3 array!");
        }
        this.successChance = successChance;
    }

    public CustomColliderRecipe(int level, ItemStack out, IBlockMatcher[][] blocks, IBlockState[][] conversionBlocks) {
        this.level = level;
        this.out = out;
        this.blocks = blocks;
        this.conversionBlocks = conversionBlocks;
        this.successChance = 100;
    }

    private final int level;
    private final ItemStack out;
    private final IBlockMatcher[][] blocks;
    private final int successChance;
    private final IBlockState[][] conversionBlocks;

    public int getLevel() {
        return level;
    }

    public ItemStack getOut() {
        return out;
    }

    public IBlockMatcher[][] getBlocks() {
        return blocks;
    }

    public boolean isAdvanced() {
        return getLevel() > 2;
    }

    @Override
    public void register() {
        ColliderRecipeManager.addRecipe(this);
    }

    public IBlockState[][] getConversionBlocks() {
        return conversionBlocks;
    }

    public int getSuccessChance() {
        return successChance;
    }

    public boolean isDefault() {
        return this instanceof ColliderRecipe;
    }
}
