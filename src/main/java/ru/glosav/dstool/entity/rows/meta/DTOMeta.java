package ru.glosav.dstool.entity.rows.meta;

import javafx.scene.control.TableColumn;
import javafx.util.Pair;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by abalyshev on 26.04.17.
 */
public abstract class DTOMeta<K> {
    protected List<Pair<String, String>> names;
    protected List<Pair<String, Class<?>>> types;
    protected List<TableColumn<K, ?>> columns;
    public DTOMeta() {
        names = new LinkedList<>();
        types = new LinkedList<>();
        columns = new LinkedList<>();
    }
    public List<Pair<String, String>> names() { return names; }
    public List<Pair<String, Class<?>>> types() { return types; }
    public List<TableColumn<K, ?>> columns() { return columns; }
}
