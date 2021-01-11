package youyihj.collision.item;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import youyihj.collision.config.Configuration;
import youyihj.collision.render.color.IItemColorized;
import youyihj.collision.util.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author youyihj
 */
public class ItemNucleus extends ItemBase implements IItemColorized {
    public static final Map<String, NucleusEntry> nuclei = new HashMap<>();

    private ItemNucleus() {
        super("nucleus", new Properties());
        Configuration.nuclei.get().forEach(s -> {
            String[] strings = s.split(",");
            String name = strings[0].trim();
            nuclei.put(name, new ItemNucleus.NucleusEntry(name, Integer.parseInt(strings[1], 16), Integer.parseInt(strings[2])));
        });
    }

    public static final ItemNucleus INSTANCE = new ItemNucleus();

    public static ItemStack withType(NucleusEntry type) {
        ItemStack stack = new ItemStack(INSTANCE);
        if (type != NucleusEntry.invalid()) {
            stack.getOrCreateTag().putString("type", type.getName());
        }
        return stack;
    }

    public static List<ItemStack> getAllSubItems() {
        return nuclei.values().stream().map(ItemNucleus::withType).collect(Collectors.toList());
    }

    @Override
    public int getColor(ItemStack stack, int tintIndex) {
        return this.getType(stack).getColor();
    }

    public NucleusEntry getType(ItemStack stack) {
        return Optional.ofNullable(nuclei.get(stack.getOrCreateTag().getString("type"))).orElse(NucleusEntry.invalid());
    }

    @Override
    public ITextComponent getDisplayName(ItemStack stack) {
        return new TranslationTextComponent("item.collision.nucleus", new TranslationTextComponent("material.nucleus." + this.getType(stack).getName()).getString());
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (this.isInGroup(group)) {
            items.addAll(getAllSubItems());
        }
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

        @Override
        public String toString() {
            return "NucleusEntry{" +
                    "name='" + name + '\'' +
                    ", color=" + color +
                    ", chance=" + chance +
                    '}';
        }
    }
}
