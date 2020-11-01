package youyihj.collision.network;

import net.minecraft.client.resources.I18n;
import youyihj.collision.core.SingleItemDeviceBase;

/**
 * @author youyihj
 */
public class GuiProtonStorage extends SingleItemDeviceBase.GuiContainerModule {
    public GuiProtonStorage(SingleItemDeviceBase.ContainerModule inventorySlotsIn) {
        super(inventorySlotsIn);
    }

    @Override
    public String getTitle() {
        return I18n.format("tile.collision.proton_storage.name");
    }
}
