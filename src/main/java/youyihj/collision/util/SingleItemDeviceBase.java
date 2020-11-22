package youyihj.collision.util;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.*;
import youyihj.collision.Collision;
import youyihj.collision.block.CollisionBlock;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author youyihj
 */
public final class SingleItemDeviceBase {
    private SingleItemDeviceBase() {}

    public static final int ENERGY_CAPACITY = 32000;
    public static final int ENERGY_MAX_RECEIVE = 1000;

    public static abstract class TileEntityModule extends TileEntity {

        public final ItemStackHandler item = new ItemStackHandler(1) {
            @Override
            protected void onContentsChanged(int slot) {
                TileEntityModule.this.markDirty();
            }
        };

        public final EnergyStorageSerializable energy = new EnergyStorageSerializable(ENERGY_CAPACITY, ENERGY_MAX_RECEIVE, 0);

        public abstract IOType getIOType();

        public abstract boolean canEditIOType();

        @Override
        public void readFromNBT(NBTTagCompound compound) {
            this.item.deserializeNBT(compound.getCompoundTag("item"));
            this.energy.readFromNBT(compound);
            super.readFromNBT(compound);
        }

        @Override
        public NBTTagCompound writeToNBT(NBTTagCompound compound) {
            compound.setTag("item", this.item.serializeNBT());
            return super.writeToNBT(this.energy.writeToNBT(compound));
        }

        @Override
        public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
            Capability<IItemHandler> iItemHandlerCapability = CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
            Capability<IEnergyStorage> iEnergyStorageCapability = CapabilityEnergy.ENERGY;
            return iItemHandlerCapability.equals(capability) || iEnergyStorageCapability.equals(capability) || super.hasCapability(capability, facing);
        }

        @Nullable
        @Override
        public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
            Capability<IItemHandler> iItemHandlerCapability = CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
            Capability<IEnergyStorage> iEnergyStorageCapability = CapabilityEnergy.ENERGY;
            if (iItemHandlerCapability.equals(capability)) {
                return iItemHandlerCapability.cast(facing == null ? this.item : new IItemHandlerModifiable() {
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
                });
            }
            if (iEnergyStorageCapability.equals(capability) && facing != null) {
                return iEnergyStorageCapability.cast(this.energy);
            }
            return super.getCapability(capability, facing);
        }
    }

    public static abstract class ContainerModule extends Container {
        protected final World world;
        protected final BlockPos pos;
        protected final IItemHandler item;
        private int energyStored = 0;

        public int getEnergyStored() {
            return energyStored;
        }

        public ContainerModule(EntityPlayer player, World world, BlockPos pos) {
            this.world = world;
            this.pos = pos;
            TileEntity tileEntity = world.getTileEntity(pos);
            Capability<IItemHandler> itemHandlerCapability = CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
            this.item = tileEntity.getCapability(itemHandlerCapability, null);
            InventoryPlayer inventoryPlayer = player.inventory;
            this.addSlotToContainer(new SlotItemHandler(this.item, 0, 80, 32));
            for (int i = 0; i < 9; i++) {
                this.addSlotToContainer(new Slot(inventoryPlayer, i, 8 + 18 * i, 152));
                this.addSlotToContainer(new Slot(inventoryPlayer, i + 9, 8 + 18 * i, 94));
                this.addSlotToContainer(new Slot(inventoryPlayer, i + 18, 8 + 18 * i, 112));
                this.addSlotToContainer(new Slot(inventoryPlayer, i + 27, 8 + 18 * i, 130));
            }
        }

        @Override
        public boolean canInteractWith(EntityPlayer playerIn) {
            return playerIn.world.equals(this.world) && playerIn.getDistanceSq(this.pos) < 64.0d;
        }

        @Override
        public void detectAndSendChanges() {
            super.detectAndSendChanges();
            TileEntity tileEntity = this.world.getTileEntity(pos);
            if (tileEntity != null && TileEntityModule.class.isAssignableFrom(tileEntity.getClass())) {
                int energy = ((TileEntityModule) tileEntity).energy.getEnergyStored();
                if (energy == this.getEnergyStored()) {
                    return;
                }
                this.energyStored = energy;
                this.listeners.forEach(listener -> listener.sendWindowProperty(this, 0, energy));
            }
        }

        @Override
        @SideOnly(Side.CLIENT)
        public void updateProgressBar(int id, int data) {
            if (id == 0) {
                this.energyStored = data;
            }
        }

        @Override
        public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
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
    }

    @SideOnly(Side.CLIENT)
    public static abstract class GuiContainerModule extends GuiContainer {

        private static final ResourceLocation DEFAULT_TEXTURE = new ResourceLocation(Collision.MODID, "textures/gui/single_item_device.png");

        protected final BlockPos pos;

        public GuiContainerModule(ContainerModule inventorySlotsIn) {
            super(inventorySlotsIn);
            this.xSize = 176;
            this.ySize = 176;
            this.pos = inventorySlotsIn.pos;
        }

        @Override
        public void drawScreen(int mouseX, int mouseY, float partialTicks) {
            super.drawDefaultBackground();
            super.drawScreen(mouseX, mouseY, partialTicks);
            this.renderHoveredToolTip(mouseX, mouseY);
        }

        @Override
        protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
            int left = (this.width - this.xSize) / 2;
            int top = (this.height - this.ySize) / 2;
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            this.mc.getTextureManager().bindTexture(getTexture());
            this.drawTexturedModalRect(left, top, 0, 0, xSize, ySize);
            int barWidth = 18;
            int barHeight = 54 - Math.round(((ContainerModule) this.inventorySlots).energyStored * 54 / ENERGY_CAPACITY);
            this.drawTexturedModalRect(left + 14, top + 18, 176, 0, barWidth, barHeight);
        }

        @Override
        protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
            this.drawCenteredString(this.fontRenderer, getTitle(), xSize / 2, 6, -1);
        }

        @Override
        protected void renderHoveredToolTip(int mouseX, int mouseY) {
            super.renderHoveredToolTip(mouseX, mouseY);
            int left = (this.width - this.xSize) / 2;
            int top = (this.height - this.ySize) / 2;
            int x = mouseX - left;
            int y = mouseY - top;
            if (x >= 14 && x <= 32 && y >= 18 && y <= 72) {
                GuiUtils.drawHoveringText(Lists.newArrayList(
                        I18n.format("tooltip.collision.energy_cap",
                                ((ContainerModule) this.inventorySlots).energyStored, ENERGY_CAPACITY)
                ), mouseX, mouseY, this.width, this.height, 300, this.fontRenderer);
            }
        }

        public abstract String getTitle();

        public ResourceLocation getTexture() {
            return DEFAULT_TEXTURE;
        }
    }

    public static abstract class BlockModule extends CollisionBlock {
        public BlockModule(String id, Material material) {
            super(id, material);
            setHarvestLevel("pickaxe", 1);
        }

        @Override
        public boolean hasTileEntity(IBlockState state) {
            return true;
        }

        @Override
        public abstract TileEntityModule createTileEntity(World world, IBlockState state);

        @Override
        public abstract boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ);

        @Override
        public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            Capability<IItemHandler> itemHandlerCapability = CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
            IItemHandler in = tileEntity.getCapability(itemHandlerCapability, EnumFacing.NORTH);
            Block.spawnAsEntity(worldIn, pos, in.getStackInSlot(0));
            super.breakBlock(worldIn, pos, state);
        }
    }
}
