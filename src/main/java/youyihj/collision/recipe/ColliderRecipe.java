package youyihj.collision.recipe;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import youyihj.collision.Collision;
import youyihj.collision.block.BlockRegistry;
import youyihj.collision.block.ColliderBase;
import youyihj.collision.block.absorber.Absorber;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author youyihj
 */
public class ColliderRecipe implements IRecipe<IInventory> {
    static final ResourceLocation ID = new ResourceLocation(Collision.MODID, "collider");

    private final ItemStack out;
    private final Absorber.Type[][] in;
    private final int level;
    private final ResourceLocation name;

    public ColliderRecipe(ResourceLocation name, int level, ItemStack out, Absorber.Type[][] in) {
        this.out = out;
        this.in = in;
        this.level = level;
        this.name = name;
    }

    @Override
    @Deprecated
    public boolean matches(IInventory inv, World worldIn) {
        return false;
    }

    public boolean matches(World worldIn, BlockPos pos) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                BlockPos posOffset = pos.add(i - 1, 0, j - 1);
                Block block = worldIn.getBlockState(posOffset).getBlock();
                if (i == 1 && j == 1) continue;
                Absorber.Type a = in[i][j];
                if ((block != Blocks.AIR || a != null) && ((!(block instanceof Absorber) || ((Absorber) block).getType() != a) || ((Absorber) block).isRefined() != this.isRefined())) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public ItemStack getCraftingResult(@Nullable IInventory inv) {
        return this.out.copy();
    }

    @Override
    public boolean canFit(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return this.out.copy();
    }

    @Override
    public ResourceLocation getId() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public boolean isRefined() {
        return getLevel() > 2;
    }

    public Absorber.Type[][] getIn() {
        return in;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ColliderRecipeSerializer.INSTANCE;
    }

    @Override
    public IRecipeType<?> getType() {
        return ColliderRecipeType.INSTANCE;
    }

    int[] inSerial() {
        int[] temp = new int[9];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Absorber.Type a = in[i][j];
                if (a == Absorber.Type.PROTON) {
                    temp[i + j * 3] = 2;
                } else if (a == Absorber.Type.NEUTRON) {
                    temp[i + j * 3] = 3;
                }
            }
        }
        return temp;
    }

    @Override
    public ItemStack getIcon() {
        return new ItemStack(BlockRegistry.getBlock(ColliderBase.getRegistryName(1)));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ColliderRecipe that = (ColliderRecipe) o;
        return level == that.level &&
                Objects.equals(out, that.out) &&
                Arrays.equals(in, that.in) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(out, level, name, inSerial());
    }
}
