package ru.glosav.dstool.entity.rows.meta;

import javafx.scene.control.TableColumn;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by abalyshev on 26.04.17.
 */
public abstract class DTOMeta<K> {
    protected List<TableColumn<K, ?>> columns;
    public DTOMeta() {
        columns = new LinkedList<>();
    }
    public List<TableColumn<K, ?>> columns() { return columns; }
}
