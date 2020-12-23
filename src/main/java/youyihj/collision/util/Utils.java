package youyihj.collision.util;

import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StringUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

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

    public static boolean spawnEntityItem(ServerWorld world, BlockPos pos, ItemStack stack) {
        return world.summonEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), stack));
    }
}
