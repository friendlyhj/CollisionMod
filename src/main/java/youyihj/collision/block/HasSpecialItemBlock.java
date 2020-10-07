package youyihj.collision.block;

import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import youyihj.collision.creativetab.CollisionTab;
import youyihj.collision.item.ItemRegistryHandler;

public abstract class HasSpecialItemBlock extends CollisionBlock implements IHasSpecialItemBlock {

    public HasSpecialItemBlock(String id, Material material) {
        super(id, material);
    }

    @Override
    public CollisionBlock getThis() {
        return this;
    }

    @Override
    public void register() {
        BlockRegistryHandler.blocks.add(getThis());
        BlockRegistryHandler.blockHashMap.put(getThis().getRegistryName().getResourcePath(), getThis());
        ItemBlock itemBlock = getItemBlock();
        itemBlock.setCreativeTab(CollisionTab.COLLISION_TAB);
        itemBlock.setRegistryName(getThis().getRegistryName());
        ItemRegistryHandler.itemBlocks.add(itemBlock);
        ItemRegistryHandler.itemBlockHashMap.put(getThis().getRegistryName().getResourcePath(), itemBlock);
    }
}
