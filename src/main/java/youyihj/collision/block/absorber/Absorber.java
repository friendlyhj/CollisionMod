package youyihj.collision.block.absorber;

import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;
import youyihj.collision.block.BlockBase;
import youyihj.collision.block.BlockRegistry;
import youyihj.collision.config.Configuration;
import youyihj.collision.itemgroup.CollisionGroup;
import youyihj.collision.recipe.ColliderRecipe;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.Random;
import java.util.function.Supplier;

/**
 * @author youyihj
 */
public abstract class Absorber extends BlockBase {
    private final boolean willTransform;
    private final Type type;
    private final boolean isRefined;

    public Absorber(String name, boolean willTransform, boolean isRefined, Type type) {
        super(name, Properties.create(Material.IRON)
                .harvestTool(ToolType.PICKAXE)
                .hardnessAndResistance(3.0f, 50.0f)
                .tickRandomly()
        );
        this.type = type;
        this.willTransform = willTransform;
        this.isRefined = isRefined;
    }

    public Absorber getTransformAbsorber() {
        return isRefined ? this.getType().transform().getRefinedAbsorber() : this.getType().transform().getAbsorber();
    }

    public void transform(World world, BlockPos pos) {
        world.setBlockState(pos, getTransformAbsorber().getDefaultState());
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        if (this.willTransform && random.nextInt(Configuration.absorberSpeed.get()) == 0 && work(worldIn)) {
            worldIn.setBlockState(pos, getTransformAbsorber().getDefaultState());
        }
    }

    public boolean match(ItemStack itemStack, boolean allowAir) {
        if (allowAir && itemStack.isEmpty()) {
            return true;
        }
        if (itemStack.getItem() instanceof BlockItem) {
            BlockItem itemBlock = ((BlockItem) itemStack.getItem());
            return Objects.deepEquals(itemBlock.getBlock(), this);
        }
        return false;
    }

    public ItemStack getItemStack() {
        return new ItemStack(this.asItem());
    }

    @Override
    public Supplier<BlockItem> getBlockItemSupplier() {
        return () -> new BlockItem(this, new Item.Properties().group(CollisionGroup.INSTANCE)) {
            @Override
            public boolean hasContainerItem(ItemStack stack) {
                return !Absorber.this.getType().isEmpty();
            }

            @Override
            public ItemStack getContainerItem(ItemStack itemStack) {
                if (!this.hasContainerItem(itemStack)) {
                    return ItemStack.EMPTY;
                }
                return new ItemStack(BlockRegistry.getBlock(Absorber.this.getTransformAbsorber().getName()));
            }
        };
    }

    public Type getType() {
        return type;
    }

    public boolean isRefined() {
        return isRefined;
    }

    private boolean work(World world) {
        return !Configuration.onlyWorkInDaytime.get() || world.isDaytime();
    }

    public enum Type {
        PROTON,
        PROTON_EMPTY,
        NEUTRON,
        NEUTRON_EMPTY;

        @Nonnull
        public Type transform() {
            switch (this) {
                case PROTON:
                    return PROTON_EMPTY;
                case NEUTRON:
                    return NEUTRON_EMPTY;
                case PROTON_EMPTY:
                    return PROTON;
                case NEUTRON_EMPTY:
                    return NEUTRON;
                default:
                    return null;
            }
        }

        @Nonnull
        public Absorber getAbsorber() {
            switch (this) {
                case PROTON:
                    return Proton.INSTANCE;
                case NEUTRON:
                    return Neutron.INSTANCE;
                case PROTON_EMPTY:
                    return ProtonEmpty.INSTANCE;
                case NEUTRON_EMPTY:
                    return NeutronEmpty.INSTANCE;
                default:
                    return null;
            }
        }

        @Nonnull
        public Absorber getRefinedAbsorber() {
            switch (this) {
                case PROTON:
                    return Proton.Refined.INSTANCE;
                case NEUTRON:
                    return Neutron.Refined.INSTANCE;
                case PROTON_EMPTY:
                    return ProtonEmpty.Refined.INSTANCE;
                case NEUTRON_EMPTY:
                    return NeutronEmpty.Refined.INSTANCE;
                default:
                    return null;
            }
        }

        public boolean isEmpty() {
            return this == PROTON_EMPTY || this == NEUTRON_EMPTY;
        }

        public Absorber getAbsorberByRecipe(ColliderRecipe recipe) {
            return recipe.isRefined() ? getRefinedAbsorber() : getAbsorber();
        }

        @Nonnull
        public Absorber.Type toFullType() {
            switch (this) {
                case PROTON_EMPTY:
                    return PROTON;
                case NEUTRON_EMPTY:
                    return NEUTRON;
                default:
                    return this;
            }
        }
    }
}
