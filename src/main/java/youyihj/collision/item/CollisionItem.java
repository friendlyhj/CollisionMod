package youyihj.collision.item;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import youyihj.collision.Collision;
import youyihj.collision.model.IHasGeneratedModel;
import youyihj.collision.IRegistryObject;
import youyihj.collision.model.ModelGenerator;
import youyihj.collision.model.ModelType;
import youyihj.collision.creativetab.CollisionTab;

import java.util.HashMap;
import java.util.List;


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
        ModelGenerator.registerModel(this);
    }

    @Override
    public void getModelRLs(List<ModelResourceLocation> list) {
        list.add(new ModelResourceLocation(this.getRegistryName(), "inventory"));
    }

    @Override
    public ModelType getModelType() {
        return ModelType.ITEM;
    }
}
