package ru.glosav.dstool.gui.utils;

import javafx.beans.value.ChangeListener;
import javafx.scene.control.TextField;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by abalyshev on 14.04.17.
 */
public class GuiUtils {
    public static ChangeListener<String> getNumberListener(final TextField textField) {
        return (observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        };
    }

    public static <K> String joinList(List<K> list, String delimiter) {
        if (list == null || list.isEmpty()) return "";
        if (list.size() == 1) return list.get(0).toString();
        return String.join(delimiter, list.stream().map(Object::toString).collect(Collectors.toList()));
    }

    public static int parseInt(String value, int def) {
        try {
            return Integer.parseInt(value);
        } catch(Exception e) {
            return def;
        }
    }
}
