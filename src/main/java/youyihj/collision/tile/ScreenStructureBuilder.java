package youyihj.collision.tile;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import youyihj.collision.util.ScreenModule;

/**
 * @author youyihj
 */
public class ScreenStructureBuilder extends ScreenModule<ContainerStructureBuilder> {
    public ScreenStructureBuilder(ContainerStructureBuilder screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }
}
