package youyihj.collision.block;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import youyihj.collision.item.ItemRegistry;

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
        return () -> new BlockItem(this, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS));
    }

    public RegistryObject<Block> register() {
        RegistryObject<Block> block = BlockRegistry.registerBlock(this);
        ItemRegistry.registerBlockItem(this, block);
        return block;
    }
}
