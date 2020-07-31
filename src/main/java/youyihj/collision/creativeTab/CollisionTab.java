package youyihj.collision.creativeTab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import youyihj.collision.item.TestItem;

import javax.annotation.Nonnull;

public class CollisionTab extends CreativeTabs {
    public CollisionTab() {
        super("collision");
    }

    public static final CollisionTab COLLISION_TAB = new CollisionTab();

    @Override
    @Nonnull
    public ItemStack getTabIconItem() {
        return new ItemStack(new TestItem());
    }
}
