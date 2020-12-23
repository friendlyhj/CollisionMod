package youyihj.collision.tile;

import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

/**
 * @author youyihj
 */
public abstract class TileSpawner extends TileEntity implements ITickableTileEntity {
    private int hasSpawnAmount;

    public TileSpawner(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    protected abstract int getLimitedAmount();

    protected abstract int getSpeed();

    protected abstract ItemStack getNextSpawnItem(Random random);

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        compound.putInt("hasSpawnAmount", hasSpawnAmount);
        return compound;
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        this.hasSpawnAmount = nbt.getInt("hasSpawnAmount");
    }

    @Override
    public void tick() {
        if (world != null && world instanceof ServerWorld && world.rand.nextInt(getSpeed()) == 0) {
            ServerWorld worldIn = ((ServerWorld) world);
            Vector3i offset = getRandomOffset(worldIn.rand);
            BlockPos posOffset = pos.add(offset);
            if (worldIn.isAirBlock(posOffset)) {
                spawnEntityItemWithRandomMotion(worldIn, getNextSpawnItem(worldIn.rand), posOffset, offset);
            }
            hasSpawnAmount++;
            this.markDirty();
            if (hasSpawnAmount >= getLimitedAmount()) {
                worldIn.destroyBlock(pos, false);
            }
        }
    }

    private Vector3i getRandomOffset(Random random) {
        int x = 2;
        int y = 2;
        int z = 2;
        while (x == 2 && y == 2 && z == 2) {
            x = random.nextInt(5);
            y = random.nextInt(5);
            z = random.nextInt(5);
        }
        return new Vector3i(x - 2, y - 2, z - 2);
    }

    private void spawnEntityItemWithRandomMotion(ServerWorld world, ItemStack itemStack, BlockPos pos, Vector3i offset) {
        ItemEntity entityItem = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), itemStack);
        Vector3d vec3d = new Vector3d(offset.getX(), offset.getY(), offset.getZ()).normalize().scale(world.rand.nextDouble());
        entityItem.setMotion(vec3d);
        world.summonEntity(entityItem);
    }
}
