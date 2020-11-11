package youyihj.collision.util;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.energy.EnergyStorage;

/**
 * @author youyihj
 */
public class EnergyStorageSerializable extends EnergyStorage {

    public EnergyStorageSerializable(int capacity) {
        super(capacity);
    }

    public EnergyStorageSerializable(int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
    }

    public EnergyStorageSerializable(int capacity, int maxReceive, int maxExtract) {
        super(capacity, maxReceive, maxExtract);
    }

    public EnergyStorageSerializable(int capacity, int maxReceive, int maxExtract, int energy) {
        super(capacity, maxReceive, maxExtract, energy);
    }

    public void readFromNBT(NBTTagCompound nbt) {
        this.energy = nbt.getInteger("energy");
        if (energy > capacity) {
            this.energy = this.capacity;
        }
    }

    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        if (this.energy < 0) {
            this.energy = 0;
        }
        nbt.setInteger("energy", energy);
        return nbt;
    }

    public boolean consumeEnergy(int energy, boolean simulate) {
        if (this.energy < energy) {
            return false;
        }
        if (!simulate) {
            this.energy -= energy;
        }
        return true;
    }
}
