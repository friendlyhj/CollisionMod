package youyihj.collision.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemBlock;
import youyihj.collision.Collision;
import youyihj.collision.core.IHasGeneratedModel;
import youyihj.collision.core.IRegistryObject;
import youyihj.collision.core.ModelGenerator;
import youyihj.collision.core.ModelType;
import youyihj.collision.creativetab.CollisionTab;
import youyihj.collision.item.ItemRegistryHandler;

import java.util.HashMap;

public class CollisionBlock extends Block implements IRegistryObject, IHasGeneratedModel {
    public CollisionBlock(String id, Material material) {
        super(material);
        this.setUnlocalizedName(Collision.MODID + "." + id);
        this.setRegistryName(id);
        this.setCreativeTab(CollisionTab.COLLISION_TAB);
    }

    public ItemBlock getItemBlock() {
        return new ItemBlock(this);
    }

    @Override
    public void register() {
        BlockRegistryHandler.blocks.add(this);
        BlockRegistryHandler.blockHashMap.put(this.getRegistryName().getResourcePath(), this);
        ItemBlock itemBlock = this.getItemBlock();
        itemBlock.setCreativeTab(CollisionTab.COLLISION_TAB);
        itemBlock.setRegistryName(this.getRegistryName());
        ItemRegistryHandler.itemBlocks.add(itemBlock);
        ItemRegistryHandler.itemBlockHashMap.put(this.getRegistryName().getResourcePath(), itemBlock);
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
        return ModelType.BLOCK;
    }
}
