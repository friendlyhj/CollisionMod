package youyihj.collision.block.spawner;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import youyihj.collision.Configuration;
import youyihj.collision.item.Nucleus;

import java.util.ArrayList;
import java.util.List;

public class MetalSpawner extends Spawner {

    public MetalSpawner() {
        super("metal_spawner");
    }

    private static final ArrayList<Integer> metalList = new ArrayList<>();
    private static final ArrayList<Integer> list = new ArrayList<>();
    private static int bound = 0;


    @Override
    public List<ItemStack> getSpawnItems(World world) {
        List<ItemStack> temp = new ArrayList<>();
        for (int i = 0; i < Configuration.spawnerConfig.metalSpawnerSpawnAmount; i++) {
            int seed = world.rand.nextInt(bound);
            int j = 0;
            for (int m : list) {
                if (seed < m) {
                    temp.add(new ItemStack(Nucleus.NUCLEUS, 1, metalList.get(j)));
                    break;
                }
                j++;
            }
        }
        return temp;
    }

    public static int initMetalList() {
        Nucleus.singleHashMap.forEach((meta, single) -> {
            bound += single.chance;
            metalList.add(meta);
            list.add(bound);
        });
        return metalList.size();
    }
}
