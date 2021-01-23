package youyihj.collision.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import youyihj.collision.tile.ContainerNeutronStorage;
import youyihj.collision.tile.ContainerRegistry;
import youyihj.collision.tile.TileNeutronStorage;
import youyihj.collision.util.SingleItemDeviceBase;

import javax.annotation.Nonnull;

/**
 * @author youyihj
 */
public class BlockNeutronStorage extends SingleItemDeviceBase.BlockModule<TileNeutronStorage> {

    public BlockNeutronStorage() {
        super(NAME, Properties.create(Material.IRON));
    }

    public static final BlockNeutronStorage INSTANCE = new BlockNeutronStorage();
    public static final String NAME = "neutron_storage";

    @Override
    protected boolean extraWorkCondition(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit, TileNeutronStorage tileEntity) {
        return player.isSneaking() && player.getHeldItem(handIn).isEmpty();
    }

    @Override
    protected void extraWork(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit, TileNeutronStorage tileEntity) {
        tileEntity.transformIO();
        tileEntity.getIOType().sendMessageToPlayer(player);
    }

    @Nonnull
    @Override
    public TileNeutronStorage createTileEntity(BlockState state, IBlockReader world) {
        return new TileNeutronStorage();
    }

    @Override
    public Class<TileNeutronStorage> getTileEntityClass() {
        return TileNeutronStorage.class;
    }

    @Override
    public void register() {
        super.register();
        ContainerRegistry.registerContainer(NAME, ContainerNeutronStorage.class, new SingleItemDeviceBase.EnergyBarNumber());
    }
}
