package youyihj.collision.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.SurvivesExplosion;
import youyihj.collision.Collision;
import youyihj.collision.block.BlockBase;
import youyihj.collision.block.BlockRegistry;
import youyihj.collision.util.AnnotationUtil;

import java.io.IOException;
import java.nio.file.Path;

/**
 * @author youyihj
 */
public class BlockLootProvider implements IDataProvider {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final DataGenerator generator;

    public BlockLootProvider(DataGenerator generator) {
        this.generator = generator;
    }

    @Override
    public void act(DirectoryCache cache) throws IOException {
        for (BlockBase block : BlockRegistry.getBlocks().values()) {
            if (AnnotationUtil.hasNotAnnotation(block, DisableBlockLootGenerator.class)) {
                Path path = generator.getOutputFolder().resolve("data/" + Collision.MODID + "/loot_tables/blocks/" + block.getName() + ".json");
                IDataProvider.save(GSON, cache, LootTableManager.toJson(genRegular(block).setParameterSet(LootParameterSets.BLOCK).build()), path);
            }
        }
    }

    @Override
    public String getName() {
        return "Collision BlockLoot Provider";
    }

    private static LootTable.Builder genRegular(Block b) {
        LootEntry.Builder<?> entry = ItemLootEntry.builder(b);
        LootPool.Builder pool = LootPool.builder().name("main").rolls(ConstantRange.of(1)).addEntry(entry)
                .acceptCondition(SurvivesExplosion.builder());
        return LootTable.builder().addLootPool(pool);
    }
}
