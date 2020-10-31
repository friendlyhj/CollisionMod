package youyihj.collision.network;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import youyihj.collision.block.ColliderBase;
import youyihj.collision.core.Collision;
import youyihj.collision.core.SingleItemDeviceBase;

@SideOnly(Side.CLIENT)
public class GuiStructureBuilder extends SingleItemDeviceBase.GuiContainerModule {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(Collision.MODID + ":textures/gui/structure_builder.png");

    public GuiStructureBuilder(SingleItemDeviceBase.ContainerModule inventorySlotsIn) {
        super(inventorySlotsIn);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        String error = I18n.format("gui.collision.no_collider");
        if (!(this.mc.world.getBlockState(this.pos.up()).getBlock() instanceof ColliderBase)) {
            this.drawCenteredString(this.fontRenderer, error, xSize / 2, 64, 0xffff0000);
        }
    }

    @Override
    public String getTitle() {
        return I18n.format("tile.collision.structure_builder.name");
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }
}
