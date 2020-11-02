package youyihj.collision.compat.theoneprobe;

import mcjty.theoneprobe.api.*;
import mcjty.theoneprobe.apiimpl.styles.ItemStyle;
import mcjty.theoneprobe.apiimpl.styles.ProgressStyle;
import mcjty.theoneprobe.apiimpl.styles.TextStyle;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import youyihj.collision.core.Collision;
import youyihj.collision.core.EnergyStorageSerializable;
import youyihj.collision.core.SingleItemDeviceBase.TileEntityModule;

import java.awt.Color;

/**
 * @author youyihj
 */
public class TOPInfoHandler implements IProbeInfoProvider {
    private static final ProgressStyle ENERGY_CAP = new ProgressStyle()
            .numberFormat(NumberFormat.COMPACT)
            .filledColor(new Color(221, 0, 0).getRGB())
            .alternateFilledColor(Color.BLACK.getRGB())
            .backgroundColor(Color.BLACK.getRGB())
            .borderColor(Color.GRAY.getRGB())
            .suffix("FE");

    @Override
    public String getID() {
        return Collision.MODID + "-block";
    }

    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
        BlockPos pos = data.getPos();
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileEntityModule) {
            TileEntityModule tem = (TileEntityModule) te;
            EnergyStorageSerializable energy = tem.energy;
            ItemStack itemStack = tem.item.getStackInSlot(0);
            probeInfo.progress(energy.getEnergyStored(), energy.getMaxEnergyStored(), ENERGY_CAP);
            if (tem.canEditIOType()) probeInfo.text(TextStyleClass.LABEL + tem.getIOType().getString());
            if (!itemStack.isEmpty()) probeInfo.item(itemStack);
        }
    }
}
