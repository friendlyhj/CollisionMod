package youyihj.collision.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import youyihj.collision.util.SingleItemDeviceBase;

public class ContainerStructureBuilder extends SingleItemDeviceBase.ContainerModule {
    public ContainerStructureBuilder(EntityPlayer player, World world, BlockPos pos) {
        super(player, world, pos);
    }
}
