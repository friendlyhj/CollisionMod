package youyihj.collision.recipe;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import youyihj.collision.block.absorber.EnumAbsorber;
import youyihj.collision.util.IBlockMatcher;
import youyihj.collision.util.Utils;

import java.util.function.Function;

public class ColliderRecipe extends CustomColliderRecipe {
    public ColliderRecipe(int level, ItemStack out, EnumAbsorber[][] input) {
        super(level, out,
                Utils.convert2DArray(input, getConversionFunction(level), IBlockMatcher.class),
                Utils.convert2DArray(input, getConversionFunctionForOut(level), IBlockState.class),
                100, 100);
        this.input = input;
    }

    private static Function<EnumAbsorber, IBlockMatcher> getConversionFunction(int level) {
        return absorber -> {
            if (absorber == null) {
                return IBlockMatcher.AIR;
            } else {
                return (iBlockState -> absorber.getInstanceByLevel(level) == iBlockState.getBlock());
            }
        };
    }

    private static Function<EnumAbsorber, IBlockState> getConversionFunctionForOut(int level) {
        return absorber -> {
            if (absorber == null) {
                return Blocks.AIR.getDefaultState();
            } else {
                return absorber.getTransformAbsorber().getInstanceByLevel(level).getDefaultState();
            }
        };
    }

    private final EnumAbsorber[][] input;

    public EnumAbsorber[][] getInput() {
        return input;
    }

    @Override
    public void register() {
        super.register();
        ColliderRecipeManager.addDefaultRecipe(this);
    }
}
