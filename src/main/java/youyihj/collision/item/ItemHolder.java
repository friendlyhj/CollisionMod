package youyihj.collision.item;

import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import youyihj.collision.block.absorber.Absorber;
import youyihj.collision.util.Utils;

/**
 * @author youyihj
 */
public class ItemHolder extends ItemBase {
    private final Absorber.Type type;

    public ItemHolder(Absorber.Type type) {
        super(type.name().toLowerCase() + "_holder", new Properties().maxDamage(100));
        this.type = type;
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getPos();
        ItemStack thisStack = context.getItem();
        if (world.getBlockState(pos).getBlock() instanceof Absorber) {
            Absorber absorber = (Absorber) world.getBlockState(pos).getBlock();
            if (absorber.getType().toFullType() == this.type) {
                if (world.isRemote)
                    return ActionResultType.SUCCESS;
                int damage = absorber.isRefined() ? 4 : 1;
                if (absorber.getType().isEmpty() && thisStack.getDamage() <= thisStack.getMaxDamage() - damage) {
                    absorber.transform(world, pos);
                    Utils.damageItem(context.getItem(), damage, context.getPlayer(), world);
                } else if (!absorber.getType().isEmpty() && thisStack.getDamage() >= damage) {
                    absorber.transform(world, pos);
                    absorber.transform(world, pos);
                    Utils.damageItem(thisStack, -damage, context.getPlayer(), world);
                }
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.PASS;
    }
}
