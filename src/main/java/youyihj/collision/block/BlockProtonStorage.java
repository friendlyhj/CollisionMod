package youyihj.collision.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
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
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!worldIn.isRemote) {
            getLinkedTileEntity(worldIn, pos).ifPresent(tileProtonStorage -> {
                if (player.isSneaking() && player.getHeldItem(handIn).isEmpty()) {
                    tileProtonStorage.transformIO();
                    tileProtonStorage.getIOType().sendMessageToPlayer(player);
                } else {
                    NetworkHooks.openGui(((ServerPlayerEntity) player), tileProtonStorage, packetBuffer -> {
                        packetBuffer.writeBlockPos(pos);
                    });
                }
            });
        }
        return ActionResultType.SUCCESS;
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
