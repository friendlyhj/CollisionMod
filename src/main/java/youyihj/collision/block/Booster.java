package youyihj.collision.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.ArrayUtils;
import youyihj.collision.Utils;
import youyihj.collision.block.absorber.Absorber;
import youyihj.collision.block.absorber.Neutron;
import youyihj.collision.block.absorber.Proton;
import youyihj.collision.item.ItemRegistryHandler;
import youyihj.collision.item.SingleNucleus;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class Booster extends CollisionBlock {

    public Booster(SingleNucleus singleNucleus) {
        super("booster_" + Utils.toLineString(singleNucleus.name) , Material.ROCK);
        this.singleNucleus = singleNucleus;
        this.setHardness(2.0f);
        this.setResistance(50.0f);
    }

    private SingleNucleus singleNucleus;
    private static final EnumFacing[] allXZFacing = {
            EnumFacing.EAST,
            EnumFacing.WEST,
            EnumFacing.NORTH,
            EnumFacing.SOUTH
    };
    private static Booster[] allBoosters;
    private static ItemBlock[] allBoostersItemBlock;

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (!worldIn.isRemote && work(worldIn, pos)) convert(worldIn, pos);
    }

    private boolean work(World world, BlockPos pos) {
        int neutronAmount = 0;
        int protonAmount = 0;
        for (EnumFacing facing : allXZFacing) {
            BlockPos posOffset = pos.offset(facing);
            Block block = world.getBlockState(posOffset).getBlock();
            if (block instanceof Proton) {
                protonAmount++;
            } else if (block instanceof Neutron) {
                neutronAmount++;
            }
        }
        return neutronAmount == 2 && protonAmount == 2;
    }

    private void convert(World world, BlockPos pos) {
        NonNullList<ItemStack> list = OreDictionary.getOres("ore" + this.singleNucleus.name);
        if (!list.isEmpty()) {
            ItemStack stack = list.get(0);
            Item item = stack.getItem();
            if (item instanceof ItemBlock) {
                ItemBlock itemBlock = (ItemBlock) item;
                IBlockState state = itemBlock.getBlock().getStateFromMeta(stack.getMetadata());
                world.setBlockState(pos, state);
                for (EnumFacing facing : allXZFacing) {
                    BlockPos posOffset = pos.offset(facing);
                    ((Absorber) world.getBlockState(posOffset).getBlock()).transform(world, pos);
                }
            }
        }
    }

    @Override
    public void register() {
        super.register();
        allBoosters = ArrayUtils.add(allBoosters, this);
        allBoostersItemBlock = ArrayUtils.add(allBoostersItemBlock, ItemRegistryHandler.itemBlockHashMap.get(this.getRegistryName().getResourcePath()));
    }

    // FIXME: 2020/8/4 cannot correctly override the method.
    @Override
    @Nonnull
    public String getLocalizedName() {
        return I18n.format("tile.collision.booster.name" +
        I18n.format("material.nucleus." + this.singleNucleus.name.toLowerCase()));
    }

    @Mod.EventBusSubscriber
    @SuppressWarnings("unused")
    public static final class BoosterTinter {
        @SubscribeEvent
        public static void blockColors(ColorHandlerEvent.Block event) {
            event.getBlockColors().registerBlockColorHandler((IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex) -> (
                Integer.parseInt(((Booster) state.getBlock()).singleNucleus.color, 16)
            ), allBoosters);
        }

        @SubscribeEvent
        public static void itemColors(ColorHandlerEvent.Item event) {
            event.getItemColors().registerItemColorHandler(((stack, tintIndex) -> (
                  Integer.parseInt(((Booster) ((ItemBlock) stack.getItem()).getBlock()).singleNucleus.color, 16)
            )), allBoostersItemBlock);
        }
    }
}
