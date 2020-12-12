package youyihj.collision.item;

import net.minecraft.item.Item;

import javax.annotation.Nonnull;

/**
 * @author youyihj
 */
public class ItemBase extends Item {
    private final String name;

    public ItemBase(String name, Properties properties) {
        super(properties);
        this.name = name;
    }

    public void register() {
        ItemRegistry.registerItem(this);
    }

    @Nonnull
    public String getBaseName() {
        return name;
    }
}
