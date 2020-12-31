package youyihj.collision.tile;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.nbt.CompoundNBT;
import youyihj.collision.block.BlockProtonStorage;
import youyihj.collision.util.IOType;
import youyihj.collision.util.SingleItemDeviceBase;

import javax.annotation.Nullable;

/**
 * @author youyihj
 */
public class TileProtonStorage extends SingleItemDeviceBase.TileEntityModule {
    private boolean output;

    public TileProtonStorage() {
        super(TileEntityRegistry.getTileEntityType(BlockProtonStorage.NAME));
    }

    public void transformIO() {
        output = !output;
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.putBoolean("output", output);
        return super.write(compound);
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        output = nbt.getBoolean("output");
    }

    @Override
    public IOType getIOType() {
        return output ? IOType.OUTPUT : IOType.INPUT;
    }

    @Override
    public boolean canEditIOType() {
        return true;
    }

    @Nullable
    @Override
    public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
        return new ContainerProtonStorage(BlockProtonStorage.NAME, p_createMenu_1_, p_createMenu_2_, this.getPos(), this.getWorld(), new SingleItemDeviceBase.EnergyBarNumber());
    }
}
