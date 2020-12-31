package youyihj.collision.tile;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.tileentity.TileEntityType;
import youyihj.collision.block.BlockNeutronStorage;
import youyihj.collision.util.IOType;
import youyihj.collision.util.SingleItemDeviceBase;

import javax.annotation.Nullable;

/**
 * @author youyihj
 */
public class TileNeutronStorage extends SingleItemDeviceBase.TileEntityModule {
    public TileNeutronStorage() {
        super(TileEntityRegistry.getTileEntityType(BlockNeutronStorage.NAME));
    }

    private boolean output;

    @Override
    public IOType getIOType() {
        return output ? IOType.OUTPUT : IOType.INPUT;
    }

    @Override
    public boolean canEditIOType() {
        return true;
    }

    public void transformIO() {
        output = !output;
    }

    @Nullable
    @Override
    public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
        return new ContainerNeutronStorage(BlockNeutronStorage.NAME, p_createMenu_1_, p_createMenu_2_, this.pos, this.world, new SingleItemDeviceBase.EnergyBarNumber());
    }
}
