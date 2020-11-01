package youyihj.collision.tile;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import youyihj.collision.block.ColliderBase;
import youyihj.collision.block.absorber.Absorber;
import youyihj.collision.block.absorber.EnumAbsorber;
import youyihj.collision.block.absorber.Neutron;
import youyihj.collision.block.absorber.Proton;
import youyihj.collision.core.SingleItemDeviceBase;
import youyihj.collision.recipe.ColliderRecipe;

public class TileStructureBuilder extends SingleItemDeviceBase.TileEntityModule implements ITickable {

    @Override
    public void update() {
        if (!world.isRemote && world.getWorldTime() % 10 == 0) {
            BlockPos posOffset = pos.up();
            int posOffsetY = 1;
            while (!(world.getBlockState(posOffset).getBlock() instanceof ColliderBase)) {
                if (!world.isAirBlock(posOffset) || world.isOutsideBuildHeight(posOffset)) return;
                posOffsetY++;
                posOffset = posOffset.up();
            }
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (i == 1 && j == 1) continue;
                    if (!world.isAirBlock(pos.add(i - 1, posOffsetY, j - 1))) return;
                }
            }
            ColliderRecipe recipe = ColliderRecipe.getRecipe(this.item.getStackInSlot(0));
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
            if (!this.energy.consumeEnergy(500, true)) return;
            TileProtonStorage protonStorage = this.getProtonStorage();
            TileNeutronStorage neutronStorage = this.getNeutronStorage();

            if (protonStorage != null && neutronStorage != null) {
                if (!protonStorage.energy.consumeEnergy(100, true)) return;

                if (!neutronStorage.energy.consumeEnergy(100, true)) return;

                ItemStack itemP = protonStorage.item.extractItem(0, p, true);
                ItemStack itemN = neutronStorage.item.extractItem(0, n, true);
                if (itemP.isEmpty() && itemN.isEmpty()) return;
                if (recipe.isAdvanced()) {
                    if (Proton.Refined.INSTANCE.match(itemP, true) && Neutron.Refined.INSTANCE.match(itemN, true)) {
                        protonStorage.item.extractItem(0, p, false);
                        neutronStorage.item.extractItem(0, n, false);
                    } else {
                        return;
                    }
                } else {
                    if (Proton.INSTANCE.match(itemP, true) && Neutron.INSTANCE.match(itemN, true)) {
                        protonStorage.item.extractItem(0, p, false);
                        neutronStorage.item.extractItem(0, n, false);
                    } else {
                        return;
                    }
                }
                this.energy.consumeEnergy(500, false);
                protonStorage.energy.consumeEnergy(100, false);
                neutronStorage.energy.consumeEnergy(100, false);
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        EnumAbsorber absorber = recipe.getInput()[i][j];
                        if (absorber == null) continue;
                        posOffset = this.getPos().add(i - 1, posOffsetY, j - 1);
                        world.setBlockState(posOffset, absorber.getInstanceByLevel(recipe.getLevel()).getDefaultState());
                    }
                }
            }
        }
    }

    private TileNeutronStorage getNeutronStorage() {
        for (BlockPos pos : BlockPos.getAllInBox(pos.add(-1, -1, -1), pos.add(1, 1, 1))) {
            TileEntity tileEntity = world.getTileEntity(pos);
            if (tileEntity != null && tileEntity instanceof TileNeutronStorage) {
                TileNeutronStorage neutronStorage = ((TileNeutronStorage) tileEntity);
                Item item = neutronStorage.item.getStackInSlot(0).getItem();
                if (item instanceof ItemBlock) {
                    Block block = ((ItemBlock) item).getBlock();
                    if (block instanceof Absorber && ((Absorber) block).getType() == EnumAbsorber.NEUTRON) {
                        return neutronStorage;
                    }
                }
            }
        }
        return null;
    }

    private TileProtonStorage getProtonStorage() {
        for (BlockPos pos : BlockPos.getAllInBox(pos.add(-1, -1, -1), pos.add(1, 1, 1))) {
            TileEntity tileEntity = world.getTileEntity(pos);
            if (tileEntity != null && tileEntity instanceof TileProtonStorage) {
                TileProtonStorage protonStorage = ((TileProtonStorage) tileEntity);
                Item item = protonStorage.item.getStackInSlot(0).getItem();
                if (item instanceof ItemBlock) {
                    Block block = ((ItemBlock) item).getBlock();
                    if (block instanceof Absorber && ((Absorber) block).getType() == EnumAbsorber.PROTON) {
                        return protonStorage;
                    }
                }
            }
        }
        return null;
    }
}
