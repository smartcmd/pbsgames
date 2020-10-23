package top.mcpbs.games.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    // yyyy/MM/dd
    public static Date date = new Date(System.currentTimeMillis());
    public static String getDate(String s) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(s);
        return dateFormat.format(date);
    }
}
