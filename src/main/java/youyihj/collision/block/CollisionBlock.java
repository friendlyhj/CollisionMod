package youyihj.collision.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import youyihj.collision.Collision;
import youyihj.collision.creativeTab.CollisionTab;
import youyihj.collision.item.ItemRegistryHandler;

public class CollisionBlock extends Block {
    public CollisionBlock(String id, Material material) {
        super(material);
        this.setUnlocalizedName(Collision.MODID + "." + id);
        this.setRegistryName(id);
        this.setCreativeTab(CollisionTab.COLLISION_TAB);
    }

    public void register() {
        BlockRegistryHandler.blocks.add(this);
        BlockRegistryHandler.blockHashMap.put(this.getRegistryName().getResourcePath(), this);
        ItemBlock itemBlock = new ItemBlock(this);
        itemBlock.setCreativeTab(CollisionTab.COLLISION_TAB);
        itemBlock.setRegistryName(this.getRegistryName());
        ItemRegistryHandler.itemBlocks.add(itemBlock);
        ItemRegistryHandler.itemBlockHashMap.put(this.getRegistryName().getResourcePath(), itemBlock);
    }
}
