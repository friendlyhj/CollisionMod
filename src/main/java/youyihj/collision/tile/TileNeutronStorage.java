package youyihj.collision.tile;

import youyihj.collision.core.IOType;
import youyihj.collision.core.SingleItemDeviceBase;

/**
 * @author youyihj
 */
public class TileNeutronStorage extends SingleItemDeviceBase.TileEntityModule {
    @Override
    public IOType getIOType() {
        return IOType.INPUT;
    }
}
