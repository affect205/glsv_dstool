package ru.glosav.dstool.gui.misc.converter;

import javafx.util.StringConverter;
import ru.glosav.dstool.entity.CqlConnection;

/**
 * Created by abalyshev on 14.04.17.
 */
public class StringToCqlConnection extends StringConverter<CqlConnection> {
    @Override public String toString(CqlConnection conn) {
        if (conn == null) {
            return null;
        } else {
            return String.format("%s:%s", conn.getUrl(), conn.getKeySpace());
        }
    }

    @Override public CqlConnection fromString(String id) {
        return null;
    }
}
