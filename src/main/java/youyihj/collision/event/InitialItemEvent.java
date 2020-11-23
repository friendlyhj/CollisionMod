package youyihj.collision.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.items.ItemHandlerHelper;
import youyihj.collision.Collision;
import youyihj.collision.Configuration;
import youyihj.collision.block.ColliderBase;
import youyihj.collision.item.ItemRegistryHandler;

/**
 * @author youyihj
 */
@Mod.EventBusSubscriber
public class InitialItemEvent {
    @SubscribeEvent
    public static void handle(PlayerEvent.PlayerLoggedInEvent event) {
        if (!Configuration.generalConfig.givePlayerInitialItems) {
            return;
        }
        EntityPlayer player = event.player;
        if (!player.world.isRemote) {
            NBTTagCompound nbtTagCompound = player.getEntityData();
            NBTTagCompound data = nbtTagCompound.hasKey(EntityPlayer.PERSISTED_NBT_TAG) ? nbtTagCompound.getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG) : new NBTTagCompound();
            if (!data.hasKey(Collision.MODID) || !data.getBoolean(Collision.MODID)) {
                ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(ItemRegistryHandler.itemBlockHashMap.get(ColliderBase.getRegistryName(1))));
                ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(ItemRegistryHandler.itemBlockHashMap.get("proton_empty"), 2, 0));
                ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(ItemRegistryHandler.itemBlockHashMap.get("neutron_empty"), 2, 0));
                data.setBoolean(Collision.MODID, true);
            }
            nbtTagCompound.setTag(EntityPlayer.PERSISTED_NBT_TAG, data);
        }
    }
}
