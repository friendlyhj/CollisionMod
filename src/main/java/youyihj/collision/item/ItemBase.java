package youyihj.collision.item;

import net.minecraft.item.Item;
import youyihj.collision.itemgroup.CollisionGroup;

import javax.annotation.Nonnull;

/**
 * @author youyihj
 */
public class ItemBase extends Item {
    private final String name;

    public ItemBase(String name, Properties properties) {
        super(properties.group(CollisionGroup.INSTANCE));
        this.name = name;
    }

    public void register() {
        ItemRegistry.registerItem(this);
    }

    @Nonnull
    public String getBaseName() {
        return name;
    }

    public String getTexturePath() {
        return this.getRegistryName().getPath();
    }
}
