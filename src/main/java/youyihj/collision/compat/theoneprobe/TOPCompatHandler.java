package youyihj.collision.compat.theoneprobe;

import mcjty.theoneprobe.api.*;

import java.util.function.Function;

/**
 * @author youyihj
 */
public class TOPCompatHandler implements Function<ITheOneProbe, Void> {
    public static ITheOneProbe probe;

    @Override
    public Void apply(ITheOneProbe iTheOneProbe) {
        probe = iTheOneProbe;
        probe.registerProvider(new TOPInfoHandler());
        return null;
    }
}
