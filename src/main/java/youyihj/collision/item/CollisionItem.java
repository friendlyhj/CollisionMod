package youyihj.collision.item;

import net.minecraft.item.Item;
import youyihj.collision.Collision;
import youyihj.collision.creativeTab.CollisionTab;


public class CollisionItem extends Item {
    public CollisionItem(String id) {
        this.setUnlocalizedName(Collision.MODID + "." + id);
        this.setRegistryName(id);
        this.setCreativeTab(CollisionTab.COLLISION_TAB);
    }

    public void register() {
        ItemRegistryHandler.items.add(this);
        ItemRegistryHandler.itemHashMap.put(this.getRegistryName().getResourcePath(), this);
    }
}
