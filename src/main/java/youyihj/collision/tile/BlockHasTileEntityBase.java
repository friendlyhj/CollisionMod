package youyihj.collision.tile;

import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import youyihj.collision.block.BlockBase;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

/**
 * @author youyihj
 */
public abstract class BlockHasTileEntityBase<T extends TileEntity> extends BlockBase {

    public BlockHasTileEntityBase(String name, Properties properties) {
        super(name, properties);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nonnull
    @Override
    public abstract T createTileEntity(BlockState state, IBlockReader world);

    public abstract Class<T> getTileEntityClass();

    @SuppressWarnings("unchecked")
    public Optional<T> getLinkedTileEntity(@Nullable IBlockReader world, @Nullable BlockPos pos) {
        if (world == null || pos == null)
            return Optional.empty();
        return (Optional<T>) Optional.ofNullable(world.getTileEntity(pos));
    }

    @Override
    public void register() {
        super.register();
        TileEntityRegistry.registerTileEntity(new TileEntityBound(this, getTileEntityClass(), getName()));
    }
}
