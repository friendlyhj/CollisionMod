package youyihj.collision.item;

import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;
import youyihj.collision.Collision;
import youyihj.collision.Configuration;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.HashSet;


public class Nucleus extends CollisionItem {
    private Nucleus() {
        super("nucleus");
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setNoRepair();
    }

    public static HashMap<Integer, SingleNucleus> singleHashMap = new HashMap<>();
    public static final Nucleus NUCLEUS = new Nucleus();

    static {
        for (String singleInfo : Configuration.generalConfig.nuclei) {
            String[] singleInfos = singleInfo.split(",");
            if (singleInfos.length == 4) {
                int meta = Integer.valueOf(singleInfos[0]);
                singleHashMap.put(meta, new SingleNucleus(singleInfos[1], singleInfos[2], Integer.valueOf(singleInfos[3])));
            } else {
                throw new IllegalArgumentException(singleInfo + " is invalid!");
            }
        }
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (this.isInCreativeTab(tab)) {
            singleHashMap.forEach((meta, single) -> {
                ItemStack stack = new ItemStack(this, 1, meta);
                items.add(stack);
                OreDictionary.registerOre("nucleus" + single.name, stack);
            });
        }
    }

    public static HashSet<Integer> getAllMetaData() {
        HashSet<Integer> hashSet = new HashSet<>();
        for (String singleInfo : Configuration.generalConfig.nuclei) {
            String[] singleInfos = singleInfo.split(",");
            hashSet.add(Integer.valueOf(singleInfos[0]));
        }
        return hashSet;
    }

    @Override
    @Nonnull
    public String getItemStackDisplayName(ItemStack stack) {
        return I18n.format("item.collision.nucleus.name",
                I18n.format("material.nucleus." + singleHashMap.get(stack.getMetadata()).name.toLowerCase()));
    }

    @EventBusSubscriber(modid = Collision.MODID)
    @SuppressWarnings("unused")
    public static final class NucleusTinter {
        @SubscribeEvent
        public static void colorMultiplier(ColorHandlerEvent.Item event) {
            event.getItemColors().registerItemColorHandler((ItemStack stack, int tintIndex) -> (
               Integer.parseInt(singleHashMap.get(stack.getMetadata()).color, 16)
            ), Nucleus.NUCLEUS);
        }
    }
}