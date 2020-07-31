package youyihj.collision.config;

import net.minecraftforge.common.config.Config;
import youyihj.collision.Collision;

@Config(modid = Collision.MODID, name = Collision.NAME, category = "absorber")
public class AbsorberConfig {
    @Config.RequiresMcRestart
    @Config.Comment("The speed of absorbers transforms. The argument higher, the speed slower.")
    public static int absorberSpeed = 2;

    @Config.RequiresMcRestart
    @Config.Comment("Absorbers works only in daytime?")
    public static boolean onlyWorkInDaytime = true;
}