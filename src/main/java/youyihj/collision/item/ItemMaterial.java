package youyihj.collision.item;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.ArrayUtils;
import youyihj.collision.Collision;

import java.util.List;

public class ItemMaterial extends CollisionItem {
    private final String[] subItemIDs;

    private ItemMaterial() {
        super("material");
        this.subItemIDs = ItemRegistrar.plainItemsID;
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setNoRepair();
    }

    private static final ItemMaterial INSTANCE = new ItemMaterial();

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
    public void getModelRLs(List<ModelResourceLocation> list) {
        for (String subItemID : subItemIDs) {
            list.add(new ModelResourceLocation(new ResourceLocation(Collision.MODID, subItemID), "inventory"));
        }
    }

    public ItemStack getSubItemStack(int count, String name) {
        return new ItemStack(this, count, ArrayUtils.indexOf(subItemIDs, name));
    }

    public ItemStack getSubItemStack(String name) {
        return this.getSubItemStack(1, name);
    }

    public static ItemMaterial instance() {
        return INSTANCE;
    }
}
