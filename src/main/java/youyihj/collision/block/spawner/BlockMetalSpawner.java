package youyihj.collision.block.spawner;

import net.minecraft.block.BlockState;
import net.minecraft.world.IBlockReader;
import youyihj.collision.tile.TileMetalSpawner;

import javax.annotation.Nonnull;

/**
 * @author youyihj
 */
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
