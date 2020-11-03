package youyihj.collision.recipe;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import youyihj.collision.block.absorber.EnumAbsorber;
import youyihj.collision.item.ItemMaterial;

public class ColliderRecipeRegistrar {
    static {
        new ColliderRecipe(1, new ItemStack(Blocks.STONE), new EnumAbsorber[][] {
            {EnumAbsorber.NEUTRON, EnumAbsorber.NEUTRON, EnumAbsorber.NEUTRON},
            {EnumAbsorber.NEUTRON, null, EnumAbsorber.NEUTRON},
            {EnumAbsorber.NEUTRON, EnumAbsorber.NEUTRON, EnumAbsorber.NEUTRON}
        }).register();

        new ColliderRecipe(1, new ItemStack(Items.BREAD), new EnumAbsorber[][]{
                {EnumAbsorber.PROTON, null, EnumAbsorber.PROTON},
                {null, null, null},
                {EnumAbsorber.PROTON, null, EnumAbsorber.PROTON}
        }).register();

        new ColliderRecipe(1, new ItemStack(Blocks.SAND, 4), new EnumAbsorber[][] {
                {null, null, null},
                {null, null, EnumAbsorber.NEUTRON},
                {null, null, EnumAbsorber.PROTON}
        }).register();

        new ColliderRecipe(3, ItemMaterial.instance().getSubItemStack("mystical_gem"), new EnumAbsorber[][]{
                {EnumAbsorber.NEUTRON, EnumAbsorber.NEUTRON, null},
                {null, null, null},
                {null, EnumAbsorber.PROTON, EnumAbsorber.PROTON}
        }).register();
    }
}
