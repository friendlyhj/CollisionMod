package youyihj.collision.tile;

import net.minecraft.nbt.NBTTagCompound;
import youyihj.collision.core.IOType;
import youyihj.collision.core.SingleItemDeviceBase;

/**
 * @author youyihj
 */
public class TileProtonStorage extends SingleItemDeviceBase.TileEntityModule {
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

    public void transformIO() {
        output = !output;
    }
}
