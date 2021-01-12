package youyihj.collision.multiblock;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.vector.Vector3i;

import java.util.function.Predicate;

public class MultiblockElement {
    private final Vector3i offset;
    private final Predicate<BlockState> checker;

    public MultiblockElement(Vector3i offset, Predicate<BlockState> checker) {
        this.offset = offset;
        this.checker = checker;
    }

    public Vector3i getOffset() {
        return offset;
    }

    public boolean check(BlockState stateToCheck) {
        return checker.test(stateToCheck);
    }

}
