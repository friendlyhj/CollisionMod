package youyihj.collision.tile;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import youyihj.collision.config.Configuration;
import youyihj.collision.util.WeightList;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author youyihj
 */
public class TileGemSpawner extends TileSpawner {
    public TileGemSpawner() {
        super(TileEntityRegistry.getTileEntityType("gem_spawner"));
    }

    private static WeightList<Item> pool;

    public static void initGemList() {
        Map<Item, Integer> temp = new HashMap<>();
        for (String s : Configuration.gemSpawnerItemPool.get()) {
            String[] sx = s.split(",");
            temp.put(ForgeRegistries.ITEMS.getValue(new ResourceLocation(sx[0].trim())), Integer.parseInt(sx[1].trim()));
        }
        pool = WeightList.create(temp);
    }

    @Override
    protected int getLimitedAmount() {
        return Configuration.gemSpawnerSpawnAmount.get();
    }

    @Override
    protected int getSpeed() {
        return Configuration.gemSpawnerSpawnSpeed.get();
    }

    @Override
    protected ItemStack getNextSpawnItem(Random random) {
        return new ItemStack(pool.get(random));
    }
}
