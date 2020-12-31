package youyihj.collision.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

/**
 * @author youyihj
 */
public enum IOType {
    INPUT,
    OUTPUT,
    BOTH;

    public void sendMessageToPlayer(PlayerEntity player) {
        player.sendStatusMessage(getText(), false);
    }

    public ITextComponent getText() {
        return new TranslationTextComponent("message.collision.show_io_type",
                new TranslationTextComponent("message.collision.io."  + this.name().toLowerCase()));
    }

    public String getString() {
        return this.getText().getUnformattedComponentText();
    }
}
