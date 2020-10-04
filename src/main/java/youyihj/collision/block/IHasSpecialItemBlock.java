package youyihj.collision.block;

import net.minecraft.item.ItemBlock;
import youyihj.collision.IRegistryObject;

public interface IHasSpecialItemBlock extends IRegistryObject {
    ItemBlock getItemBlock();

    CollisionBlock getThis();
}
