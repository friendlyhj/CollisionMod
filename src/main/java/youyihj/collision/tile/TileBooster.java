package youyihj.collision.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileBooster extends TileEntity {
    private int type;
    private boolean full;

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        type = compound.getInteger("type");
        full = compound.getBoolean("full");
        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger("type", type);
        compound.setBoolean("full", full);
        return super.writeToNBT(compound);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isFull() {
        return full;
    }

    public void setFull(boolean full) {
        this.full = full;
    }

}
