package youyihj.collision.block.spawner;

import net.minecraft.block.BlockState;
import net.minecraft.world.IBlockReader;
import youyihj.collision.data.DisableBlockLootGenerator;
import youyihj.collision.tile.TileGemSpawner;

import javax.annotation.Nonnull;

/**
 * @author youyihj
 */
@DisableBlockLootGenerator
public class BlockGemSpawner extends Spawner<TileGemSpawner> {
    public static final BlockGemSpawner INSTANCE = new BlockGemSpawner();

    private BlockGemSpawner() {
        super("gem_spawner");
    }

    @Nonnull
    @Override
    public TileGemSpawner createTileEntity(BlockState state, IBlockReader world) {
        return new TileGemSpawner();
    }

    @Override
    public Class<TileGemSpawner> getTileEntityClass() {
        return TileGemSpawner.class;
    }
}
