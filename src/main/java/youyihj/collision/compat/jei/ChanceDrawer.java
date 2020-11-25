package youyihj.collision.compat.jei;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

import java.awt.Color;

/**
 * @author youyihj
 */
public class ChanceDrawer {
    public static void draw(Minecraft minecraft, int recipeWidth, int successChance) {
        if (successChance == 100) {
            return;
        }
        String text = I18n.format("message.collision.collider_success_chance", successChance) + " %";

        int width = minecraft.fontRenderer.getStringWidth(text);
        int x = (recipeWidth - width) / 2;
        int y = 56;

        minecraft.fontRenderer.drawString(text, x, y, Color.GRAY.getRGB());
    }
}
