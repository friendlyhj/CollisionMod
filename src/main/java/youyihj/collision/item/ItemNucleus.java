package youyihj.collision.item;

import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import youyihj.collision.color.IItemColorized;
import youyihj.collision.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author youyihj
 */
public class ItemNucleus extends ItemBase implements IItemColorized {
    public static List<NucleusEntry> nuclei = new ArrayList<>();

    public ItemNucleus(String metalName, int color, int chance) {
        super(Utils.toLineString(metalName) + "_nucleus", new Properties());
        NucleusEntry entry = new NucleusEntry(metalName, color, chance);
        this.type = entry;
        nuclei.add(entry);
    }

    public final NucleusEntry type;

    @Override
    public int getColor(ItemStack stack, int tintIndex) {
        return this.type.getColor();
    }

    @Override
    public String getTexturePath() {
        return "nucleus";
    }

    public NucleusEntry getType() {
        return type;
    }

    @Override
    public ITextComponent getDisplayName(ItemStack stack) {
        return new TranslationTextComponent("item.collision.nucleus", new TranslationTextComponent("material.nucleus." + Utils.toLineString(this.getType().getName())).getString());
    }

    public static class NucleusEntry {
        private final String name;
        private final int color;
        private final int chance;

        private NucleusEntry(String name, int color, int chance) {
            this.name = name;
            this.color = color;
            this.chance = chance;
        }

        public String getName() {
            return name;
        }

        public int getColor() {
            return color;
        }

        public int getChance() {
            return chance;
        }
    }
}
