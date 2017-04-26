package ru.glosav.dstool.entity.rows.meta;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.control.TableColumn;
import javafx.util.Pair;
import ru.glosav.dstool.entity.rows.dto.EventDTO;

import java.nio.ByteBuffer;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by abalyshev on 26.04.17.
 */
public class EventDTOMeta extends DTOMeta<EventDTO> {
    public EventDTOMeta() {
        super();
        names.add(new Pair<>("devOp", "dev_op"));
        names.add(new Pair<>("devId", "device_id"));
        names.add(new Pair<>("opId", "operator_id"));
        names.add(new Pair<>("type", "type"));
        names.add(new Pair<>("span", "span"));
        names.add(new Pair<>("ts", "ts"));
        names.add(new Pair<>("payload", "payload"));

        types.add(new Pair<>("devOp", Long.class));
        types.add(new Pair<>("devId", Integer.class));
        types.add(new Pair<>("opId", Integer.class));
        types.add(new Pair<>("type", Integer.class));
        types.add(new Pair<>("span", Long.class));
        types.add(new Pair<>("ts", Long.class));
        types.add(new Pair<>("payload", ByteBuffer.class));

        DateTimeFormatter df = DateTimeFormatter
                .ofPattern("dd-MM-yyyy HH:mm:ss")
                .withZone(ZoneId.systemDefault());

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
        spanCol.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(df.format(Instant.ofEpochMilli(p.getValue().span))));
        columns.add(spanCol);

        TableColumn<EventDTO, String> tsCol = new TableColumn<>("ts");
        tsCol.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(df.format(Instant.ofEpochMilli(p.getValue().ts))));
        columns.add(tsCol);

        TableColumn<EventDTO, String> payloadCol = new TableColumn<>("payload");
        payloadCol.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue().payload.toString()));
        columns.add(payloadCol);
    }
}
