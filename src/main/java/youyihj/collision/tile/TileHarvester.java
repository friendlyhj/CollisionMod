package youyihj.collision.tile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.items.IItemHandler;
import youyihj.collision.block.absorber.Absorber;
import youyihj.collision.block.absorber.EnumAbsorber;
import youyihj.collision.util.*;
import youyihj.collision.item.ItemRegistryHandler;

import java.util.Iterator;

/**
 * @author youyihj
 */
public class TileHarvester extends SingleItemDeviceBase.TileEntityModule implements ITickable {
    private boolean doFull;
    private Iterator<BlockPos> offsets;

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        offsets = new CircleIterable<>(BlockPos.getAllInBox(this.pos.add(-3, 0, -3), this.pos.add(3, 0, 3))).iterator();
        doFull = compound.getBoolean("doFull");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setBoolean("doFull", doFull);
        return super.writeToNBT(compound);
    }

    public void transformWork() {
        doFull = !doFull;
    }

    public TextComponentTranslation getShowWorkTypeText() {
        return new TextComponentTranslation("message.collision.work_type", new TextComponentTranslation(
                doFull ? "message.collision.full" : "message.collision.empty"
        ));
    }

    @Override
    public IOType getIOType() {
        return IOType.BOTH;
    }

    @Override
    public boolean canEditIOType() {
        return false;
    }

    private int getPosOffsetY() {
        ItemStack stack = this.item.getStackInSlot(0);
        if (stack.getItem() == ItemRegistryHandler.itemHashMap.get("material")) {
            if (stack.getMetadata() == 3) {
                return stack.getCount();
            }
            if (stack.getMetadata() == 4) {
                return -stack.getCount();
            }
        }
        return 0;
    }

    private BlockPos getNextPos() {
        return offsets.next().up(getPosOffsetY());
    }

    @Override
    public void update() {
        if (!world.isRemote && world.getWorldTime() % 2 == 0) {
            TileNeutronStorage tileNeutronStorage = Utils.getNeutronStorage(world, pos, true, 1);
            TileProtonStorage tileProtonStorage = Utils.getProtonStorage(world, pos, true, 1);
            if (tileNeutronStorage != null && tileProtonStorage != null) {
                if (!this.energy.consumeEnergy(100, true) ||
                        !tileNeutronStorage.energy.consumeEnergy(20, true) ||
                        !tileProtonStorage.energy.consumeEnergy(20, true)) {
                    return;
                }
                IItemHandler itemN = tileNeutronStorage.item;
                IItemHandler itemP = tileProtonStorage.item;
                boolean success = false;
                BlockPos workPos = this.getNextPos();
                IBlockState state = world.getBlockState(workPos);
                if (state.getBlock() instanceof Absorber) {
                    Absorber absorber = ((Absorber) state.getBlock());
                    ItemStack stack = absorber.getItemStack();
                    if (doFull) {
                        if (absorber.getType() == EnumAbsorber.PROTON && itemP.insertItem(0, stack, true) != stack) {
                            itemP.insertItem(0, stack, false);
                            success = true;
                        } else if (absorber.getType() == EnumAbsorber.NEUTRON && itemN.insertItem(0, stack, true) != stack) {
                            itemN.insertItem(0, stack, false);
                            success = true;
                        }
                    } else {
                        if (absorber.getType() == EnumAbsorber.PROTON_EMPTY && itemP.insertItem(0, stack, true) != stack) {
                            itemP.insertItem(0, stack, false);
                            success = true;
                        } else if (absorber.getType() == EnumAbsorber.NEUTRON_EMPTY && itemN.insertItem(0, stack, true) != stack) {
                            itemN.insertItem(0, stack, false);
                            success = true;
                        }
                    }
                }
                if (success) {
                    world.destroyBlock(workPos, false);
                    this.energy.consumeEnergy(100, false);
                    tileNeutronStorage.energy.consumeEnergy(20, false);
                    tileProtonStorage.energy.consumeEnergy(20, false);
                }
            }
        }
    }
}
