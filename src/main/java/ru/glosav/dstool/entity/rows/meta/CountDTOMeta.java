package ru.glosav.dstool.entity.rows.meta;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.control.TableColumn;
import javafx.util.Pair;
import ru.glosav.dstool.entity.rows.dto.CountDTO;

/**
 * Created by abalyshev on 26.04.17.
 */
public class CountDTOMeta extends DTOMeta<CountDTO> {
    public CountDTOMeta() {
        super();
        names.add(new Pair<>("cnt", "count"));
        types.add(new Pair<>("cnt", Long.class));

        TableColumn<CountDTO, Long> devIdCol = new TableColumn<>("count");
        devIdCol.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue().cnt));
        columns.add(devIdCol);
    }
}
