package youyihj.collision.block.spawner;

import net.minecraft.block.BlockState;
import net.minecraft.world.IBlockReader;
import youyihj.collision.tile.TileMetalSpawner;
import youyihj.collision.util.annotation.DisableBlockLootGenerator;

import javax.annotation.Nonnull;

/**
 * @author youyihj
 */
@DisableBlockLootGenerator
public class BlockMetalSpawner extends Spawner<TileMetalSpawner> {
    public static final BlockMetalSpawner INSTANCE = new BlockMetalSpawner();

    private BlockMetalSpawner() {
        super("metal_spawner");
    }

    @Nonnull
    @Override
    public TileMetalSpawner createTileEntity(BlockState state, IBlockReader world) {
        return new TileMetalSpawner();
    }

    @Override
    public Class<TileMetalSpawner> getTileEntityClass() {
        return TileMetalSpawner.class;
    }
}
