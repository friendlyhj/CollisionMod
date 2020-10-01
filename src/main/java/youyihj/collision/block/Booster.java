package youyihj.collision.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import youyihj.collision.block.absorber.Absorber;
import youyihj.collision.block.absorber.Neutron;
import youyihj.collision.block.absorber.Proton;
import youyihj.collision.item.Nucleus;
import youyihj.collision.item.SingleNucleus;
import youyihj.collision.tile.TileBooster;

import javax.annotation.Nullable;
import java.util.WeakHashMap;


public class Booster extends CollisionBlock {

    public static final Booster INSTANCE = new Booster();

    @SideOnly(Side.CLIENT)
    public static boolean doRerender;

    @SideOnly(Side.CLIENT)
    public static WeakHashMap<BlockPos, Boolean> rerenderMap = new WeakHashMap<>();

    private Booster() {
        super("booster", Material.ROCK);
        this.setHardness(2.0f);
        this.setResistance(50.0f);
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileBooster();
    }

    @Override
    @SuppressWarnings("deprecation")
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (!world.isRemote && work(world, pos)) convert(world, pos);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = playerIn.getHeldItem(hand);
        if (stack.getItem() instanceof Nucleus) {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if (tileEntity instanceof TileBooster) {
                TileBooster tileBooster = (TileBooster) tileEntity;
                if (!tileBooster.isFull()) {
                    if (worldIn.isRemote) {
                        setRerender(worldIn, pos);
                        return true;
                    }
                    stack.shrink(1);
                    tileBooster.setType(stack.getMetadata());
                    tileBooster.setFull(true);
                    worldIn.notifyBlockUpdate(pos, state, state, Constants.BlockFlags.DEFAULT);
                    this.neighborChanged(state, worldIn, pos, this, pos);
                    return true;
                }
            }
        }
        return false;
    }

    @SideOnly(Side.CLIENT)
    private void setRerender(IBlockAccess world, BlockPos pos) {
        doRerender = true;
        if (world.getTileEntity(pos) instanceof TileBooster) {
            rerenderMap.put(pos, true);
        }
    }

    private boolean work(IBlockAccess world, BlockPos pos) {
        int neutronAmount = 0;
        int protonAmount = 0;
        for (EnumFacing facing : EnumFacing.HORIZONTALS) {
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

    private static SingleNucleus getNucleusFromTileEntity(IBlockAccess world, BlockPos pos) {
        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity instanceof TileBooster) {
            TileBooster tileBooster = (TileBooster) tileEntity;
            return tileBooster.isFull() ? Nucleus.singleHashMap.get(tileBooster.getType()) : null;
        }
        return null;
    }

    private void convert(World world, BlockPos pos) {
        SingleNucleus nucleus = getNucleusFromTileEntity(world, pos);
        if (nucleus == null) return;
        NonNullList<ItemStack> list = OreDictionary.getOres("ore" + nucleus.name);
        ItemStack stack = list.stream().filter(itemStack -> itemStack.getItem() instanceof ItemBlock).findFirst().orElse(null);
        if (stack != null) {
            ItemBlock itemBlock = (ItemBlock) stack.getItem();
            IBlockState state = itemBlock.getBlock().getStateFromMeta(stack.getMetadata());
            world.setBlockState(pos, state);
            for (EnumFacing facing : EnumFacing.HORIZONTALS) {
                BlockPos posOffset = pos.offset(facing);
                ((Absorber) world.getBlockState(posOffset).getBlock()).transform(world, posOffset);
            }
        }
    }

    @Mod.EventBusSubscriber
    @SuppressWarnings("unused")
    public static final class BoosterTinter {
        @SubscribeEvent
        public static void blockColors(ColorHandlerEvent.Block event) {
            event.getBlockColors().registerBlockColorHandler((IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex) -> {
                if (worldIn == null || pos == null) return 0xffffff;
                SingleNucleus nucleus = getNucleusFromTileEntity(worldIn, pos);
                return nucleus == null ? 0xffffff : nucleus.getColorToInt();
            }, INSTANCE);
        }
    }
}
