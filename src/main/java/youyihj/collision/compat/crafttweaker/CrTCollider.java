package youyihj.collision.compat.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.mc1120.item.MCItemStack;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import youyihj.collision.block.absorber.EnumAbsorber;
import youyihj.collision.recipe.ColliderRecipe;

import java.util.Arrays;
import java.util.Optional;

import static youyihj.collision.recipe.ColliderRecipe.*;

@ZenClass("mods.collision.Collider")
@ZenRegister
@SuppressWarnings("unused")
public class CrTCollider {

    @ZenMethod
    public static void addRecipe(int level, IItemStack out, CrTEnumAbsorber[][] absorbers) {
        CraftTweakerAPI.apply(new ColliderAdd(level, out, absorbers));
    }

    @ZenMethod
    public static void removeRecipe(IItemStack out) {
        Optional<ColliderRecipe> oRecipe =  colliderRecipes.stream().filter((recipe -> {
            ItemStack stack = recipe.getOut();
            stack.setCount(1);
            return CraftTweakerMC.getIItemStack(stack).matchesExact(out);
        })).findAny();
        if (oRecipe.isPresent()){
            ColliderRecipe recipe = oRecipe.get();
            CraftTweakerAPI.apply(new ColliderRemove(recipe.getLevel(), CraftTweakerMC.getIItemStack(recipe.getOut()), ColliderRecipeAction.getAbsorbers(recipe.getInput())));
        } else {
            CraftTweakerAPI.logWarning("cannot find a collider recipe for " + out.getDisplayName() + "! Ignore this command.");
        }
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
            return Arrays.stream(absorbers).map((absorberArray) ->
                Arrays.stream(absorberArray).map(CrTEnumAbsorber::getInternalStatic).toArray(EnumAbsorber[]::new
                )).toArray(EnumAbsorber[][]::new);
        }

        protected static CrTEnumAbsorber[][] getAbsorbers(EnumAbsorber[][] absorbers) {
            return Arrays.stream(absorbers).map((absorberArray) ->
                    Arrays.stream(absorberArray).map(CrTEnumAbsorber::new).toArray(CrTEnumAbsorber[]::new
                    )).toArray(CrTEnumAbsorber[][]::new);
        }
    }

    public static class ColliderAdd extends ColliderRecipeAction {

        public ColliderAdd(int level, IItemStack stack, CrTEnumAbsorber[][] absorbers) {
            super(level, stack, absorbers);
        }

        @Override
        public void apply() {
            new ColliderRecipe(level, CraftTweakerMC.getItemStack(out), getInternalAbsorbers()).register();
        }

        @Override
        public String describe() {
            return "Adding a collider recipe for " + out.getDisplayName();
        }
    }

    public static class ColliderRemove extends ColliderRecipeAction {

        public ColliderRemove(int level, IItemStack stack, CrTEnumAbsorber[][] absorbers) {
            super(level, stack, absorbers);
        }

        @Override
        public void apply() {
            colliderRecipes.removeIf(recipe -> CraftTweakerMC.getIItemStack(recipe.getOut()).matchesExact(out));
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
            ColliderRecipe.colliderRecipes.clear();
        }

        @Override
        public String describe() {
            return "Removing all collider recipe.";
        }
    }
}
