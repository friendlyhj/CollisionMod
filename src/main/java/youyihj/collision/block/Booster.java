package youyihj.collision.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import youyihj.collision.item.ItemNucleus;
import youyihj.collision.render.INeedRenderUpdate;
import youyihj.collision.render.RenderUpdateHandler;
import youyihj.collision.render.color.IBlockColorized;
import youyihj.collision.tile.BlockHasTileEntityBase;
import youyihj.collision.tile.TileBooster;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

/**
 * @author youyihj
 */
public class Booster extends BlockHasTileEntityBase<TileBooster> implements INeedRenderUpdate, IBlockColorized {
    public Booster() {
        super("booster", Properties.create(Material.IRON).hardnessAndResistance(5.0f, 100.0f));
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        ItemStack handItem = player.getHeldItem(handIn);
        if (handItem.getItem() instanceof ItemNucleus) {
            TileBooster te = getLinkedTileEntity(worldIn, pos);
            if (te == null || te.isFull())
                return ActionResultType.FAIL;
            handItem.shrink(1);
            te.setFull(true);
            te.setNucleusType(((ItemNucleus) handItem.getItem()).getType());
            if (worldIn.isRemote) {
                RenderUpdateHandler.mark(pos);
            }
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.PASS;
    }

    @Nonnull
    @Override
    public TileBooster createTileEntity(BlockState state, IBlockReader world) {
        return new TileBooster();
    }

    @Override
    public Class<TileBooster> getTileEntityClass() {
        return TileBooster.class;
    }

    @Override
    public int getColor(BlockState state, @Nullable IBlockDisplayReader world, @Nullable BlockPos pos, int tintIndex) {
        return Optional.ofNullable(getLinkedTileEntity(world, pos)).map(te -> te.getNucleusType().getColor()).orElse(-1);
    }

    @Override
    public Block getSelf() {
        return this;
    }

    @Override
    public boolean needRenderUpdate(World world, BlockPos pos) {
        return Optional.ofNullable(getLinkedTileEntity(world, pos)).map(TileBooster::isFull).orElse(false);
    }

    @Override
    public boolean isDelay() {
        return true;
    }

    @Override
    public boolean isGenerateModel() {
        return false;
    }
}
