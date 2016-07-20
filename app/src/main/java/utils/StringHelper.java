package utils;

/**
 * Created by Gi Wah Davalos on 26/06/2016.
 */
public class StringHelper {

    public static String shortenString(int chars, String str) {
        if (str.length() <= chars) {
            return str;
        }
        return str.substring(0, chars - 1) + "...";
    }

    public static String toImgFormat(String str) {
        if (str.length() <= 0) {
            return str;
        }

        return format(str) + ".jpg";
    }

    public static String toAudioFormat(String str){
        if (str.length() <= 0) {
            return str;
        }
        return format(str) + ".3gp";
    }

    private static String format(String str) {
        return str
                .trim()
                .replaceAll(" ", "_")
                .toLowerCase();
    }
}
