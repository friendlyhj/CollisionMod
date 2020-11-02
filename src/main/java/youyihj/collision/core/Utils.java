package youyihj.collision.core;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import youyihj.collision.block.absorber.Absorber;
import youyihj.collision.block.absorber.EnumAbsorber;
import youyihj.collision.block.absorber.Neutron;
import youyihj.collision.block.absorber.Proton;
import youyihj.collision.tile.TileNeutronStorage;
import youyihj.collision.tile.TileProtonStorage;

public final class Utils {

    public Utils() {
        throw new UnsupportedOperationException("no instance");
    }

    public static String toCamelString(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        String[] str = string.split("_");
        for (String string2 : str) {
            if (stringBuilder.length() == 0) {
                stringBuilder.append(string2);
            } else {
                stringBuilder.append(string2.substring(0, 1).toLowerCase());
                stringBuilder.append(string2.substring(1));
            }
        }
        return stringBuilder.toString();
    }


    public static String toLineString(String string) {
        StringBuilder stringBuilder = new StringBuilder(string);
        int temp = 0;
        for (int i = 0; i < string.length(); i++){
            if (Character.isUpperCase(string.charAt(i))){
                stringBuilder.insert(i + temp,"_");
                temp += 1;
            }
        }
        return stringBuilder.toString().toLowerCase();
    }

    public static boolean isDevEnvironment() {
        return (Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");
    }

    public static TileNeutronStorage getNeutronStorage(World world, BlockPos posIn, boolean allowAir, int range) {
        for (BlockPos pos : BlockPos.getAllInBox(posIn.add(-range, 0, -range), posIn.add(range, 0, range))) {
            TileEntity tileEntity = world.getTileEntity(pos);
            if (tileEntity != null && tileEntity instanceof TileNeutronStorage) {
                TileNeutronStorage neutronStorage = ((TileNeutronStorage) tileEntity);
                ItemStack item = neutronStorage.item.getStackInSlot(0);
                if (Neutron.INSTANCE.match(item, allowAir) || Neutron.Refined.INSTANCE.match(item, allowAir))
                    return neutronStorage;
            }
        }
        return null;
    }

    public static TileProtonStorage getProtonStorage(World world, BlockPos posIn, boolean allowAir, int range) {
        for (BlockPos pos : BlockPos.getAllInBox(posIn.add(-range, 0, -range), posIn.add(range, 0, range))) {
            TileEntity tileEntity = world.getTileEntity(pos);
            if (tileEntity != null && tileEntity instanceof TileProtonStorage) {
                TileProtonStorage protonStorage = ((TileProtonStorage) tileEntity);
                ItemStack item = protonStorage.item.getStackInSlot(0);
                if (Proton.INSTANCE.match(item, allowAir) || Proton.Refined.INSTANCE.match(item, allowAir))
                    return protonStorage;
            }
        }
        return null;
    }
}
