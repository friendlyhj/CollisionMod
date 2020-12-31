package youyihj.collision.util;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.IntNBT;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.energy.EnergyStorage;

/**
 * @author youyihj
 */
public class EnergyStorageSerializable extends EnergyStorage implements INBTSerializable<IntNBT> {
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


    public boolean consumeEnergy(int energy, boolean simulate) {
        if (this.energy < energy) {
            return false;
        }
        if (!simulate) {
            this.energy -= energy;
        }
        return true;
    }

    @Override
    public IntNBT serializeNBT() {
        if (this.energy < 0)
            this.energy = 0;
        return IntNBT.valueOf(this.energy);
    }

    @Override
    public void deserializeNBT(IntNBT nbt) {
        this.energy = Math.min(capacity, nbt.getInt());
    }
}
