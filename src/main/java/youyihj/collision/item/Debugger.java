package youyihj.collision.item;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class Debugger extends CollisionItem{
    public Debugger() {
        super("debugger");
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (worldIn.isRemote) return EnumActionResult.SUCCESS;
        IBlockState blockState = worldIn.getBlockState(pos);
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        player.sendMessage(new TextComponentString(blockState.getBlock().getRegistryName().toString()));
        player.sendMessage(new TextComponentString(String.valueOf(blockState.getBlock().getMetaFromState(blockState))));
        if (tileEntity != null) {
            NBTTagCompound tag = new NBTTagCompound();
            player.sendMessage(new TextComponentString(tileEntity.writeToNBT(tag).toString()));
        }
        return EnumActionResult.SUCCESS;
    }
}
