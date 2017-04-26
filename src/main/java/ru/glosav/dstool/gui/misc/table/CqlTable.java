package ru.glosav.dstool.gui.misc.table;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import ru.glosav.dstool.entity.interfaces.IDTORow;
import ru.glosav.dstool.entity.rows.dto.DTOResult;
import ru.glosav.dstool.event.CqlResultEvent;
import ru.glosav.dstool.gui.utils.TableUtils;

import java.util.List;


/**
 * Created by abalyshev on 24.04.17.
 */
@Component
public class CqlTable extends TableView<IDTORow> implements ApplicationListener<CqlResultEvent> {
    @Override
    public void onApplicationEvent(CqlResultEvent event) {
        DTOResult result = event.getResult();
        List<TableColumn<IDTORow, ?>> columns = TableUtils.makeColumns(result.getType());
        Platform.runLater(() -> {
            getColumns().clear();
            getColumns().setAll(columns);
            setItems(FXCollections.observableArrayList(result.getResult()));
            refresh();
        });
    }
}
