package youyihj.collision;

import net.minecraftforge.common.config.Config;

@Config(modid = Collision.MODID, name = Collision.NAME, category = Collision.MODID)
public class Configuration {
    @Config.Name("general")
    public static GeneralConfig generalConfig = new GeneralConfig();

    @Config.Name("absorber")
    public static AbsorberConfig absorberConfig = new AbsorberConfig();

    @Config.Name("spawner")
    public static SpawnerConfig spawnerConfig = new SpawnerConfig();


    public static class GeneralConfig {
        @Config.Comment({
                "The list of nuclei",
                "The format is \"meta,name,color,chanceInMetalSpawner\"",
                "chance = 1 means 0.1%"
        })
        public String[] nuclei = {
                "0,Iron,BF8040,200", "1,Gold,FFFF00,120"
        };

        @Config.Comment({
                "How many nuggets will be outputted when nuclei are smelted"
        })
        public int nuggetsOutputCount = 3;
    }

    public static class AbsorberConfig {
        @Config.Comment("The speed of absorbers transforms. The argument higher, the speed slower.")
        public int absorberSpeed = 2;

        @Config.Comment("Absorbers works only in daytime?")
        public boolean onlyWorkInDaytime = true;
    }

    public static class SpawnerConfig {
        @Config.Comment("How many nuclei metal spawner spawn?")
        public int metalSpawnerSpawnAmount = 24;

        @Config.Comment("How many nuclei gem spawner spawn?")
        public int gemSpawnerSpawnAmount = 12;
    }
}
