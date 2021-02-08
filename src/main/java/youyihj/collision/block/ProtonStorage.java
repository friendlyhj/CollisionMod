package youyihj.collision.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import youyihj.collision.Collision;
import youyihj.collision.util.SingleItemDeviceBase;
import youyihj.collision.network.CollisionGuiHandler;
import youyihj.collision.tile.TileProtonStorage;

/**
 * @author youyihj
 */
public class ProtonStorage extends SingleItemDeviceBase.BlockModule {
    public ProtonStorage() {
        super("proton_storage", Material.IRON);
    }

    @Override
    public SingleItemDeviceBase.TileEntityModule createTileEntity(World world, IBlockState state) {
        return new TileProtonStorage();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            if (playerIn.isSneaking() && playerIn.getActiveItemStack().isEmpty()) {
                TileEntity te = worldIn.getTileEntity(pos);
                if (te instanceof TileProtonStorage) {
                    TileProtonStorage tep = (TileProtonStorage) te;
                    tep.transformIO();
                    tep.getIOType().sendMessageToPlayer(playerIn);
                }
            } else {
                playerIn.openGui(Collision.MODID, CollisionGuiHandler.PROTON_STORAGE, worldIn, pos.getX(), pos.getY(), pos.getZ());
            }
        }
        return true;
    }
}