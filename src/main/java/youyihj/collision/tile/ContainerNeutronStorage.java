package youyihj.collision.tile;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import youyihj.collision.util.SingleItemDeviceBase;

/**
 * @author youyihj
 */
public class ContainerNeutronStorage extends SingleItemDeviceBase.ContainerModule {
    public ContainerNeutronStorage(String name, int id, PlayerInventory inventoryPlayer, BlockPos pos, World world, SingleItemDeviceBase.EnergyBarNumber intArray) {
        super(name, id, inventoryPlayer, pos, world, intArray);
    }
}
