package youyihj.collision.item;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import youyihj.collision.Collision;
import youyihj.collision.core.IHasGeneratedModel;
import youyihj.collision.core.IRegistryObject;
import youyihj.collision.creativetab.CollisionTab;

import java.util.HashMap;


public class CollisionItem extends Item implements IRegistryObject, IHasGeneratedModel {
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
        Collision.needGenerateModels.add(this);
    }

    @Override
    public HashMap<Integer, ModelResourceLocation> getModelRLs() {
        HashMap<Integer, ModelResourceLocation> temp = new HashMap<>();
        temp.put(0, new ModelResourceLocation(this.getRegistryName(), "inventory"));
        return temp;
    }

    @Override
    public Class<?> getRegistryEntryType() {
        return this.getClass();
    }
}
