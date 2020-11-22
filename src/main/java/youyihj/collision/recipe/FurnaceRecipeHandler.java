package youyihj.collision.recipe;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import youyihj.collision.Configuration;
import youyihj.collision.item.Nucleus;

/**
 * @author youyihj
 */
public class FurnaceRecipeHandler {
    public static void registerNucleusRecipe() {
        Nucleus.singleHashMap.forEach((meta, nucleus) -> {
            String nugget = "nugget" + nucleus.name;
            if (OreDictionary.doesOreNameExist(nugget) && !OreDictionary.getOres(nugget).isEmpty()) {
                ItemStack itemStack = OreDictionary.getOres(nugget).get(0);
                if (!itemStack.isEmpty()) {
                    GameRegistry.addSmelting(
                            new ItemStack(Nucleus.NUCLEUS, 1, meta),
                            new ItemStack(itemStack.getItem(), Configuration.generalConfig.nuggetsOutputCount, itemStack.getItemDamage()),
                            0);
                }
            }
        });
    }
}
