package youyihj.collision.compat.theoneprobe;

import mcjty.theoneprobe.api.*;
import mcjty.theoneprobe.apiimpl.styles.ProgressStyle;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import youyihj.collision.Collision;
import youyihj.collision.util.EnergyStorageSerializable;
import youyihj.collision.util.SingleItemDeviceBase.TileEntityModule;
import youyihj.collision.tile.TileHarvester;

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
            if (tem instanceof TileHarvester)
                probeInfo.text(TextStyleClass.LABEL + ((TileHarvester) tem).getShowWorkTypeText().getUnformattedText());
            if (!itemStack.isEmpty()) probeInfo.item(itemStack);
        }
    }
}
