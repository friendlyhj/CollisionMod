package youyihj.collision.network;

import net.minecraft.client.resources.I18n;
import youyihj.collision.util.SingleItemDeviceBase;

/**
 * @author youyihj
 */
public class GuiNeutronStorage extends SingleItemDeviceBase.GuiContainerModule {
    public GuiNeutronStorage(SingleItemDeviceBase.ContainerModule inventorySlotsIn) {
        super(inventorySlotsIn);
    }

    @Override
    public String getTitle() {
        return I18n.format("tile.collision.neutron_storage.name");
    }
}
