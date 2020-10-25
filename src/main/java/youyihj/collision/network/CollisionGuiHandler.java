package youyihj.collision.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class CollisionGuiHandler implements IGuiHandler {
    public static final int STRUCTURE_BUILDER = 1;
    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == STRUCTURE_BUILDER) {
            return new ContainerStructureBuilder(player, world, new BlockPos(x, y, z));
        }
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == STRUCTURE_BUILDER) {
            return new GuiStructureBuilder(player, world, new BlockPos(x, y, z));
        }
        return null;
    }
}
