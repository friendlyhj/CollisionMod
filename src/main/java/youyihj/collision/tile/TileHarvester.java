package youyihj.collision.tile;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import youyihj.collision.util.IOType;
import youyihj.collision.util.SingleItemDeviceBase;

import javax.annotation.Nullable;

/**
 * @author youyihj
 */
public class TileHarvester extends SingleItemDeviceBase.TileEntityModule implements ITickableTileEntity {
    public TileHarvester() {
        super(TileEntityRegistry.getTileEntityType("harvester"));
    }

    @Override
    public void tick() {

    }

    @Override
    public IOType getIOType() {
        return IOType.BOTH;
    }

    @Override
    public boolean canEditIOType() {
        return false;
    }

    @Nullable
    @Override
    public Container createMenu(int syncID, PlayerInventory playerInventory, PlayerEntity player) {
        return new ContainerHarvester("harvester", syncID, playerInventory, this.getPos(), this.getWorld(), new SingleItemDeviceBase.EnergyBarNumber());
    }
}
