package youyihj.collision.item;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import youyihj.collision.core.Collision;
import youyihj.collision.model.IHasGeneratedModel;
import youyihj.collision.core.IRegistryObject;
import youyihj.collision.model.ModelGenerator;
import youyihj.collision.model.ModelType;
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
        ModelGenerator.needGenerateModels.add(this);
    }

    @Override
    public HashMap<Integer, ModelResourceLocation> getModelRLs() {
        HashMap<Integer, ModelResourceLocation> temp = new HashMap<>();
        temp.put(0, new ModelResourceLocation(this.getRegistryName(), "inventory"));
        return temp;
    }

    @Override
    public ModelType getModelType() {
        return ModelType.ITEM;
    }
}
