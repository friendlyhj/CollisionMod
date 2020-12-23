package youyihj.collision.item;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

/**
 * @author youyihj
 */
public class ItemDebugger extends ItemBase {
    public ItemDebugger() {
        super("debugger", new Properties());
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        World world = context.getWorld();
        if (!world.isRemote()) {
            BlockState blockState = world.getBlockState(context.getPos());
            TileEntity tileEntity = world.getTileEntity(context.getPos());
            PlayerEntity player = context.getPlayer();
            if (player != null) {
                player.sendStatusMessage(new StringTextComponent(blockState.toString()), false);
                if (tileEntity != null) {
                    CompoundNBT nbt = tileEntity.write(new CompoundNBT());
                    player.sendStatusMessage(new StringTextComponent(nbt.toString()), false);
                }
            }
        }
        return ActionResultType.SUCCESS;
    }
}
