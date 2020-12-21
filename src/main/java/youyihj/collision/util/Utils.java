package youyihj.collision.util;

import net.minecraft.util.StringUtils;

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
}
