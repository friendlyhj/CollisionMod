package youyihj.collision.recipe;

import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
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
import java.util.Objects;
import java.util.Optional;

/**
 * @author youyihj
 */
public class ColliderRecipe implements IRecipe<IInventory> {
    public static final ResourceLocation ID = Collision.rl("collider");

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
                BlockState blockState = worldIn.getBlockState(posOffset);
                if (i == 1 && j == 1) continue;
                Optional<Absorber> absorber = getInAbsorber(i, j);
                if (absorber.isPresent()) {
                    if (absorber.get() != blockState.getBlock())
                        return false;
                } else {
                    if (blockState.getMaterial() != Material.AIR)
                        return false;
                }
            }
        }
        return true;
    }

    public Optional<Absorber> getInAbsorber(int x, int y) {
        return Optional.ofNullable(this.getIn()[x][y]).map(type -> type.getAbsorberByRecipe(this));
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

    int inSerial() {
        int temp = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                temp <<= 2;
                if (in[i][j] == null) {
                    continue;
                }
                switch (in[i][j]) {
                    case PROTON:
                        temp += 2;
                        break;
                    case NEUTRON:
                        temp += 3;
                        break;
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
                inSerial() == that.inSerial() &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(out, level, name, inSerial());
    }
}
