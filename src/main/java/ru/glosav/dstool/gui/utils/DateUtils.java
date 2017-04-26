package ru.glosav.dstool.gui.utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Created by abalyshev on 26.04.17.
 */
public class DateUtils {
    public static String convertToDateTime(long timestamp) {
        DateTimeFormatter df = DateTimeFormatter
                .ofPattern("dd-MM-yyyy HH:mm:ss")
                .withZone(ZoneId.systemDefault());
        return df.format(Instant.ofEpochMilli(timestamp));
    }
}
