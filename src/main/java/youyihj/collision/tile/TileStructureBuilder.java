package youyihj.collision.tile;

import net.minecraft.util.ITickable;
import youyihj.collision.block.ColliderBase;
import youyihj.collision.block.absorber.EnumAbsorber;
import youyihj.collision.core.SingleItemDeviceBase;
import youyihj.collision.recipe.ColliderRecipe;

public class TileStructureBuilder extends SingleItemDeviceBase.TileEntityModule implements ITickable {

    @Override
    public void update() {
        if (!world.isRemote &&
                world.getWorldTime() % 10 == 0 &&
                world.getBlockState(this.getPos().up()).getBlock() instanceof ColliderBase) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (i == 1 && j == 1) continue;
                    if (!world.isAirBlock(pos.add(i - 1, 1, j - 1))) return;
                }
            }
            ColliderRecipe recipe = ColliderRecipe.colliderRecipes.stream()
                    .filter(recipe0 -> recipe0.getOut().getItem() == this.item.getStackInSlot(0).getItem())
                    .findAny()
                    .orElse(null);
            if (recipe == null) return;
            int p = 0;
            int n = 0;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (i == 1 && j == 1) continue;
                    EnumAbsorber absorber = recipe.getInput()[i][j];
                    if (absorber == EnumAbsorber.PROTON) p++;
                    if (absorber == EnumAbsorber.NEUTRON) n++;
                }
            }
            /*
            boolean work;
            boolean nYes = this.neutron.extractItem(0, n, true).getItem() == ItemRegistryHandler.itemBlockHashMap.get(Neutron.INSTANCE.getRegistryName().getResourcePath());;
            boolean pYes = this.proton.extractItem(0, n, true).getItem() == ItemRegistryHandler.itemBlockHashMap.get(Neutron.INSTANCE.getRegistryName().getResourcePath());
            if (p == 0) work = nYes;
            else if (n == 0) work = pYes;
            else work = nYes && pYes;
            if (work) {
                this.proton.extractItem(0, p, false);
                this.neutron.extractItem(0, n, false);
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (i == 1 && j == 1) continue;
                        BlockPos posOffset = this.getPos().add(i - 1, 1, j - 1);
                        world.setBlockState(posOffset, recipe.getInput()[i][j].getInstanceByLevel(recipe.getLevel()).getDefaultState());
                    }
                }
            }
            */
        }
    }
}
