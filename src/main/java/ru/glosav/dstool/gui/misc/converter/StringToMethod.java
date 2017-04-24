package ru.glosav.dstool.gui.misc.converter;

import javafx.util.StringConverter;
import ru.glosav.cassandra.dao.ICassandraDaoLocal;

import java.lang.reflect.Method;

/**
 * Created by abalyshev on 24.04.17.
 */
public class StringToMethod extends StringConverter<Method> {
    @Override
    public String toString(Method m) {
        if (m == null) {
            return null;
        } else {
            return m.getName();
        }
    }

    @Override
    public Method fromString(String id) {
        try {
            return ICassandraDaoLocal.class.getMethod(id);
        } catch (Exception e) {
            return null;
        }
    }
}
