package youyihj.collision.config;

import com.google.common.collect.Lists;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;

/**
 * @author youyihj
 */
public class Configuration {
    public static ForgeConfigSpec config;

    public static ForgeConfigSpec.BooleanValue givePlayerInitialItems;
    public static ForgeConfigSpec.IntValue nuggetsOutputCount;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> nuclei;

    public static ForgeConfigSpec.IntValue absorberSpeed;
    public static ForgeConfigSpec.BooleanValue onlyWorkInDaytime;

    public static ForgeConfigSpec.IntValue metalSpawnerSpawnAmount;
    public static ForgeConfigSpec.IntValue gemSpawnerSpawnAmount;

    static {
        ForgeConfigSpec.Builder configBuilder = new ForgeConfigSpec.Builder();

        configBuilder.push("general");
        givePlayerInitialItems = configBuilder.comment("should give player initial items? (collider lv1 and so on)").define("givePlayerInitialItems", true);
        nuggetsOutputCount = configBuilder.comment("How many nuggets will be outputted when nuclei are smelted").defineInRange("nuggetsOutputCount", 3, 0, 64);
        nuclei = configBuilder.comment("The list of nuclei", "The format is \"meta,name,color,wightInMetalSpawner\"")
                .defineList("nuclei", Lists.newArrayList("Iron,BF8040,200", "Gold,FFFF00,120"),
                        s -> ((String) s).split(",").length == 3);
        configBuilder.pop();

        configBuilder.push("absorber");
        absorberSpeed = configBuilder.comment("The speed of absorbers transforms. The argument higher, the speed slower.").defineInRange("absorberSpeed", 2, 0, Integer.MAX_VALUE);
        onlyWorkInDaytime = configBuilder.comment("Absorbers works only in daytime?").define("onlyWorkInDaytime", true);
        configBuilder.pop();

        configBuilder.push("spawner");
        metalSpawnerSpawnAmount = configBuilder.comment("How many nuclei metal spawner spawn?").defineInRange("metalSpawnerSpawnAmount", 24, 0, Integer.MAX_VALUE);
        gemSpawnerSpawnAmount = configBuilder.comment("How many nuclei gem spawner spawn?").defineInRange("gemSpawnerSpawnAmount", 12, 0, Integer.MAX_VALUE);
        configBuilder.pop();

        config = configBuilder.build();
    }
}
