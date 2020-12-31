package youyihj.collision.tile;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import youyihj.collision.util.SingleItemDeviceBase;

/**
 * @author youyihj
 */
public class ScreenHarvester extends SingleItemDeviceBase.ScreenModule<ContainerHarvester> {
    public ScreenHarvester(ContainerHarvester screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }
}
