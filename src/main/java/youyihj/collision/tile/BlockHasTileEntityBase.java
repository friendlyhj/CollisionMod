package youyihj.collision.tile;

import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import youyihj.collision.block.BlockBase;

import javax.annotation.Nonnull;

/**
 * @author youyihj
 */
public abstract class BlockHasTileEntityBase extends BlockBase {

    public BlockHasTileEntityBase(String name, Properties properties) {
        super(name, properties);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nonnull
    public abstract TileEntity createTileEntity(BlockState state, IBlockReader world);

    public abstract Class<? extends TileEntity> getTileEntityClass();

    @Override
    public void register() {
        super.register();
        TileEntityRegistry.registerTileEntity(new TileEntityBound(this, getTileEntityClass(), getName()));
    }
}
