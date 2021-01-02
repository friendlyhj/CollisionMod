package youyihj.collision.tile;

import net.minecraft.block.SoundType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import youyihj.collision.block.BlockStructureBuilder;
import youyihj.collision.block.ColliderBase;
import youyihj.collision.block.absorber.Absorber;
import youyihj.collision.block.absorber.Neutron;
import youyihj.collision.block.absorber.Proton;
import youyihj.collision.recipe.ColliderRecipeCache;
import youyihj.collision.util.IOType;
import youyihj.collision.util.SingleItemDeviceBase;
import youyihj.collision.util.Utils;

import javax.annotation.Nullable;

/**
 * @author youyihj
 */
public class TileStructureBuilder extends SingleItemDeviceBase.TileEntityModule implements ITickableTileEntity {
    public TileStructureBuilder() {
        super(TileEntityRegistry.getTileEntityType(BlockStructureBuilder.NAME));
    }


    @Override
    public IOType getIOType() {
        return IOType.BOTH;
    }

    @Override
    public boolean canEditIOType() {
        return false;
    }

    @Nullable
    @Override
    public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
        return new ContainerStructureBuilder(BlockStructureBuilder.NAME, p_createMenu_1_, p_createMenu_2_, this.getPos(), this.getWorld(), new SingleItemDeviceBase.EnergyBarNumber());
    }

    @Override
    public void tick() {
        if (world == null || pos == null)
            return;
//        if (!this.energy.consumeEnergy(500, true))
//            return;
        if (!world.isRemote && world.getGameTime() % 10 == 0) {
            BlockPos.Mutable posOffset = pos.up().toMutable();
            int posOffsetY = 1;
            while (!(world.getBlockState(posOffset).getBlock() instanceof ColliderBase)) {
                if (!world.getBlockState(posOffset).allowsMovement(world, pos, PathType.AIR) || World.isOutsideBuildHeight(pos))
                    return;
                posOffsetY++;
                posOffset.move(Direction.UP);
            }
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (i == 1 && j == 1)
                        continue;
                    if (!world.isAirBlock(pos.add(i - 1, posOffsetY, j - 1)))
                        return;
                }
            }
            ColliderRecipeCache.get(world.getRecipeManager(), pos, recipe -> recipe.getRecipeOutput().isItemEqual(this.item.getStackInSlot(0)))
                    .ifPresent(recipe -> {
                        int p = 0;
                        int n = 0;
                        TileProtonStorage protonStorage = Utils.getProtonStorage(world, pos, false, 1);
                        TileNeutronStorage neutronStorage = Utils.getNeutronStorage(world, pos, false, 1);
                        if (protonStorage == null || neutronStorage == null)
                            return;
//                        if (!(protonStorage.energy.consumeEnergy(100, true) && neutronStorage.energy.consumeEnergy(100, true)))
//                            return;
                        for (int i = 0; i < 3; i++) {
                            for (int j = 0; j < 3; j++) {
                                if (i == 1 && j == 1)
                                    continue;
                                Absorber.Type type = recipe.getIn()[i][j];
                                if (type == Absorber.Type.PROTON)
                                    p++;
                                if (type == Absorber.Type.NEUTRON)
                                    n++;
                            }
                        }
                        ItemStack itemN = neutronStorage.item.extractItem(0, n, true);
                        ItemStack itemP = neutronStorage.item.extractItem(0, p, true);
                        if (itemP.getCount() < p || itemN.getCount() < n)
                            return;
                        if (itemP.isEmpty() && itemN.isEmpty())
                            return;
                        if (recipe.isRefined()) {
                            if (Proton.Refined.INSTANCE.match(itemP, true) && Neutron.Refined.INSTANCE.match(itemN, true)) {
                                protonStorage.item.extractItem(0, p, false);
                                neutronStorage.item.extractItem(0, n, false);
                            } else return;
                            if (Proton.INSTANCE.match(itemP, true) && Neutron.INSTANCE.match(itemN, true)) {
                                protonStorage.item.extractItem(0, p, false);
                                neutronStorage.item.extractItem(0, p, false);
                            } else return;
                        }
//                        this.energy.consumeEnergy(500, false);
//                        protonStorage.energy.consumeEnergy(100, false);
//                        neutronStorage.energy.consumeEnergy(100, false);
                        for (int i = 0; i < 3; i++) {
                            for (int j = 0; j < 3; j++) {
                                Absorber.Type type = recipe.getIn()[i][j];
                                if (type == null)
                                    continue;
                                posOffset.move(i - 1, 0, j - 1);
                                world.setBlockState(posOffset, type.getAbsorberByLevel(recipe.getLevel()).getDefaultState());
                                posOffset.move(1 - i, 0, 1 - j);
                            }
                        }
                        SoundType stonePlaceType = SoundType.STONE;
                        world.playSound(null, posOffset, stonePlaceType.getPlaceSound(), SoundCategory.BLOCKS, (stonePlaceType.getVolume() + 1.0F) / 2.0F, stonePlaceType.getPitch() * 0.8F);
                    });
        }
    }
}
