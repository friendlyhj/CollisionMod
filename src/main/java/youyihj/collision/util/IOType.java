package youyihj.collision.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

/**
 * @author youyihj
 */
public enum IOType {
    INPUT,
    OUTPUT,
    BOTH;

    public void sendMessageToPlayer(EntityPlayer player) {
        player.sendStatusMessage(getText(), false);
    }

    public ITextComponent getText() {
        return new TextComponentTranslation("message.collision.show_io_type",
                new TextComponentTranslation("message.collision.io."  + this.name().toLowerCase()));
    }

    public String getString() {
        return this.getText().getUnformattedText();
    }
}
