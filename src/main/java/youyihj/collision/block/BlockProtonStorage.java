package youyihj.collision.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import youyihj.collision.tile.ContainerProtonStorage;
import youyihj.collision.tile.ContainerRegistry;
import youyihj.collision.tile.TileProtonStorage;
import youyihj.collision.util.SingleItemDeviceBase;

import javax.annotation.Nonnull;

/**
 * @author youyihj
 */
public class BlockProtonStorage extends SingleItemDeviceBase.BlockModule<TileProtonStorage> {
    private BlockProtonStorage() {
        super(NAME, Properties.create(Material.IRON));
    }

    public static final BlockProtonStorage INSTANCE = new BlockProtonStorage();
    public static final String NAME = "proton_storage";

    @Override
    protected boolean extraWorkCondition(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit, TileProtonStorage tileEntity) {
        return player.isSneaking() && player.getHeldItem(handIn).isEmpty();
    }

    @Override
    protected void extraWork(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit, TileProtonStorage tileEntity) {
        tileEntity.transformIO();
        tileEntity.getIOType().sendMessageToPlayer(player);
    }

    @Nonnull
    @Override
    public TileProtonStorage createTileEntity(BlockState state, IBlockReader world) {
        return new TileProtonStorage();
    }

    @Override
    public Class<TileProtonStorage> getTileEntityClass() {
        return TileProtonStorage.class;
    }

    @Override
    public void register() {
        super.register();
        ContainerRegistry.registerContainer(NAME, ContainerProtonStorage.class, new SingleItemDeviceBase.EnergyBarNumber());
    }
}
