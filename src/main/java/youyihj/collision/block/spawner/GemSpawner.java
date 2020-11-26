package youyihj.collision.block.spawner;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import youyihj.collision.Configuration;

import java.util.*;

public class GemSpawner extends Spawner {
    public GemSpawner() {
        super("gem_spawner");
    }

    private static final Map<ItemStack, Integer> spawnItemPool = new HashMap<>();
    private static final List<ItemStack> itemStacks = new ArrayList<>();
    private static final List<Integer> list = new ArrayList<>();
    private static int bound = 0;

    public static void addItem(ItemStack itemStack, int wight) {
        spawnItemPool.put(itemStack, wight);
    }

    public static void removeItem(ItemStack itemStack) {
        for (ItemStack item : spawnItemPool.keySet()) {
            if (item.isItemEqual(itemStack)) {
                spawnItemPool.remove(item);
                return;
            }
        }
    }

    public static void removeAll() {
        spawnItemPool.clear();
    }

    static {
        addItem(new ItemStack(Items.REDSTONE), 1000);
        addItem(new ItemStack(Items.GLOWSTONE_DUST), 800);
        addItem(new ItemStack(Items.DYE, 1, 4), 400);
        addItem(new ItemStack(Items.QUARTZ), 400);
        addItem(new ItemStack(Items.DIAMOND), 50);
        addItem(new ItemStack(Items.EMERALD), 50);
    }

    @Override
    public List<ItemStack> getSpawnItems(World world) {
        List<ItemStack> temp = new ArrayList<>();
        for (int i = 0; i < Configuration.spawnerConfig.gemSpawnerSpawnAmount; i++) {
            int seed = world.rand.nextInt(bound);
            int j = 0;
            for (int m : list) {
                if (seed < m) {
                    temp.add(itemStacks.get(j).copy());
                    break;
                }
                j++;
            }
        }
        return temp;
    }

    public static int initGemList() {
        spawnItemPool.forEach(((itemStack, n) -> {
            bound += n;
            itemStacks.add(itemStack);
            list.add(bound);
        }));
        return itemStacks.size();
    }
}
