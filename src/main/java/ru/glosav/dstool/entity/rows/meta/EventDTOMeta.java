package ru.glosav.dstool.entity.rows.meta;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.control.TableColumn;
import ru.glosav.dstool.entity.rows.dto.EventDTO;

import java.util.Objects;

import static ru.glosav.dstool.gui.utils.DateUtils.convertToDateTime;

/**
 * Created by abalyshev on 26.04.17.
 */
public class EventDTOMeta extends DTOMeta<EventDTO> {
    public EventDTOMeta() {
        super();

        TableColumn<EventDTO, Integer> numCol = new TableColumn<>("#");
        numCol.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue().num));
        columns.add(numCol);

        TableColumn<EventDTO, Integer> devIdCol = new TableColumn<>("device_id");
        devIdCol.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue().devId));
        columns.add(devIdCol);

        TableColumn<EventDTO, Integer> opIdCol = new TableColumn<>("operator_id");
        opIdCol.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue().devId));
        columns.add(opIdCol);

        TableColumn<EventDTO, Long> devOpCol = new TableColumn<>("dev_op");
        devOpCol.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue().devOp));
        columns.add(devOpCol);

        TableColumn<EventDTO, Integer> typeCol = new TableColumn<>("type");
        typeCol.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue().type));
        columns.add(typeCol);

        TableColumn<EventDTO, String> spanCol = new TableColumn<>("span");
        spanCol.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(convertToDateTime(p.getValue().span)));
        columns.add(spanCol);

        TableColumn<EventDTO, String> tsCol = new TableColumn<>("ts");
        tsCol.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(convertToDateTime(p.getValue().ts)));
        columns.add(tsCol);

        TableColumn<EventDTO, String> payloadCol = new TableColumn<>("payload");
        payloadCol.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(Objects.toString(p.getValue().payload.getHeader())));
        columns.add(payloadCol);
    }
}
