package youyihj.collision.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.state.StateContainer;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import youyihj.collision.tile.ContainerHarvester;
import youyihj.collision.tile.ContainerRegistry;
import youyihj.collision.tile.TileHarvester;
import youyihj.collision.util.SingleItemDeviceBase;

import javax.annotation.Nonnull;

/**
 * @author youyihj
 */
public class BlockHarvester extends SingleItemDeviceBase.BlockModule<TileHarvester> {
    private BlockHarvester() {
        super("harvester", Properties.create(Material.IRON));
        this.setDefaultState(this.getStateContainer().getBaseState().with(HorizontalBlock.HORIZONTAL_FACING, Direction.NORTH));
    }

    public static final BlockHarvester INSTANCE = new BlockHarvester();

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!worldIn.isRemote) {
            getLinkedTileEntity(worldIn, pos).ifPresent(tileHarvester -> {
                if (player.isSneaking() && player.getHeldItem(handIn).isEmpty()) {
                    tileHarvester.transformWork();
                    player.sendStatusMessage(tileHarvester.getShowWorkTypeText(), true);
                } else {
                    NetworkHooks.openGui(((ServerPlayerEntity) player), tileHarvester, packetBuffer -> {
                        packetBuffer.writeBlockPos(tileHarvester.getPos());
                    });
                }
            });
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HorizontalBlock.HORIZONTAL_FACING);
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.toRotation(state.get(HorizontalBlock.HORIZONTAL_FACING)));
    }

    @Override
    public BlockState rotate(BlockState state, IWorld world, BlockPos pos, Rotation direction) {
        return state.with(HorizontalBlock.HORIZONTAL_FACING, direction.rotate(state.get(HorizontalBlock.HORIZONTAL_FACING)));
    }

    @Nonnull
    @Override
    public TileHarvester createTileEntity(BlockState state, IBlockReader world) {
        return new TileHarvester();
    }

    @Override
    public Class<TileHarvester> getTileEntityClass() {
        return TileHarvester.class;
    }

    @Override
    public void register() {
        super.register();
        ContainerRegistry.registerContainer("harvester", ContainerHarvester.class, new SingleItemDeviceBase.EnergyBarNumber());
    }
}
