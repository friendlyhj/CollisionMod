package youyihj.collision.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
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
