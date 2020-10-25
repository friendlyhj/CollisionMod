package youyihj.collision.network;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import youyihj.collision.block.ColliderBase;
import youyihj.collision.core.Collision;

@SideOnly(Side.CLIENT)
public class GuiStructureBuilder extends GuiContainer {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(Collision.MODID + ":textures/gui/structure_builder.png");

    private final BlockPos pos;

    public GuiStructureBuilder(EntityPlayer player, World world, BlockPos pos) {
        super(new ContainerStructureBuilder(player, world, pos));
        this.xSize = 176;
        this.ySize = 176;
        this.pos = pos;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        super.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        int left = (this.width - this.xSize) / 2;
        int top = (this.height - this.ySize) / 2;
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(TEXTURE);
        this.drawTexturedModalRect(left, top, 0, 0, xSize, ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String text = I18n.format("tile.collision.structure_builder.name");
        String error = I18n.format("gui.collision.no_collider");
        this.drawCenteredString(this.fontRenderer, text, xSize / 2, 6, -1);
        if (!(this.mc.world.getBlockState(this.pos.up()).getBlock() instanceof ColliderBase)) {
            this.drawCenteredString(this.fontRenderer, error, xSize / 2, 64, 0xffff0000);
        }
    }
}
