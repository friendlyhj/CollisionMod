package youyihj.collision.util;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * @author youyihj
 */
@FunctionalInterface
public interface IBlockMatcher extends Predicate<IBlockState> {
    IBlockMatcher AIR = (state) -> state.getMaterial() == Material.AIR;

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
}
