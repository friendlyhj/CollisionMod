package youyihj.collision.recipe;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import youyihj.collision.block.absorber.EnumAbsorber;
import youyihj.collision.item.ItemMaterial;
import youyihj.collision.item.ItemRegistryHandler;

import static youyihj.collision.block.absorber.EnumAbsorber.NEUTRON;
import static youyihj.collision.block.absorber.EnumAbsorber.PROTON;

public class ColliderRecipeRegistrar {
    static {
        new ColliderRecipe(1, new ItemStack(Blocks.COBBLESTONE, 4), new EnumAbsorber[][] {
                {null, PROTON, null},
                {null, null, NEUTRON},
                {null, null, null}
        }).register();

        new ColliderRecipe(1, new ItemStack(Items.DYE, 2, 15), new EnumAbsorber[][]{
                {null, null, null},
                {PROTON, null, NEUTRON},
                {null, null, null}
        }).register();

        new ColliderRecipe(1, new ItemStack(Items.GUNPOWDER), new EnumAbsorber[][]{
                {PROTON, null, null},
                {null, null, null},
                {null, NEUTRON, null}
        }).register();

        new ColliderRecipe(1, new ItemStack(Items.STRING), new EnumAbsorber[][]{
                {null, PROTON, null},
                {null, null, null},
                {null, NEUTRON, null}
        }).register();

        new ColliderRecipe(1, new ItemStack(Blocks.SAPLING, 1, 0), new EnumAbsorber[][]{
                {PROTON, NEUTRON, null},
                {null, null, null},
                {null, null, null}
        }).register();

        new ColliderRecipe(1, new ItemStack(Blocks.SAPLING, 1, 1), new EnumAbsorber[][]{
                {PROTON, null, NEUTRON},
                {null, null, null},
                {null, null, null}
        }).register();

        new ColliderRecipe(1, new ItemStack(Blocks.SAPLING, 1, 2), new EnumAbsorber[][]{
                {PROTON, null, null},
                {null, null, NEUTRON},
                {null, null, null}
        }).register();

        new ColliderRecipe(1, new ItemStack(Blocks.SAPLING, 1, 3), new EnumAbsorber[][]{
                {PROTON, null, null},
                {null, null, null},
                {null, null, NEUTRON}
        }).register();

        new ColliderRecipe(1, new ItemStack(Blocks.SAPLING, 1, 4), new EnumAbsorber[][]{
                {PROTON, null, null},
                {NEUTRON, null, null},
                {null, null, null}
        }).register();

        new ColliderRecipe(1, new ItemStack(Blocks.SAPLING, 1, 5), new EnumAbsorber[][]{
                {PROTON, null, null},
                {null, null, null},
                {null, NEUTRON, null}
        }).register();

        new ColliderRecipe(1, new ItemStack(Blocks.SAND, 4), new EnumAbsorber[][] {
                {null, null, null},
                {null, null, NEUTRON},
                {null, null, PROTON}
        }).register();

        new ColliderRecipe(1, new ItemStack(Blocks.GRAVEL, 4), new EnumAbsorber[][] {
                {null, null, null},
                {null, null, PROTON},
                {null, null, NEUTRON}
        }).register();

        new ColliderRecipe(1, new ItemStack(Blocks.DIRT, 4), new EnumAbsorber[][] {
                {null, PROTON, null},
                {null, null, null},
                {NEUTRON, null, null}
        }).register();

        new ColliderRecipe(1, new ItemStack(Items.COAL), new EnumAbsorber[][] {
                {NEUTRON, null, PROTON},
                {null, null, null},
                {null, null, null}
        }).register();

        new ColliderRecipe(1, new ItemStack(ItemRegistryHandler.itemHashMap.get("water_drop")), new EnumAbsorber[][]{
                {null, null, null},
                {null, null, PROTON},
                {null, NEUTRON, null}
        }).register();

        new ColliderRecipe(2, new ItemStack(Blocks.NETHERRACK, 4), new EnumAbsorber[][] {
                {PROTON, null, NEUTRON},
                {PROTON, null, null},
                {NEUTRON, null, null}
        }).register();

        new ColliderRecipe(2, new ItemStack(Blocks.SOUL_SAND, 4), new EnumAbsorber[][] {
                {PROTON, PROTON, null},
                {null, null, null},
                {null, NEUTRON, NEUTRON}
        }).register();

        new ColliderRecipe(2, new ItemStack(ItemRegistryHandler.itemHashMap.get("lava_drop")), new EnumAbsorber[][]{
                {null, PROTON, null},
                {PROTON, null, NEUTRON},
                {null, NEUTRON, null}
        }).register();

        new ColliderRecipe(2, new ItemStack(Blocks.GRASS), new EnumAbsorber[][] {
                {NEUTRON, null, PROTON},
                {NEUTRON, null, null},
                {PROTON, null, null}
        }).register();

        new ColliderRecipe(2, new ItemStack(Items.WHEAT_SEEDS), new EnumAbsorber[][]{
                {PROTON, NEUTRON, null},
                {PROTON, null, NEUTRON},
                {null, null, null}
        }).register();

        new ColliderRecipe(2, new ItemStack(Items.MELON_SEEDS), new EnumAbsorber[][]{
                {PROTON, null, NEUTRON},
                {PROTON, null, NEUTRON},
                {null, null, null}
        }).register();

        new ColliderRecipe(2, new ItemStack(Items.PUMPKIN_SEEDS), new EnumAbsorber[][]{
                {PROTON, null, null},
                {PROTON, null, null},
                {NEUTRON, null, NEUTRON}
        }).register();

        new ColliderRecipe(2, new ItemStack(Items.CARROT), new EnumAbsorber[][]{
                {null, PROTON, PROTON},
                {null, null, null},
                {NEUTRON, null, NEUTRON}
        }).register();

        new ColliderRecipe(2, new ItemStack(Items.POTATO), new EnumAbsorber[][]{
                {NEUTRON, null, PROTON},
                {null, null, NEUTRON},
                {null, PROTON, null}
        }).register();

        new ColliderRecipe(2, new ItemStack(Items.BEETROOT_SEEDS), new EnumAbsorber[][]{
                {null, NEUTRON, PROTON},
                {null, null, null},
                {NEUTRON, null, PROTON}
        }).register();

        new ColliderRecipe(2, new ItemStack(Items.DYE), new EnumAbsorber[][]{
                {PROTON, null, NEUTRON},
                {null, null, null},
                {NEUTRON, null, PROTON}
        }).register();

        new ColliderRecipe(2, new ItemStack(Items.DYE, 1, 3), new EnumAbsorber[][]{
                {PROTON, null, null},
                {NEUTRON, null, NEUTRON},
                {PROTON, null, null}
        }).register();

        new ColliderRecipe(2, ItemMaterial.instance().getSubItemStack("metal_chunk"), new EnumAbsorber[][]{
                {NEUTRON, null, null},
                {PROTON, null, PROTON},
                {NEUTRON, null, null}
        }).register();

        new ColliderRecipe(3, ItemMaterial.instance().getSubItemStack("mystical_gem"), new EnumAbsorber[][]{
                {NEUTRON, NEUTRON, null},
                {null, null, null},
                {null, PROTON, PROTON}
        }).register();

        new ColliderRecipe(3, new ItemStack(Items.BLAZE_ROD), new EnumAbsorber[][]{
                {null, PROTON, null},
                {PROTON, null, NEUTRON},
                {null, NEUTRON, null}
        }).register();

        new ColliderRecipe(3, new ItemStack(Items.NETHER_WART), new EnumAbsorber[][]{
                {PROTON, null, NEUTRON},
                {NEUTRON, null, null},
                {null, null, PROTON}
        }).register();

        new ColliderRecipe(4, ItemMaterial.instance().getSubItemStack("little_ghast_drop"), new EnumAbsorber[][]{
                {PROTON, PROTON, NEUTRON},
                {null, null, null},
                {NEUTRON,NEUTRON, PROTON}
        }).register();
    }
}
