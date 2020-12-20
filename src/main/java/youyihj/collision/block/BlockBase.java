package youyihj.collision.block;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import youyihj.collision.item.ItemRegistry;
import youyihj.collision.itemgroup.CollisionGroup;

import java.util.function.Supplier;

/**
 * @author youyihj
 */
public class BlockBase extends Block {
    private final String name;

    public BlockBase(String name, Properties properties) {
        super(properties);
        this.name = name;
    }

    public boolean isGenerateModel() {
        return true;
    }

    public String getName() {
        return name;
    }

    public boolean enableBlockLootGenerator() {
        return true;
    }

    public Supplier<BlockItem> getBlockItemSupplier() {
        return () -> new BlockItem(this, new Item.Properties().group(CollisionGroup.INSTANCE));
    }

    public void register() {
        RegistryObject<Block> block = BlockRegistry.registerBlock(this);
        ItemRegistry.registerBlockItem(this, block);
    }
}
