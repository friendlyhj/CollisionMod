package youyihj.collision.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import youyihj.collision.tile.ContainerNeutronStorage;
import youyihj.collision.tile.ContainerProtonStorage;
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
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!worldIn.isRemote) {
            getLinkedTileEntity(worldIn, pos).ifPresent(tileNeutronStorage -> {
                if (player.isSneaking() && player.getHeldItem(handIn).isEmpty()) {
                    tileNeutronStorage.transformIO();
                    tileNeutronStorage.getIOType().sendMessageToPlayer(player);
                } else {
                    NetworkHooks.openGui(((ServerPlayerEntity) player), tileNeutronStorage, packetBuffer -> {
                        packetBuffer.writeBlockPos(pos);
                    });
                }
            });
        }
        return ActionResultType.SUCCESS;
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
