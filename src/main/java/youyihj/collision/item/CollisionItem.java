package youyihj.collision.item;

import net.minecraft.item.Item;
import youyihj.collision.Collision;
import youyihj.collision.IRegistryObject;
import youyihj.collision.creativetab.CollisionTab;


public class CollisionItem extends Item implements IRegistryObject {
    public CollisionItem(String id) {
        super();
        this.setUnlocalizedName(Collision.MODID + "." + id);
        this.setRegistryName(id);
        this.setCreativeTab(CollisionTab.COLLISION_TAB);
    }

    @Override
    public void register() {
        ItemRegistryHandler.items.add(this);
        ItemRegistryHandler.itemHashMap.put(this.getRegistryName().getResourcePath(), this);
    }
}
