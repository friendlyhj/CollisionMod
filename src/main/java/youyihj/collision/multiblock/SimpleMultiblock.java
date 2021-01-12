package youyihj.collision.multiblock;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.IWorldReader;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author youyihj
 */
public class SimpleMultiblock {

    public SimpleMultiblock(Predicate<BlockState> core) {
        addElement(new MultiblockElement(Vector3i.NULL_VECTOR, core));
    }

    private final List<MultiblockElement> multiblockElements = new ArrayList<>();

    public SimpleMultiblock addElement(MultiblockElement element) {
        multiblockElements.add(element);
        return this;
    }

    public SimpleMultiblock addElement(Vector3i offset, Predicate<BlockState> checker) {
        return addElement(new MultiblockElement(offset, checker));
    }

    public boolean match(IWorldReader world, BlockPos pos) {
        return multiblockElements.stream().allMatch(element -> element.check(world.getBlockState(pos.add(element.getOffset()))));
    }

    public List<MultiblockElement> getMultiblockElements() {
        return multiblockElements;
    }
}
