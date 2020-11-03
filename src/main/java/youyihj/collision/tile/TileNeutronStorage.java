package youyihj.collision.tile;

import net.minecraft.nbt.NBTTagCompound;
import youyihj.collision.util.IOType;
import youyihj.collision.util.SingleItemDeviceBase;

/**
 * @author youyihj
 */
public class TileNeutronStorage extends SingleItemDeviceBase.TileEntityModule {
    private boolean output;

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        output = compound.getBoolean("output");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setBoolean("output", output);
        return super.writeToNBT(compound);
    }

    @Override
    public IOType getIOType() {
        return output ? IOType.OUTPUT : IOType.INPUT;
    }

    public void setOutput(boolean output) {
        this.output = output;
    }

    public boolean isOutput() {
        return output;
    }

    public void transformIO() {
        output = !output;
    }

    @Override
    public boolean canEditIOType() {
        return true;
    }
}
