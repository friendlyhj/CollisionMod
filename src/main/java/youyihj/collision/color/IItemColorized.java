package youyihj.collision.color;

import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;

/**
 * @author youyihj
 */
public interface IItemColorized extends IItemProvider {
    int getColor(ItemStack stack, int tintIndex);
}
