package youyihj.collision.util;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * @author youyihj
 */
@FunctionalInterface
public interface IBlockMatcher extends Predicate<IBlockState> {

    @Override
    @Nonnull
    default IBlockMatcher or(Predicate<? super IBlockState> other) {
        Objects.requireNonNull(other);
        return (state) -> test(state) || other.test(state);
    }

    @Override
    @Nonnull
    default IBlockMatcher negate() {
        return (state) -> !test(state);
    }

    @Override
    @Nonnull
    default Predicate<IBlockState> and(Predicate<? super IBlockState> other) {
        throw new UnsupportedOperationException();
    }

    final class Impl {
        private static final IBlockMatcher AIR = (state) -> state.getMaterial() == Material.AIR;
        private static final IBlockMatcher ACCEPT_ALL = AIR.negate();

        public static IBlockMatcher air() {
            return AIR;
        }
        public static IBlockMatcher acceptAll() {
            return ACCEPT_ALL;
        }

        public static IBlockMatcher fromItemStack(ItemStack stack) {
            if (stack.isEmpty()) {
                return air();
            }
            Item item = stack.getItem();
            if (!(item instanceof ItemBlock)) {
                throw new IllegalArgumentException(item.getRegistryName().toString() + " is not a block!");
            }
            return (state) -> state.getBlock() == ((ItemBlock) item).getBlock() && state.getBlock().getMetaFromState(state) == stack.getMetadata();
        }

        public static IBlockMatcher fromItemStack(ItemStack... stacks) {
            if (stacks.length == 0) {
                return air();
            }
            IBlockMatcher matcher = fromItemStack(stacks[0]);
            if (stacks.length == 1) {
                return matcher;
            }
            for (int i = 1; i < stacks.length; i++) {
                matcher = matcher.or(fromItemStack(stacks[i]));
            }
            return matcher;
        }

        public static IBlockMatcher fromIngredient(Ingredient ingredient) {
            return fromItemStack(ingredient.getMatchingStacks());
        }

        public static IBlockMatcher fromBlock(Block block) {
            return (state) -> state.getBlock() == block;
        }
    }
}
