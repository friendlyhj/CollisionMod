package youyihj.collision.block;

import net.minecraft.block.material.Material;

/**
 * @author youyihj
 */
public class ColliderBase extends BlockBase {
    private final int level;

    public ColliderBase(int level) {
        super(getRegistryName(level), Properties.create(Material.IRON).hardnessAndResistance(3.0f, 50.0f));
        this.level = level;
    }

    public static String getRegistryName(int level) {
        return "collider_lv" + level;
    }
}
