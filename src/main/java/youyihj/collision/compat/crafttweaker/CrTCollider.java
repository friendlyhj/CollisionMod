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
import youyihj.collision.util.Lazy;
import youyihj.collision.util.Utils;

@ZenClass("mods.collision.Collider")
@ZenRegister
@SuppressWarnings("unused")
public class CrTCollider {

    @ZenMethod
    public static void addRecipe(int level, IItemStack out, CrTEnumAbsorber[][] absorbers, @Optional(valueLong = 100) int successChance) {
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
    public static void addCustomRecipe(int level, IItemStack out, IIngredient[][] blocks, @Optional(valueLong = 100) int successChance, @Optional(valueLong = 100) int conversionChance, @Optional IIngredient[][] conversionBlocks) {
        CraftTweakerAPI.apply(new ColliderAddCustom(level, out, blocks, successChance, conversionChance, conversionBlocks));
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
        private final int conversionChance;

        public ColliderAddCustom(int level, IItemStack stack, IIngredient[][] blocks, int successChance, int conversionChance, IIngredient[][] conversionBlocks) {
            super(level, stack, null, successChance);
            this.blocks = blocks;
            this.conversionBlocks = conversionBlocks;
            this.conversionChance = conversionChance;
        }

        @Override
        public void apply() {
            IBlockMatcher[][] matchers = Utils.map2DArray(this.blocks, CraftTweakerUtils::get, IBlockMatcher.class, IBlockMatcher.Impl.air());
            IBlockState[][] conversionBlocks = Lazy.of(this.conversionBlocks)
                    .map(blocks -> Utils.map2DArray(blocks, CraftTweakerUtils::itemToState, IBlockState.class, Blocks.AIR.getDefaultState()))
                    .orElse(Utils.create2DArray(Blocks.AIR::getDefaultState, IBlockState.class, 3, 3));
            new CustomColliderRecipe(level, CraftTweakerMC.getItemStack(out), matchers, conversionBlocks, conversionChance, successChance).register();
            ItemStack[][] outBlocks = Lazy.of(this.conversionBlocks)
                    .map(ingredients -> Utils.map2DArray(ingredients, CraftTweakerMC::getItemStack, ItemStack.class))
                    .orElse(Utils.create2DArray(() -> ItemStack.EMPTY, ItemStack.class, 3, 3));
            CollisionPlugin.addJEICustomColliderRecipe(level,
                    Utils.map2DArray(blocks, CraftTweakerUtils::getIngredientOrAnyBlock, Ingredient.class),
                    CraftTweakerMC.getItemStack(out),
                    outBlocks,
                    successChance,
                    conversionChance
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
