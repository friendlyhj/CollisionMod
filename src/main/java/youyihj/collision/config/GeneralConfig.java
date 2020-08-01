package youyihj.collision.config;

import net.minecraftforge.common.config.Config;
import youyihj.collision.Collision;

@Config(modid = Collision.MODID, name = Collision.NAME)
public class GeneralConfig {
    @Config.Comment("The capacity of proton and neutron storage")
    @Config.RequiresMcRestart
    public static int storageCapacity = 100;

    @Config.Comment({
          "233"
    })
    @Config.RequiresMcRestart
    public static String[] nucleuses = {
      "0,Iron,BF8040,200", "1,Gold,FFFF00,120"
    };
}
