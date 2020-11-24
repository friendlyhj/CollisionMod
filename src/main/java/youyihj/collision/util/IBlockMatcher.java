package youyihj.collision.util;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

import java.util.function.Predicate;

/**
 * @author youyihj
 */
@FunctionalInterface
public interface IBlockMatcher extends Predicate<IBlockState> {
    IBlockMatcher AIR = (state) -> state.getMaterial() == Material.AIR;
}
