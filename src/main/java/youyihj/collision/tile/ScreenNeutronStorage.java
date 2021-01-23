package youyihj.collision.tile;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import youyihj.collision.util.ScreenModule;

/**
 * @author youyihj
 */
public class ScreenNeutronStorage extends ScreenModule<ContainerNeutronStorage> {
    public ScreenNeutronStorage(ContainerNeutronStorage screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }
}
