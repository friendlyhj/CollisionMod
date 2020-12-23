package youyihj.collision.block.spawner;

import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import youyihj.collision.tile.TileGemSpawner;

import javax.annotation.Nonnull;

/**
 * @author youyihj
 */
public class BlockGemSpawner extends Spawner {
    public static final BlockGemSpawner INSTANCE = new BlockGemSpawner();

    private BlockGemSpawner() {
        super("gem_spawner");
    }

    @Nonnull
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new TileGemSpawner();
    }

    @Override
    public Class<? extends TileEntity> getTileEntityClass() {
        return TileGemSpawner.class;
    }
}
