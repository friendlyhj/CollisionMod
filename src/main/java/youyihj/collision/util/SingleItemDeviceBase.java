package youyihj.collision.util;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import youyihj.collision.tile.BlockHasTileEntityBase;
import youyihj.collision.tile.ContainerRegistry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

/**
 * @author youyihj
 */
public class SingleItemDeviceBase {
    private SingleItemDeviceBase() {}

    public static final int ENERGY_CAPACITY = 32000;
    public static final int ENERGY_MAX_RECEIVE = 1000;

    public static abstract class TileEntityModule extends TileEntity implements INamedContainerProvider {

        protected TileEntityModule(TileEntityType<?> tileEntityTypeIn) {
            super(tileEntityTypeIn);
        }

        public final ItemStackHandler item = new ItemStackHandler(1) {
            @Override
            protected void onContentsChanged(int slot) {
                TileEntityModule.this.markDirty();
            }
        };

        private final Inventory inventory = new Inventory(1);

        public final EnergyStorageSerializable energy = new EnergyStorageSerializable(ENERGY_CAPACITY, ENERGY_MAX_RECEIVE, 0);

        public abstract IOType getIOType();

        public abstract boolean canEditIOType();

        public Inventory getInventory() {
            return inventory;
        }

        @Override
        public void read(BlockState state, CompoundNBT nbt) {
            super.read(state, nbt);
            this.item.deserializeNBT(nbt.getCompound("item"));
            INBT energyNbt =  nbt.get("energy");
            if (energyNbt != null && energyNbt.getId() == 3)
                this.energy.deserializeNBT(((IntNBT) energyNbt));
        }

