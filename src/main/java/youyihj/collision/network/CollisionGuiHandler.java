package youyihj.collision.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class CollisionGuiHandler implements IGuiHandler {
    public static final int STRUCTURE_BUILDER = 1;
    public static final int PROTON_STORAGE = 2;
    public static final int NEUTRON_STORAGE = 3;
    public static final int HARVESTER = 4;

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case STRUCTURE_BUILDER:
                return new ContainerStructureBuilder(player, world, new BlockPos(x, y, z));
            case PROTON_STORAGE:
                return new ContainerProtonStorage(player, world, new BlockPos(x, y, z));
            case NEUTRON_STORAGE:
                return new ContainerNeutronStorage(player, world, new BlockPos(x, y, z));
            case HARVESTER:
                return new ContainerHarvester(player, world, new BlockPos(x, y, z));
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case STRUCTURE_BUILDER:
                return new GuiStructureBuilder(new ContainerStructureBuilder(player, world, new BlockPos(x, y, z)));
            case PROTON_STORAGE:
                return new GuiProtonStorage(new ContainerProtonStorage(player, world, new BlockPos(x, y, z)));
            case NEUTRON_STORAGE:
                return new GuiNeutronStorage(new ContainerNeutronStorage(player, world, new BlockPos(x, y, z)));
            case HARVESTER:
                return new GuiHarvester(new ContainerHarvester(player, world, new BlockPos(x, y, z)));
            default:
                return null;
        }
    }
}
