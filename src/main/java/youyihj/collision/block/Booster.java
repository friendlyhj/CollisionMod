package youyihj.collision.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import youyihj.collision.block.absorber.Absorber;
import youyihj.collision.block.absorber.Neutron;
import youyihj.collision.block.absorber.Proton;
import youyihj.collision.item.ItemNucleus;
import youyihj.collision.render.INeedRenderUpdate;
import youyihj.collision.render.RenderUpdateHandler;
import youyihj.collision.render.color.IBlockColorized;
import youyihj.collision.tile.BlockHasTileEntityBase;
import youyihj.collision.tile.TileBooster;
import youyihj.collision.util.annotation.DisableModelGenerator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

/**
 * @author youyihj
 */
@DisableModelGenerator
public class Booster extends BlockHasTileEntityBase<TileBooster> implements INeedRenderUpdate, IBlockColorized {
    private Booster() {
        super("booster", Properties.create(Material.IRON).hardnessAndResistance(5.0f, 100.0f));
    }

    public static final Booster INSTANCE = new Booster();

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        ItemStack handItem = player.getHeldItem(handIn);
        if (handItem.getItem() instanceof ItemNucleus) {
            Optional<TileBooster> teo = getLinkedTileEntity(worldIn, pos);
            if (!teo.isPresent() || teo.map(TileBooster::isFull).get())
                return ActionResultType.FAIL;
            TileBooster te = teo.get();
            te.setNucleusType(((ItemNucleus) handItem.getItem()).getType());
            handItem.shrink(1);
            te.setFull(true);
            if (worldIn.isRemote) {
                RenderUpdateHandler.mark(pos);
            }
            neighborChanged(state, worldIn, pos, state.getBlock(), pos, false);
            return ActionResultType.CONSUME;
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
        return getLinkedTileEntity(world, pos).map(te -> te.getNucleusType().getColor()).orElse(-1);
    }

    @Override
    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        if (!worldIn.isRemote) {
            Optional<TileBooster> tileBooster = getLinkedTileEntity(worldIn, pos);
            ITag<Item> oreTag = ItemTags.getCollection().get(new ResourceLocation("forge", "ores/" + tileBooster.map(te -> te.getNucleusType().getName()).orElse("")));
            if (oreTag != null) {
                Block ore = null;
                for (Item item : oreTag.getAllElements()) {
                    if (item instanceof BlockItem) {
                        ore = ((BlockItem) item).getBlock();
                        break;
                    }
                }
                if (ore == null)
                    return;
                int n = 0;
                int p = 0;
                for (int i = 0; i < 4; i++) {
                    Block block = worldIn.getBlockState(pos.offset(Direction.byHorizontalIndex(i))).getBlock();
                    if (block == Neutron.INSTANCE) {
                        n++;
                    } else if (block == Proton.INSTANCE) {
                        p++;
                    }
                }
                if (n == 2 && p == 2) {
                    worldIn.setBlockState(pos, ore.getDefaultState());
                    for (int i = 0; i < 4; i++) {
                        BlockPos posOffset = pos.offset(Direction.byHorizontalIndex(i));
                        Absorber absorber = (Absorber) worldIn.getBlockState(posOffset).getBlock();
                        absorber.transform(worldIn, posOffset);
                    }
                }
            }
        }
    }

    @Override
    public boolean needRenderUpdate(IWorldReader world, BlockPos pos) {
        return getLinkedTileEntity(world, pos).map(TileBooster::isFull).orElse(false);
    }

    @Override
    public boolean isDelay() {
        return true;
    }

}
