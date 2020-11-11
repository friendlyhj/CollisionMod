package youyihj.collision.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import youyihj.collision.Collision;
import youyihj.collision.util.SingleItemDeviceBase;
import youyihj.collision.network.CollisionGuiHandler;
import youyihj.collision.tile.TileHarvester;

/**
 * @author youyihj
 */
public class BlockHarvester extends SingleItemDeviceBase.BlockModule {
    public BlockHarvester() {
        super("harvester", Material.IRON);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    }

    private static final IProperty<EnumFacing> FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getHorizontalIndex();
    }

    @Override
    @SuppressWarnings("deprecation")
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta));
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @Override
    public SingleItemDeviceBase.TileEntityModule createTileEntity(World world, IBlockState state) {
        return new TileHarvester();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            if (playerIn.isSneaking() && playerIn.getActiveItemStack().isEmpty()) {
                TileEntity te = worldIn.getTileEntity(pos);
                if (te != null && te instanceof TileHarvester) {
                    TileHarvester tep = (TileHarvester) te;
                    tep.transformWork();
                    playerIn.sendStatusMessage(tep.getShowWorkTypeText(), false);
                }
            } else {
                playerIn.openGui(Collision.MODID, CollisionGuiHandler.HARVESTER, worldIn, pos.getX(), pos.getY(), pos.getZ());
            }
        }
        return true;
    }

    @Override
    public boolean isGeneratingModel() {
        return false;
    }
}
