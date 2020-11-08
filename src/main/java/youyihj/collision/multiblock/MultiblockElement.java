package youyihj.collision.multiblock;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.Vec3i;

import java.util.function.Predicate;

public class MultiblockElement {
    private final Vec3i offset;
    private final IBlockMatcher checker;

    public MultiblockElement(Vec3i offset, IBlockMatcher checker) {
        this.offset = offset;
        this.checker = checker;
    }

    public Vec3i getOffset() {
        return offset;
    }

    public boolean check(IBlockState stateToCheck) {
        return checker.test(stateToCheck);
    }

    @FunctionalInterface
    public interface IBlockMatcher extends Predicate<IBlockState> {

    }
}
