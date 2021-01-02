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
import youyihj.collision.recipe.ColliderRecipeCache;
import youyihj.collision.tile.ContainerRegistry;
import youyihj.collision.tile.ContainerStructureBuilder;
import youyihj.collision.tile.TileStructureBuilder;
import youyihj.collision.util.SingleItemDeviceBase;

import javax.annotation.Nonnull;

public class BlockStructureBuilder extends SingleItemDeviceBase.BlockModule<TileStructureBuilder> {
    private BlockStructureBuilder() {
        super(NAME, Properties.create(Material.IRON));
    }

    public static final String NAME = "structure_builder";
    public static final BlockStructureBuilder INSTANCE = new BlockStructureBuilder();

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!worldIn.isRemote) {
            getLinkedTileEntity(worldIn, pos).ifPresent(tileStructureBuilder -> {
                NetworkHooks.openGui(((ServerPlayerEntity) player), tileStructureBuilder, packetBuffer -> {
                    packetBuffer.writeBlockPos(tileStructureBuilder.getPos());
                });
            });
        }
        return ActionResultType.SUCCESS;
    }

    @Nonnull
    @Override
    public TileStructureBuilder createTileEntity(BlockState state, IBlockReader world) {
        return new TileStructureBuilder();
    }

    @Override
    public Class<TileStructureBuilder> getTileEntityClass() {
        return TileStructureBuilder.class;
    }

    @Override
    public void register() {
        super.register();
        ContainerRegistry.registerContainer(NAME, ContainerStructureBuilder.class, new SingleItemDeviceBase.EnergyBarNumber());
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        super.onReplaced(state, worldIn, pos, newState, isMoving);
        ColliderRecipeCache.removeCachePos(pos);
    }
}
