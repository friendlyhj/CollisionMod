package youyihj.collision.compat.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.mc1120.item.MCItemStack;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import youyihj.collision.block.absorber.EnumAbsorber;
import youyihj.collision.compat.jei.CollisionPlugin;
import youyihj.collision.recipe.ColliderRecipe;
import youyihj.collision.recipe.ColliderRecipeManager;
import youyihj.collision.recipe.CustomColliderRecipe;
import youyihj.collision.util.IBlockMatcher;
import youyihj.collision.util.Utils;

@ZenClass("mods.collision.Collider")
@ZenRegister
@SuppressWarnings("unused")
public class CrTCollider {

    @ZenMethod
    public static void addRecipe(int level, IItemStack out, CrTEnumAbsorber[][] absorbers, @Optional(valueDouble = 100.0) int successChance) {
        CraftTweakerAPI.apply(new ColliderAdd(level, out, absorbers, successChance));
    }

    @ZenMethod
    public static void removeRecipe(IItemStack out) {
        if (ColliderRecipeManager.isSuchOutputExist(CraftTweakerMC.getItemStack(out))) {
            CraftTweakerAPI.apply(new ColliderRemove(out));
        } else {
            CraftTweakerAPI.logWarning("cannot find a collider recipe for " + out.getDisplayName() + "! Ignore this command.");
        }
    }

    @ZenMethod
    public static void addRecipe(int level, IItemStack out, IIngredient[][] blocks, @Optional(valueDouble = 100.0) int successChance, @Optional IIngredient[][] conversionBlocks) {
        CraftTweakerAPI.apply(new ColliderAddCustom(level, out, blocks, successChance, conversionBlocks));
    }

    @ZenMethod
    public static void removeAll() {
        CraftTweakerAPI.apply(new ColliderRemoveAll());
    }

    public static abstract class ColliderRecipeAction implements IAction {
        protected final int level;
        protected final IItemStack out;
        protected final CrTEnumAbsorber[][] absorbers;

        public ColliderRecipeAction(int level, IItemStack stack, CrTEnumAbsorber[][] absorbers) {
            this.level = level;
            this.out = stack;
            this.absorbers = absorbers;
        }

        protected final EnumAbsorber[][] getInternalAbsorbers() {
            return Utils.map2DArray(absorbers, CrTEnumAbsorber::getInternal, EnumAbsorber.class);
        }

        protected static CrTEnumAbsorber[][] getAbsorbers(EnumAbsorber[][] absorbers) {
            return Utils.map2DArray(absorbers, CrTEnumAbsorber::new, CrTEnumAbsorber.class);
        }
    }

    public static class ColliderAdd extends ColliderRecipeAction {

        protected final int successChance;

        public ColliderAdd(int level, IItemStack stack, CrTEnumAbsorber[][] absorbers, int successChance) {
            super(level, stack, absorbers);
            this.successChance = successChance;
        }

        @Override
        public void apply() {
            new ColliderRecipe(level, CraftTweakerMC.getItemStack(out), getInternalAbsorbers(), successChance).register();
        }

        @Override
        public String describe() {
            return "Adding a collider recipe for " + out.getDisplayName();
        }
    }

    public static class ColliderAddCustom extends ColliderAdd {
        private final IIngredient[][] blocks;
        private final IIngredient[][] conversionBlocks;

        public ColliderAddCustom(int level, IItemStack stack, IIngredient[][] blocks, int successChance, IIngredient[][] conversionBlocks) {
            super(level, stack, null, successChance);
            this.blocks = blocks;
            this.conversionBlocks = conversionBlocks;
        }

        @Override
        public void apply() {
            IBlockMatcher[][] matchers = Utils.map2DArray(this.blocks, CrTBlockMatcherGetter::get, IBlockMatcher.class);
            IBlockState[][] conversionBlocks = this.conversionBlocks == null
                    ? Utils.map2DArray(new Object[3][3], (obj) -> Blocks.AIR.getDefaultState(), IBlockState.class)
                    : Utils.map2DArray(this.conversionBlocks, (ingredient) -> {
                        if (ingredient == null) {
                            return Blocks.AIR.getDefaultState();
                        }
                        IItemStack stack = ingredient.getItems().get(0);
                        return CraftTweakerMC.getBlockState(stack.asBlock().getDefinition().getStateFromMeta(stack.getMetadata()));
            }, IBlockState.class);
            new CustomColliderRecipe(level, CraftTweakerMC.getItemStack(out), matchers, conversionBlocks, successChance).register();
            ItemStack[][] outBlocks = this.conversionBlocks == null
                    ? Utils.map2DArray(new Object[3][3], (obj) -> ItemStack.EMPTY, ItemStack.class)
                    : Utils.map2DArray(this.conversionBlocks, (ingredient) -> {
                if (ingredient == null) {
                    return ItemStack.EMPTY;
                }
                return CraftTweakerMC.getItemStack(ingredient.getItems().get(0));
            }, ItemStack.class);
            CollisionPlugin.addJEICustomColliderRecipe(level,
                    Utils.map2DArray(blocks, CraftTweakerMC::getIngredient, Ingredient.class),
                    CraftTweakerMC.getItemStack(out),
                    outBlocks,
                    successChance
            );
        }
    }

    public static class ColliderRemove extends ColliderRecipeAction {

        public ColliderRemove(IItemStack stack) {
            super(0, stack, null);
        }

        @Override
        public void apply() {
            ColliderRecipeManager.removeRecipe(CraftTweakerMC.getItemStack(out));
        }

        @Override
        public String describe() {
            return "Removing collider recipe(s) for " + out.getDisplayName();
        }
    }

    public static class ColliderRemoveAll extends ColliderRecipeAction {

        public ColliderRemoveAll() {
            super(0, MCItemStack.EMPTY, null);
        }

        @Override
        public void apply() {
            ColliderRecipeManager.removeAllRecipe();
        }

        @Override
        public String describe() {
            return "Removing all collider recipe.";
        }
    }
}
