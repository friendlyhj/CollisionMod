package youyihj.collision.tile;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import youyihj.collision.config.Configuration;
import youyihj.collision.item.ItemNucleus;
import youyihj.collision.item.ItemRegistry;
import youyihj.collision.util.WeightList;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author youyihj
 */
public class TileMetalSpawner extends TileSpawner {
    public TileMetalSpawner() {
        super(TileEntityRegistry.getTileEntityType("metal_spawner"));
    }

    private static WeightList<Item> pool;

    public static void initMetalList() {
        Map<Item, Integer> temp = new HashMap<>();
        ItemNucleus.nuclei.values().forEach(nucleusEntry -> temp.put(ItemRegistry.getItem(nucleusEntry.getName() + "_nucleus"), nucleusEntry.getChance()));
        pool = WeightList.create(temp);
    }

    @Override
    protected int getLimitedAmount() {
        return Configuration.metalSpawnerSpawnAmount.get();
    }

    @Override
    protected int getSpeed() {
        return Configuration.metalSpawnerSpawnSpeed.get();
    }

    @Override
    protected ItemStack getNextSpawnItem(Random random) {
        return new ItemStack(pool.get(random));
    }
}
