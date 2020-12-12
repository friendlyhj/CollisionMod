package youyihj.collision.util;

import com.google.common.collect.Lists;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

/**
 * @author youyihj
 */
public class ItemStackList extends NonNullList<ItemStack> {
    public static ItemStackList create() {
        return new ItemStackList(Lists.newArrayList());
    }

    public static ItemStackList create(List<ItemStack> stacks) {
        return new ItemStackList(stacks);
    }

    public static ItemStackList create(int size) {
        return new ItemStackList(Arrays.asList(new ItemStack[size]));
    }

    private ItemStackList(List<ItemStack> list) {
        super(list, ItemStack.EMPTY);
    }

    @Nonnull
    @Override
    public ItemStack get(int index) {
        return super.get(index).copy();
    }
}
