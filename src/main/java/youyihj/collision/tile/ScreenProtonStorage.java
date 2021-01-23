package youyihj.collision.tile;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import youyihj.collision.util.ScreenModule;

/**
 * @author youyihj
 */
public class ScreenProtonStorage extends ScreenModule<ContainerProtonStorage> {
    public ScreenProtonStorage(ContainerProtonStorage screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }
}
