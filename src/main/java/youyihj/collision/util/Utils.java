package youyihj.collision.util;

import net.minecraft.item.ItemStack;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import youyihj.collision.block.absorber.Neutron;
import youyihj.collision.block.absorber.NeutronEmpty;
import youyihj.collision.block.absorber.Proton;
import youyihj.collision.block.absorber.ProtonEmpty;
import youyihj.collision.tile.TileNeutronStorage;
import youyihj.collision.tile.TileProtonStorage;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

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
            if (tileEntity instanceof TileNeutronStorage) {
                TileNeutronStorage neutronStorage = ((TileNeutronStorage) tileEntity);
                ItemStack item = neutronStorage.item.getStackInSlot(0);
                if (Neutron.INSTANCE.match(item, allowAir) ||
                        Neutron.Refined.INSTANCE.match(item, allowAir) ||
                        NeutronEmpty.INSTANCE.match(item, allowAir) ||
                        NeutronEmpty.Refined.INSTANCE.match(item, allowAir)) {
                    return neutronStorage;
                }
            }
        }
        return null;
    }

    public static TileProtonStorage getProtonStorage(World world, BlockPos posIn, boolean allowAir, int range) {
        for (BlockPos pos : BlockPos.getAllInBox(posIn.add(-range, 0, -range), posIn.add(range, 0, range))) {
            TileEntity tileEntity = world.getTileEntity(pos);
            if (tileEntity instanceof TileProtonStorage) {
                TileProtonStorage protonStorage = ((TileProtonStorage) tileEntity);
                ItemStack item = protonStorage.item.getStackInSlot(0);
                if (Proton.INSTANCE.match(item, allowAir) ||
                        Proton.Refined.INSTANCE.match(item, allowAir) ||
                        ProtonEmpty.INSTANCE.match(item, allowAir) ||
                        ProtonEmpty.Refined.INSTANCE.match(item, allowAir)) {
                    return protonStorage;
                }
            }
        }
        return null;
    }

    public static String i18nFormat(String key, Object... objects) {
        return new TextComponentTranslation(key, objects).getUnformattedText();
    }

    public static boolean hasPassableBlock(World world, BlockPos pos) {
        return world.getBlockState(pos).getBlock().isPassable(world, pos);
    }

    public static <T> void enumerateForEach(Iterator<T> iterator, BiConsumer<Integer, ? super T> action) {
        Objects.requireNonNull(action);
        int i = 0;
        while (iterator.hasNext()) {
            action.accept(i++, iterator.next());
        }
    }

    public static <T> void enumerateForEach(Iterable<T> iterable, BiConsumer<Integer, ? super T> action) {
        enumerateForEach(iterable.iterator(), action);
    }

    public static <T, R> R[][] map2DArray(T[][] array, Function<T, R> mapper, Class<R> resultClass) {
        return map2DArray(array, mapper, resultClass, null);
    }

    @SuppressWarnings("unchecked")
    public static <T, R> R[][] map2DArray(T[][] array, Function<T, R> mapper, Class<R> resultClass, R nullValue) {
        R[][] temp = (R[][]) Array.newInstance(resultClass, array.length, array[0].length);
        for (int i = 0; i < array.length; i++) {
            temp[i] = mapArray(array[i], mapper, resultClass, nullValue);
        }
        return temp;
    }

    public static <T, R> R[] mapArray(T[] array, Function<T, R> mapper, Class<R> resultClass) {
        return mapArray(array, mapper, resultClass, null);
    }

    @SuppressWarnings("unchecked")
    public static <T, R> R[] mapArray(T[] array, Function<T, R> mapper, Class<R> resultClass, R nullValue) {
        R[] temp = (R[]) Array.newInstance(resultClass, array.length);
        for (int i = 0; i < array.length; i++) {
            temp[i] = Lazy.of(array[i]).map(mapper).orElse(nullValue);
        }
        return temp;
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] createArray(Supplier<T> supplier, Class<T> clazz, int size) {
        T[] temp = (T[]) Array.newInstance(clazz, size);
        for (int i = 0; i < size; i++) {
            temp[i] = supplier.get();
        }
        return temp;
    }

    @SuppressWarnings("unchecked")
    public static <T> T[][] create2DArray(Supplier<T> supplier, Class<T> clazz, int sizeX, int sizeY) {
        T[][] temp = (T[][]) Array.newInstance(clazz, sizeX, sizeY);
        for (int i = 0; i < temp.length; i++) {
            temp[i] = createArray(supplier, clazz, sizeX);
        }
        return temp;
    }
}
