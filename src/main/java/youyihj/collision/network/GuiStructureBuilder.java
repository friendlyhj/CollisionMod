package youyihj.collision.network;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import youyihj.collision.block.ColliderBase;
import youyihj.collision.core.Collision;
import youyihj.collision.core.SingleItemDeviceBase;

@SideOnly(Side.CLIENT)
public class GuiStructureBuilder extends SingleItemDeviceBase.GuiContainerModule {

    public GuiStructureBuilder(SingleItemDeviceBase.ContainerModule inventorySlotsIn) {
        super(inventorySlotsIn);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        String error = I18n.format("gui.collision.no_collider");
        BlockPos posOffset = pos.up();
        World world = this.mc.world;
        while (!(world.getBlockState(posOffset).getBlock() instanceof ColliderBase)) {
            if (!world.isAirBlock(posOffset) || world.isOutsideBuildHeight(posOffset)) {
                this.drawCenteredString(this.fontRenderer, error, xSize / 2, 64, 0xffff0000);
                return;
            }
            posOffset = posOffset.up();
        }
    }

    @Override
    public String getTitle() {
        return I18n.format("tile.collision.structure_builder.name");
    }
}
