package youyihj.collision.recipe;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import youyihj.collision.block.absorber.EnumAbsorber;
import youyihj.collision.util.IBlockMatcher;
import youyihj.collision.util.Lazy;
import youyihj.collision.util.Utils;

import java.util.function.Function;

public class ColliderRecipe extends CustomColliderRecipe {
    public ColliderRecipe(int level, ItemStack out, EnumAbsorber[][] input, int successChance) {
        super(level, out,
                Utils.map2DArray(input, getConversionFunction(level), IBlockMatcher.class, IBlockMatcher.Impl.air()),
                Utils.map2DArray(input, getConversionFunctionForOut(level), IBlockState.class, Blocks.AIR.getDefaultState()),
                successChance, 100);
        this.input = input;
    }

    public ColliderRecipe(int level, ItemStack out, EnumAbsorber[][] input) {
        this(level, out, input, 100);
    }

    private static Function<EnumAbsorber, IBlockMatcher> getConversionFunction(int level) {
        return absorber -> Lazy.of(absorber).map(absorber1 -> IBlockMatcher.Impl.fromBlock(absorber1.getInstanceByLevel(level))).orElse(IBlockMatcher.Impl.air());
    }

    private static Function<EnumAbsorber, IBlockState> getConversionFunctionForOut(int level) {
        return absorber -> Lazy.of(absorber).map(absorber1 -> absorber1.getTransformAbsorber().getInstanceByLevel(level).getDefaultState()).orElse(Blocks.AIR.getDefaultState());
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
