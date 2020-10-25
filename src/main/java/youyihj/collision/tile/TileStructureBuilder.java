package youyihj.collision.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class TileStructureBuilder extends TileEntity implements ITickable {
    private final ItemStackHandler in = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            TileStructureBuilder.this.markDirty();
        }
    };

    @Override
    public void update() {

    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        this.in.deserializeNBT(compound.getCompoundTag("in"));
        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setTag("in", this.in.serializeNBT());
        return super.writeToNBT(compound);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        Capability<IItemHandler> iItemHandlerCapability = CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
        return iItemHandlerCapability.equals(capability) || super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        Capability<IItemHandler> iItemHandlerCapability = CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
        if (iItemHandlerCapability.equals(capability)) {
            if (facing != null && facing.getHorizontalIndex() != -1) {
                return iItemHandlerCapability.cast(this.in);
            }
        }
        return super.getCapability(capability, facing);
    }
}
