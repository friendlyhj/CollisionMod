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
import youyihj.collision.util.IOType;
import youyihj.collision.util.SingleItemDeviceBase;
import youyihj.collision.util.Utils;
import youyihj.collision.item.ItemRegistryHandler;

/**
 * @author youyihj
 */
public class TileHarvester extends SingleItemDeviceBase.TileEntityModule implements ITickable {
    private boolean doFull;
    private int posOffsetX = -3;
    private int posOffsetZ = -3;

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        doFull = compound.getBoolean("doFull");
        posOffsetX = compound.getInteger("posOffsetX");
        posOffsetZ = compound.getInteger("posOffsetZ");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setBoolean("doFull", doFull);
        compound.setInteger("posOffsetX", posOffsetX);
        compound.setInteger("posOffsetZ", posOffsetZ);
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
            if (stack.getMetadata() == 3) return stack.getCount();
            if (stack.getMetadata() == 4) return -stack.getCount();
        }
        return 0;
    }

    private BlockPos getNextPos() {
        if (posOffsetX > 3) {
            posOffsetX = -3;
            posOffsetZ++;
        } else {
            posOffsetX++;
        }
        if (posOffsetZ > 3) {
            posOffsetZ = -3;
            posOffsetX = -3;
        }
        return this.pos.add(posOffsetX, getPosOffsetY(), posOffsetZ);
    }

    @Override
    public void update() {
        if (!world.isRemote && world.getWorldTime() % 2 == 0) {
            TileNeutronStorage tileNeutronStorage = Utils.getNeutronStorage(world, pos, true, 1);
            TileProtonStorage tileProtonStorage = Utils.getProtonStorage(world, pos, true, 1);
            if (tileNeutronStorage != null && tileProtonStorage != null) {
                if (!this.energy.consumeEnergy(100, true) ||
                        !tileNeutronStorage.energy.consumeEnergy(20, true) ||
                        !tileProtonStorage.energy.consumeEnergy(20, true)) return;
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
