package ru.glosav.dstool.gui.misc.converter;

import javafx.util.StringConverter;
import ru.glosav.cassandra.dao.impl.CassandraGate;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Stream;

import static java.util.Arrays.copyOfRange;

/**
 * Created by abalyshev on 24.04.17.
 */
public class StringToMethod extends StringConverter<Method> {
    @Override
    public String toString(Method m) {
        if (m == null) {
            return null;
        } else {
            Class<?>[] types = m.getParameterTypes().length > 0 ? copyOfRange(m.getParameterTypes(), 1, m.getParameterTypes().length) : m.getParameterTypes();
            Object[] args = Stream.of(types).map(Class::getSimpleName).toArray();
            return String.format("%s(%s): %s", m.getName(), Arrays.toString(args), m.getReturnType().getSimpleName());
        }
    }

    @Override
    public Method fromString(String id) {
        try {
            return CassandraGate.class.getMethod(id);
        } catch (Exception e) {
            return null;
        }
    }
}
