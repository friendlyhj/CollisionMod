package youyihj.collision.block.absorber;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import youyihj.collision.Configuration;
import youyihj.collision.block.HasSpecialItemBlock;

import javax.annotation.Nullable;
import java.util.Random;

import static youyihj.collision.item.ItemRegistryHandler.itemBlockHashMap;

public abstract class Absorber extends HasSpecialItemBlock {
    private boolean isEmpty;

    public Absorber(String id, boolean isEmpty, boolean tickRandomly) {
        super(id, Material.ROCK);
        this.isEmpty = isEmpty;
        this.setTickRandomly(tickRandomly);
        this.setHardness(3.0f);
        this.setResistance(50.0f);
        this.setHarvestLevel("pickaxe", 0);
    }
    public abstract Absorber getTransformAbsorber();

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random random) {
        if (!worldIn.isRemote && worldIn.canSeeSky(pos.up()) && random.nextInt() % Configuration.absorberConfig.absorberSpeed == 0 && work(worldIn)) {
            this.transform(worldIn, pos);
        }
    }

    private boolean work(World world) {
        return !Configuration.absorberConfig.onlyWorkInDaytime || world.isDaytime();
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    @Override
    public ItemBlock getItemBlock() {
        return new ItemBlock(this) {
            @Override
            public boolean hasContainerItem(ItemStack stack) {
                Absorber absorber = (Absorber) getThis();
                return !absorber.isEmpty();
            }

            @Nullable
            @Override
            public Item getContainerItem() {
                Absorber absorber = (Absorber) getThis();
                return absorber.isEmpty() ? null : itemBlockHashMap.get(absorber.getTransformAbsorber().getRegistryName().getResourcePath());
            }
        };
    }

    public void transform(World world, BlockPos pos) {
        world.setBlockState(pos, this.getTransformAbsorber().getDefaultState());
    }
}
