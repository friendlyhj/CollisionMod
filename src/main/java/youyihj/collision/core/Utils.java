package youyihj.collision.core;

import net.minecraft.launchwrapper.Launch;

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
}
