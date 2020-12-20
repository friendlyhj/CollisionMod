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
import youyihj.collision.block.absorber.Absorber;

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

    public boolean matches(IInventory inv, World worldIn, BlockPos pos) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Block block = worldIn.getBlockState(pos).getBlock();
                if (i == 1 && j == 1) continue;
                Absorber.Type a = in[i][j];
                if ((block != Blocks.AIR || a != null) && (!(block instanceof Absorber) || ((Absorber) block).getType() != a)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public ItemStack getCraftingResult(IInventory inv) {
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

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ColliderRecipeSerializer.INSTANCE;
    }

    @Override
    public IRecipeType<?> getType() {
        return ColliderRecipeType.INSTANCE;
    }

    public int[] inSerial() {
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
