package youyihj.collision.compat.jei;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

import java.awt.Color;

/**
 * @author youyihj
 */
public class ChanceDrawer {
    public static void draw(Minecraft minecraft, int recipeWidth, int successChance, int conversionChance) {
        int[] args = new int[] {successChance, conversionChance};
        String[] texts = new String[] {
                I18n.format("message.collision.collider_success_chance", successChance) + " %",
                I18n.format("message.collision.collider_convert_chance", conversionChance) + " %"
        };
        int y = 56;
        for (int i = 0; i < 2; i++) {
            if (args[i] == 100) {
                continue;
            }
            int width = minecraft.fontRenderer.getStringWidth(texts[i]);
            int x = (recipeWidth - width) / 2;
            minecraft.fontRenderer.drawString(texts[i], x, y, Color.GRAY.getRGB());
            y += 10;
        }
    }
}
