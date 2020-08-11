package youyihj.collision.config;

import net.minecraftforge.common.config.Config;
import youyihj.collision.Collision;

@Config(modid = Collision.MODID, name = Collision.NAME, category = Collision.MODID)
public class Configuration {
    @Config.Name("general")
    public static GeneralConfig generalConfig = new GeneralConfig();

    @Config.Name("absorber")
    public static AbsorberConfig absorberConfig = new AbsorberConfig();

    @Config.Name("spawner")
    public static SpawnerConfig spawnerConfig = new SpawnerConfig();


    public static class GeneralConfig {
        @Config.Comment("The capacity of proton and neutron storage")
        public int storageCapacity = 100;

        @Config.Comment({
                "The list of nuclei",
                "The format is \"name,color,chanceInMetalSpawner\"",
                "chance = 1 means 0.1%"
        })
        public String[] nuclei = {
                "Iron,BF8040,200", "Gold,FFFF00,120"
        };
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
    }
}
