package youyihj.collision.block.spawner;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import youyihj.collision.block.CollisionBlock;

import java.util.List;
import java.util.Random;

public abstract class Spawner extends CollisionBlock {

    public Spawner(String id) {
        super(id, Material.ROCK);
        this.setHardness(8.0f);
        this.setHarvestLevel("pickaxe", 1);
        this.setResistance(30000.0f);
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        drops.clear();
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        if (!worldIn.isRemote) {
            this.getSpawnItems(worldIn).forEach((itemStack) -> {
                Vec3i offset = getRandomOffset(worldIn.rand);
                BlockPos posOffset = pos.add(offset);
                if (worldIn.isAirBlock(posOffset)) {
                    spawnEntityItemWithRandomMotion(worldIn, itemStack, posOffset, offset);
                }
            });
        }
    }

    @Override
    public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
        return false;
    }

    private Vec3i getRandomOffset(Random random) {
        int x = 2;
        int y = 2;
        int z = 2;
        while (x == 2 && y == 2 && z == 2) {
            x = random.nextInt(5);
            y = random.nextInt(5);
            z = random.nextInt(5);
        }
        return new Vec3i(x - 2, y - 2, z - 2);
    }

    private void spawnEntityItemWithRandomMotion(World world, ItemStack itemStack, BlockPos pos, Vec3i offset) {
        EntityItem entityItem = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), itemStack);
        Vec3d vec3d = new Vec3d(offset).normalize().scale(world.rand.nextDouble());
        entityItem.motionX = vec3d.x;
        entityItem.motionY = vec3d.y;
        entityItem.motionZ = vec3d.z;
        world.spawnEntity(entityItem);
    }

    public abstract List<ItemStack> getSpawnItems(World world);
}
