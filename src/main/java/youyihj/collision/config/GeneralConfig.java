package youyihj.collision.config;

import net.minecraftforge.common.config.Config;
import youyihj.collision.Collision;

@Config(modid = Collision.MODID, name = Collision.NAME)
public class GeneralConfig {
    @Config.Comment("The capacity of proton and neutron storage")
    @Config.RequiresMcRestart
    public static int storageCapacity = 100;
}
