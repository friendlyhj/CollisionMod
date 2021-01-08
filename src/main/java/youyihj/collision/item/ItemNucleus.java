package youyihj.collision.item;

import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import youyihj.collision.render.color.IItemColorized;
import youyihj.collision.util.Utils;
import youyihj.collision.util.annotation.DisableModelGenerator;

import java.util.HashMap;
import java.util.Map;

/**
 * @author youyihj
 */
@DisableModelGenerator
public class ItemNucleus extends ItemBase implements IItemColorized {
    public static final Map<String, NucleusEntry> nuclei = new HashMap<>();

    public ItemNucleus(String metalName, int color, int chance) {
        super(Utils.toLineString(metalName) + "_nucleus", new Properties());
        NucleusEntry entry = new NucleusEntry(metalName, color, chance);
        this.type = entry;
        nuclei.put(Utils.toLineString(metalName), entry);
    }

    private final NucleusEntry type;

    @Override
    public int getColor(ItemStack stack, int tintIndex) {
        return this.type.getColor();
    }

    public NucleusEntry getType() {
        return type;
    }

    @Override
    public ITextComponent getDisplayName(ItemStack stack) {
        return new TranslationTextComponent("item.collision.nucleus", new TranslationTextComponent("material.nucleus." + this.getType().getName()).getString());
    }

    public static class NucleusEntry {
        private static final NucleusEntry INVALID = new NucleusEntry("invalid", -1, 0);

        private final String name;
        private final int color;
        private final int chance;

        private NucleusEntry(String name, int color, int chance) {
            this.name = Utils.toLineString(name);
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

        public static NucleusEntry invalid() {
            return INVALID;
        }
    }
}