        @Override
        public CompoundNBT write(CompoundNBT compound) {
            compound.put("item", this.item.serializeNBT());
            compound.put("energy", this.energy.serializeNBT());
            return super.write(compound);
        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            if (cap == CapabilityEnergy.ENERGY) {
                return LazyOptional.of(() -> this.energy).cast();
            }
            if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
                return LazyOptional.of(() -> side == null ? this.item : new ItemStackHandler() {
                    @Override
                    public void setStackInSlot(int slot, @Nonnull ItemStack stack) {
                        item.setStackInSlot(slot, stack);
                    }

                    @Override
                    public int getSlots() {
                        return item.getSlots();
                    }

                    @Nonnull
                    @Override
                    public ItemStack getStackInSlot(int slot) {
                        return item.getStackInSlot(slot);
                    }

                    @Nonnull
                    @Override
                    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                        return TileEntityModule.this.getIOType() == IOType.OUTPUT ? stack : item.insertItem(slot, stack, simulate);
                    }

                    @Nonnull
                    @Override
                    public ItemStack extractItem(int slot, int amount, boolean simulate) {
                        return TileEntityModule.this.getIOType() == IOType.INPUT ? ItemStack.EMPTY : item.extractItem(slot, amount, simulate);
                    }

                    @Override
                    public int getSlotLimit(int slot) {
                        return item.getSlotLimit(slot);
                    }
                }).cast();
            }
            return super.getCapability(cap, side);
        }

        @Override
        public ITextComponent getDisplayName() {
            if (world == null || pos == null)
                return new StringTextComponent("");
            return new TranslationTextComponent(world.getBlockState(pos).getBlock().getTranslationKey());
        }
    }

    public static abstract class BlockModule<T extends TileEntityModule> extends BlockHasTileEntityBase<T> {

        public BlockModule(String name, Properties properties) {
            super(name, properties.harvestTool(ToolType.PICKAXE).harvestLevel(1).hardnessAndResistance(3, 50));
        }

        @Override
        public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
            if (!worldIn.isRemote) {
                getLinkedTileEntity(worldIn, pos).ifPresent(tileEntity -> {
                    if (extraWorkCondition(state, worldIn, pos, player, handIn, hit, tileEntity)) {
                        extraWork(state, worldIn, pos, player, handIn, hit, tileEntity);
                    } else {
                        NetworkHooks.openGui(((ServerPlayerEntity) player), tileEntity, packetBuffer -> {
                            packetBuffer.writeBlockPos(tileEntity.getPos());
                            packetBuffer.writeString(worldIn.getDimensionKey().getLocation().toString());
                        });
                    }
                });
            }
            return ActionResultType.SUCCESS;
        }

        protected boolean extraWorkCondition(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit, T tileEntity) {
            return false;
        }

        protected void extraWork(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit, T tileEntity) {

        }

        @Override
        public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
            if (!state.isIn(newState.getBlock())) {
                getLinkedTileEntity(worldIn, pos).ifPresent(t -> InventoryHelper.dropItems(worldIn, pos, NonNullList.from(ItemStack.EMPTY, t.item.getStackInSlot(0))));
            }
            super.onReplaced(state, worldIn, pos, newState, isMoving);
        }
    }

    public static abstract class ContainerModule extends Container {
        private final EnergyBarNumber energyBarNumber;
        private final World world;
        private final BlockPos pos;

        public ContainerModule(String name, int id, PlayerInventory inventoryPlayer, BlockPos pos, World world, EnergyBarNumber intArray) {
            super(ContainerRegistry.getContainerType(name), id);
            this.world = world;
            this.pos = pos;
            this.energyBarNumber = intArray;
            this.trackIntArray(intArray);
            TileEntityModule tileEntity = (TileEntityModule) world.getTileEntity(pos);
            Objects.requireNonNull(tileEntity);
            this.addSlot(new SlotItemHandler(tileEntity.item, 0, 80, 32));
            for (int i = 0; i < 9; i++) {
                this.addSlot(new Slot(inventoryPlayer, i, 8 + 18 * i, 152));
                this.addSlot(new Slot(inventoryPlayer, i + 9, 8 + 18 * i, 94));
                this.addSlot(new Slot(inventoryPlayer, i + 18, 8 + 18 * i, 112));
                this.addSlot(new Slot(inventoryPlayer, i + 27, 8 + 18 * i, 130));
            }
        }

        @Override
        public boolean canInteractWith(PlayerEntity playerIn) {
            return playerIn.world.equals(this.world) && playerIn.getDistanceSq(Vector3d.copyCentered(this.pos)) < 64.0d;
        }

        @Override
        public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
            ItemStack itemstack = ItemStack.EMPTY;
            if (index < 0) {
                return itemstack;
            }
            Slot slot = this.inventorySlots.get(index);

            if (slot != null && slot.getHasStack())
            {
                ItemStack itemstack1 = slot.getStack();
                itemstack = itemstack1.copy();

                if (index == 0)
                {
                    if (!this.mergeItemStack(itemstack1, 1, 37, true))
                    {
                        return ItemStack.EMPTY;
                    }

                    slot.onSlotChange(itemstack1, itemstack);
                }
                else if (this.mergeItemStack(itemstack1, 0, 1, false))
                {
                    return ItemStack.EMPTY;
                }
                else if (index < 28)
                {
                    if (!this.mergeItemStack(itemstack1, 28, 37, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
                else if (index < 37)
                {
                    if (!this.mergeItemStack(itemstack1, 1, 28, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
                else if (!this.mergeItemStack(itemstack1, 1, 37, false))
                {
                    return ItemStack.EMPTY;
                }

                if (itemstack1.isEmpty())
                {
                    slot.putStack(ItemStack.EMPTY);
                }
                else
                {
                    slot.onSlotChanged();
                }

                if (itemstack1.getCount() == itemstack.getCount())
                {
                    return ItemStack.EMPTY;
                }

                slot.onTake(playerIn, itemstack1);
            }

            return itemstack;
        }

        @Override
        public void detectAndSendChanges() {
            super.detectAndSendChanges();
            TileEntity tileEntity = this.world.getTileEntity(pos);
            if (tileEntity != null && TileEntityModule.class.isAssignableFrom(tileEntity.getClass())) {
                int energy = ((TileEntityModule) tileEntity).energy.getEnergyStored();
                if (energy != this.energyBarNumber.get(0)) {
                    this.energyBarNumber.set(0, energy);
                }
            }
        }

        public BlockPos getPos() {
            return pos;
        }

        public int getEnergy() {
            return energyBarNumber.get(0);
        }
    }

    public static class EnergyBarNumber implements IIntArray {

        private int energy = 0;

        @Override
        public int get(int index) {
            return energy;
        }

        @Override
        public void set(int index, int value) {
            energy = value;
        }

        @Override
        public int size() {
            return 1;
        }
    }
}
