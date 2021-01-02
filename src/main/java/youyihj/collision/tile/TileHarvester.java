package youyihj.collision.tile;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.items.IItemHandler;
import youyihj.collision.block.absorber.Absorber;
import youyihj.collision.item.ItemRegistry;
import youyihj.collision.util.CircleIterable;
import youyihj.collision.util.IOType;
import youyihj.collision.util.SingleItemDeviceBase;
import youyihj.collision.util.Utils;

import javax.annotation.Nullable;
import java.util.Iterator;

/**
 * @author youyihj
 */
public class TileHarvester extends SingleItemDeviceBase.TileEntityModule implements ITickableTileEntity {
    public TileHarvester() {
        super(TileEntityRegistry.getTileEntityType("harvester"));
    }

    private boolean doFull;
    private Iterator<BlockPos> offsets;

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        this.doFull = nbt.getBoolean("doFull");
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.putBoolean("doFull", doFull);
        return super.write(compound);
    }

    private int getOffsetY() {
        ItemStack stack = this.item.getStackInSlot(0);
        if (stack.getItem() == ItemRegistry.getItem("up_shifter")) {
            return stack.getCount();
        }
        if (stack.getItem() == ItemRegistry.getItem("down_shifter")) {
            return -stack.getCount();
        }
        return 0;
    }

    private BlockPos getNextPos() {
        if (offsets == null)
            offsets = CircleIterable.of(BlockPos.getAllInBoxMutable(this.pos.add(-3, 0, -3), this.pos.add(3, 0, 3))).iterator();

        return offsets.next().up(getOffsetY());
    }

    public void transformWork() {
        doFull = !doFull;
    }

    public ITextComponent getShowWorkTypeText() {
        return new TranslationTextComponent("message.collision.work_type", new TranslationTextComponent(
                doFull ? "message.collision.full" : "message.collision.empty"
        ));
    }

    @Override
    public void tick() {
        if (world != null && !world.isRemote && world.getGameTime() % 2 == 0) {
            TileNeutronStorage tileNeutronStorage = Utils.getNeutronStorage(world, pos, true, 1);
            TileProtonStorage tileProtonStorage = Utils.getProtonStorage(world, pos, true, 1);
            if (tileNeutronStorage != null && tileProtonStorage != null) {
//                if (!this.energy.consumeEnergy(100, true) ||
//                    !tileNeutronStorage.energy.consumeEnergy(20, true) ||
//                    !tileProtonStorage.energy.consumeEnergy(20, true)) {
//                    return;
//                }
                IItemHandler itemN = tileNeutronStorage.item;
                IItemHandler itemP = tileProtonStorage.item;
                boolean success = false;
                BlockPos workPos = this.getNextPos();
                BlockState blockState = world.getBlockState(workPos);
                if (blockState.getBlock() instanceof Absorber) {
                    Absorber absorber = ((Absorber) blockState.getBlock());
                    ItemStack stack = absorber.getItemStack();
                    if (doFull) {
                        if (absorber.getType() == Absorber.Type.PROTON && itemP.insertItem(0, stack, true) != stack) {
                            itemP.insertItem(0, stack, false);
                            success = true;
                        }
                        if (absorber.getType() == Absorber.Type.NEUTRON && itemP.insertItem(0, stack, true) != stack) {
                            itemN.insertItem(0, stack, false);
                            success = true;
                        }
                    } else {
                        if (absorber.getType() == Absorber.Type.PROTON_EMPTY && itemP.insertItem(0, stack, true) != stack) {
                            itemP.insertItem(0, stack, false);
                            success = true;
                        }
                        if (absorber.getType() == Absorber.Type.NEUTRON_EMPTY && itemP.insertItem(0, stack, true) != stack) {
                            itemN.insertItem(0, stack, false);
                            success = true;
                        }
                    }
                }
                if (success) {
                    world.destroyBlock(workPos, false);
//                    this.energy.consumeEnergy(100, false);
//                    tileNeutronStorage.energy.consumeEnergy(20, false);
//                    tileProtonStorage.energy.consumeEnergy(20, false);
                }
            }
        }
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
    public Container createMenu(int syncID, PlayerInventory playerInventory, PlayerEntity player) {
        return new ContainerHarvester("harvester", syncID, playerInventory, this.getPos(), this.getWorld(), new SingleItemDeviceBase.EnergyBarNumber());
    }
}
