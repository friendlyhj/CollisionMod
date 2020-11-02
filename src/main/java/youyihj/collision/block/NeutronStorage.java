package youyihj.collision.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import youyihj.collision.core.Collision;
import youyihj.collision.core.SingleItemDeviceBase;
import youyihj.collision.network.CollisionGuiHandler;
import youyihj.collision.tile.TileNeutronStorage;

/**
 * @author youyihj
 */
public class NeutronStorage extends SingleItemDeviceBase.BlockModule {
    public NeutronStorage() {
        super("neutron_storage", Material.IRON);
    }

    @Override
    public SingleItemDeviceBase.TileEntityModule createTileEntity(World world, IBlockState state) {
        return new TileNeutronStorage();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            if (playerIn.isSneaking() && playerIn.getActiveItemStack().isEmpty()) {
                TileEntity te = worldIn.getTileEntity(pos);
                if (te != null && te instanceof TileNeutronStorage) {
                    TileNeutronStorage tep = (TileNeutronStorage) te;
                    tep.transformIO();
                    tep.getIOType().sendMessageToPlayer(playerIn);
                }
            } else {
                playerIn.openGui(Collision.MODID, CollisionGuiHandler.NEUTRON_STORAGE, worldIn, pos.getX(), pos.getY(), pos.getZ());
            }
        }
        return true;
    }
}
