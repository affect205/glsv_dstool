package ru.glosav.dstool.gui.misc.converter;

import javafx.util.StringConverter;
import ru.glosav.dstool.entity.CqlApiMethod;

import java.util.Objects;

/**
 * Created by abalyshev on 25.04.17.
 */
public class StringToCqlApiMethod extends StringConverter<CqlApiMethod> {
    @Override public String toString(CqlApiMethod c) {
        return Objects.toString(c);
    }

    @Override public CqlApiMethod fromString(String id) {
        return null;
    }
}
