package youyihj.collision.block.spawner;

import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import youyihj.collision.tile.TileMetalSpawner;

import javax.annotation.Nonnull;

/**
 * @author youyihj
 */
public class BlockMetalSpawner extends Spawner {
    public static final BlockMetalSpawner INSTANCE = new BlockMetalSpawner();

    private BlockMetalSpawner() {
        super("metal_spawner");
    }

    @Nonnull
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new TileMetalSpawner();
    }

    @Override
    public Class<? extends TileEntity> getTileEntityClass() {
        return TileMetalSpawner.class;
    }
}
