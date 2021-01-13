package youyihj.collision.event;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.ItemHandlerHelper;
import youyihj.collision.Collision;
import youyihj.collision.block.ColliderBase;
import youyihj.collision.block.absorber.NeutronEmpty;
import youyihj.collision.block.absorber.ProtonEmpty;
import youyihj.collision.config.Configuration;

/**
 * @author youyihj
 */
@Mod.EventBusSubscriber
public class InitialItemHandler {
    private static final ResourceLocation INITIAL_ITEM_RL = Collision.rl("initial_item");

    @SubscribeEvent
    public static void handle(PlayerEvent.PlayerLoggedInEvent event) {
        PlayerEntity player = event.getPlayer();
        if (player.world.isRemote || !Configuration.givePlayerInitialItems.get())
            return;
        if (!player.getPersistentData().getBoolean(INITIAL_ITEM_RL.toString())) {
            ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(ColliderBase.getItem(1)));
            ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(ProtonEmpty.INSTANCE, 2));
            ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(NeutronEmpty.INSTANCE, 2));
            player.getPersistentData().putBoolean(INITIAL_ITEM_RL.toString(), true);
        }
    }
}
