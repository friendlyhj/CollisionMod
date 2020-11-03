package youyihj.collision.network;

import net.minecraft.client.resources.I18n;
import youyihj.collision.util.SingleItemDeviceBase;

/**
 * @author youyihj
 */
public class GuiHarvester extends SingleItemDeviceBase.GuiContainerModule {
    public GuiHarvester(SingleItemDeviceBase.ContainerModule inventorySlotsIn) {
        super(inventorySlotsIn);
    }

    @Override
    public String getTitle() {
        return I18n.format("tile.collision.harvester.name");
    }
}
