package youyihj.collision.item;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import youyihj.collision.core.Collision;

import java.util.HashMap;

public class ItemMaterial extends CollisionItem {
    private String[] subItemIDs;

    ItemMaterial(String... subItemIDs) {
        super("material");
        this.subItemIDs = subItemIDs;
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setNoRepair();
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (this.isInCreativeTab(tab)) {
            for (int i = 0; i < subItemIDs.length; i++) {
                items.add(new ItemStack(this, 1, i));
            }
        }
    }

    @Override
    public int getItemStackLimit(ItemStack stack) {
        int meta = stack.getMetadata();
        return (meta == 3 || meta == 4) ? 4 : super.getItemStackLimit(stack);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        int meta = stack.getMetadata();
        return meta < subItemIDs.length
                ? "item." + Collision.MODID + "." + subItemIDs[meta]
                : this.getUnlocalizedName(new ItemStack(this, 1, 0));
    }

    @Override
    public HashMap<Integer, ModelResourceLocation> getModelRLs() {
        HashMap<Integer, ModelResourceLocation> temp = new HashMap<>();
        for (int i = 0; i < subItemIDs.length; i++) {
            temp.put(i, new ModelResourceLocation(new ResourceLocation(Collision.MODID, subItemIDs[i]), "inventory"));
        }
        return temp;
    }
}
