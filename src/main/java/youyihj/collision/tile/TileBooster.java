package youyihj.collision.tile;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import youyihj.collision.item.ItemNucleus;

import javax.annotation.Nullable;

/**
 * @author youyihj
 */
public class TileBooster extends TileEntity {
    public TileBooster(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public TileBooster() {
        super(TileEntityRegistry.getTileEntityType("booster"));
    }

    private ItemNucleus.NucleusEntry type = ItemNucleus.NucleusEntry.getInvalid();
    private boolean isFull;

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        this.isFull = nbt.getBoolean("isFull");
        ItemNucleus.NucleusEntry entry = ItemNucleus.nuclei.get(nbt.getString("nucleus_type"));
        if (entry != null) {
            this.type = entry;
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.putString("nucleus_type", type.getName());
        compound.putBoolean("isFull", isFull);
        return super.write(compound);
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return write(new CompoundNBT());
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tag) {
        read(state, tag);
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        CompoundNBT nbt = pkt.getNbtCompound();
        if (world == null)
            return;
        handleUpdateTag(world.getBlockState(pos), nbt);
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(pos, 1, getUpdateTag());
    }

    public boolean isFull() {
        return isFull;
    }

    public void setFull(boolean full) {
        isFull = full;
    }

    public ItemNucleus.NucleusEntry getNucleusType() {
        return type;
    }

    public void setNucleusType(ItemNucleus.NucleusEntry type) {
        this.type = type;
    }
}
