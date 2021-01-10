package youyihj.collision.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StringUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import youyihj.collision.block.absorber.Neutron;
import youyihj.collision.block.absorber.NeutronEmpty;
import youyihj.collision.block.absorber.Proton;
import youyihj.collision.block.absorber.ProtonEmpty;
import youyihj.collision.tile.TileNeutronStorage;
import youyihj.collision.tile.TileProtonStorage;

import javax.annotation.Nullable;
import java.lang.reflect.Array;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author youyihj
 */
public class Utils {
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
        if (StringUtils.isNullOrEmpty(string)) return "";
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        for (int i = 0; i < string.length(); i++) {
            if (!isFirst) {
                if (Character.isUpperCase(string.charAt(i))) {
                    sb.append("_");
                }
            }
            sb.append(string.charAt(i));
            isFirst = false;
        }
        return sb.toString().toLowerCase();
    }

    public static void spawnEntityItem(ServerWorld world, BlockPos pos, ItemStack stack) {
        world.summonEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), stack));
    }

    public static TileNeutronStorage getNeutronStorage(World world, BlockPos posIn, boolean allowAir, int range) {
        for (BlockPos pos : BlockPos.getAllInBoxMutable(posIn.add(-range, 0, -range), posIn.add(range, 0, range))) {
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
        for (BlockPos pos : BlockPos.getAllInBoxMutable(posIn.add(-range, 0, -range), posIn.add(range, 0, range))) {
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
            temp[i] = Optional.ofNullable(array[i]).map(mapper).orElse(nullValue);
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

    public static void damageItem(ItemStack stack, int amount, @Nullable LivingEntity entityIn, World world) {
        if (entityIn == null) {
            stack.attemptDamageItem(amount, world.rand, null);
        } else {
            stack.damageItem(amount, entityIn, entity -> {});
        }
    }
}
