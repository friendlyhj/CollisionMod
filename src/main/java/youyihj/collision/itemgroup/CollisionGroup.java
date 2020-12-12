package youyihj.collision.itemgroup;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import youyihj.collision.Collision;

/**
 * @author youyihj
 */
public class CollisionGroup extends ItemGroup {
    public static final ItemGroup INSTANCE = new CollisionGroup();

    private CollisionGroup() {
        super(Collision.MODID);
    }

    @Override
    public ItemStack createIcon() {
        return null;
    }
}
