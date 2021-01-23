package youyihj.collision.util;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.gui.GuiUtils;
import youyihj.collision.Collision;

/**
 * @author youyihj
 */
@OnlyIn(Dist.CLIENT)
public abstract class ScreenModule<T extends SingleItemDeviceBase.ContainerModule> extends ContainerScreen<T> {

    private static final ResourceLocation DEFAULT_TEXTURE = Collision.rl("textures/gui/single_item_device.png");

    protected final BlockPos pos;

    public ScreenModule(T screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        this.xSize = 176;
        this.ySize = 176;
        this.pos = screenContainer.getPos();
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        int left = (this.width - this.xSize) / 2;
        int top = (this.height - this.ySize) / 2;
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.minecraft.getTextureManager().bindTexture(getTexture());
        this.blit(matrixStack, left, top, 0, 0, xSize, ySize);
        int barWidth = 18;
        int barHeight = 54 - (this.container.getEnergy() * 54 / SingleItemDeviceBase.ENERGY_CAPACITY);
        this.blit(matrixStack, left + 14, top + 18, 176, 0, barWidth, barHeight);
    }

    public ResourceLocation getTexture() {
        return DEFAULT_TEXTURE;
    }

    @Override
    protected void renderHoveredTooltip(MatrixStack matrixStack, int mouseX, int mouseY) {
        super.renderHoveredTooltip(matrixStack, mouseX, mouseY);
        int left = (this.width - this.xSize) / 2;
        int top = (this.height - this.ySize) / 2;
        int x = mouseX - left;
        int y = mouseY - top;
        if (x >= 14 && x <= 32 && y >= 18 && y <= 72) {
            GuiUtils.drawHoveringText(matrixStack, Lists.newArrayList(
                    new TranslationTextComponent("tooltip.collision.energy_cap",
                            this.container.getEnergy(), SingleItemDeviceBase.ENERGY_CAPACITY)
            ), mouseX, mouseY, this.width, this.height, 300, this.font);
        }
    }
}
