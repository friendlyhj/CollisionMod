package youyihj.collision.multiblock;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.IBlockAccess;

import java.util.ArrayList;
import java.util.List;

/**
 * @author youyihj
 */
public class SimpleMultiblock {

    public SimpleMultiblock(MultiblockElement.IBlockMatcher core) {
        addElement(new MultiblockElement(Vec3i.NULL_VECTOR, core));
    }

    private final List<MultiblockElement> multiblockElements = new ArrayList<>();

    public SimpleMultiblock addElement(MultiblockElement element) {
        multiblockElements.add(element);
        return this;
    }

    public SimpleMultiblock addElement(Vec3i offset, MultiblockElement.IBlockMatcher checker) {
        return addElement(new MultiblockElement(offset, checker));
    }

    public boolean match(IBlockAccess world, BlockPos pos) {
        return multiblockElements.stream().allMatch(element -> element.check(world.getBlockState(pos.add(element.getOffset()))));
    }

    public List<MultiblockElement> getMultiblockElements() {
        return multiblockElements;
    }
}
