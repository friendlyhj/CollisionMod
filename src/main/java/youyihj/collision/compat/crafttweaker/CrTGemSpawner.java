package youyihj.collision.compat.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import youyihj.collision.block.spawner.GemSpawner;

@ZenClass("mods.collision.GemSpawner")
@ZenRegister
@SuppressWarnings("unused")
public class CrTGemSpawner {
    @ZenMethod
    public static void addItem(IItemStack stack, int wight) {
        CraftTweakerAPI.apply(new GemSpawnerAdd(stack, wight));
    }

    @ZenMethod
    public static void removeItem(IItemStack stack) {
        CraftTweakerAPI.apply(new GemSpawnerRemove(stack));
    }

    @ZenMethod
    public static void removeAll() {
        CraftTweakerAPI.apply(new GemSpawnerRemoveAll());
    }

    public static class GemSpawnerAdd implements IAction {
        private IItemStack stack;
        private int wight;

        public GemSpawnerAdd(IItemStack stack, int wight) {
            this.stack = stack;
            this.wight = wight;
        }

        @Override
        public void apply() {
            GemSpawner.addItem(CraftTweakerMC.getItemStack(stack), wight);
        }

        @Override
        public String describe() {
            return "Adding " + stack.getDisplayName() + " to gem spawner's item pool.";
        }
    }

    public static class GemSpawnerRemove implements IAction {
        private IItemStack stack;

        public GemSpawnerRemove(IItemStack stack) {
            this.stack = stack;
        }

        @Override
        public void apply() {
            GemSpawner.removeItem(CraftTweakerMC.getItemStack(stack));
        }

        @Override
        public String describe() {
            return "Removing " + stack.getDisplayName() + " in gem spawner's item pool.";
        }
    }

    public static class GemSpawnerRemoveAll implements IAction {

        @Override
        public void apply() {
            GemSpawner.removeAll();
        }

        @Override
        public String describe() {
            return "Removing gem spawner item pool.";
        }
    }
}
