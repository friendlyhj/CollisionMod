package youyihj.collision.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import youyihj.collision.core.SingleItemDeviceBase;

/**
 * @author youyihj
 */
public class ContainerNeutronStorage extends SingleItemDeviceBase.ContainerModule {
    public ContainerNeutronStorage(EntityPlayer player, World world, BlockPos pos) {
        super(player, world, pos);
    }
}
