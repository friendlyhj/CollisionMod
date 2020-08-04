package youyihj.collision;

public class Utils {

    public Utils() {
        throw new UnsupportedOperationException("no instance");
    }

    public static String toLineString(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        String[] str = string.split("_");
        for (String string2 : str) {
            if (stringBuilder.length() == 0) {
                stringBuilder.append(string2);
            } else {
                stringBuilder.append(string2.substring(0, 1));
                stringBuilder.append(string2.substring(1));
            }
        }
        return stringBuilder.toString().toLowerCase();
    }


    public static String toCamelString(String string) {
        StringBuilder sb = new StringBuilder(string);
        int temp = 0;
        for (int i = 0; i < string.length(); i++){
            if (Character.isUpperCase(string.charAt(i))){
                sb.insert(i + temp,"_");
                temp += 1;
            }
        }
        return sb.toString().toLowerCase();
    }
}
