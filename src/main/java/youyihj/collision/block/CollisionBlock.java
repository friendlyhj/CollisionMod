package youyihj.collision.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemBlock;
import youyihj.collision.Collision;
import youyihj.collision.model.IHasGeneratedModel;
import youyihj.collision.IRegistryObject;
import youyihj.collision.model.ModelGenerator;
import youyihj.collision.model.ModelType;
import youyihj.collision.creativetab.CollisionTab;
import youyihj.collision.item.ItemRegistryHandler;

import java.util.HashMap;
import java.util.List;

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
    public void getModelRLs(List<ModelResourceLocation> list) {
        list.add(new ModelResourceLocation(this.getRegistryName(), "inventory"));
    }

    @Override
    public ModelType getModelType() {
        return ModelType.BLOCK;
    }
}
