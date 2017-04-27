package ru.glosav.dstool.entity.rows.meta;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.control.TableColumn;
import ru.glosav.dstool.entity.rows.dto.MessageDTO;

import java.util.Objects;

import static ru.glosav.dstool.gui.utils.DateUtils.convertToDateTime;

/**
 * Created by abalyshev on 26.04.17.
 */
public class MessageDTOMeta extends DTOMeta<MessageDTO> {
    public MessageDTOMeta() {
        super();

        TableColumn<MessageDTO, Integer> numCol = new TableColumn<>("#");
        numCol.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue().num));
        columns.add(numCol);

        TableColumn<MessageDTO, Integer> devIdCol = new TableColumn<>("device_id");
        devIdCol.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue().devId));
        columns.add(devIdCol);

        TableColumn<MessageDTO, Integer> opIdCol = new TableColumn<>("operator_id");
        opIdCol.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue().opId));
        columns.add(opIdCol);

        TableColumn<MessageDTO, Long> devOpCol = new TableColumn<>("dev_op");
        devOpCol.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue().devOp));
        columns.add(devOpCol);

        TableColumn<MessageDTO, String> spanCol = new TableColumn<>("span");
        spanCol.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(convertToDateTime(p.getValue().span)));
        columns.add(spanCol);

        TableColumn<MessageDTO, String> tsCol = new TableColumn<>("ts");
        tsCol.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(convertToDateTime(p.getValue().ts)));
        columns.add(tsCol);

        TableColumn<MessageDTO, String> payloadCol = new TableColumn<>("payload");
        payloadCol.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(Objects.toString(p.getValue().payload.getHeader())));
        columns.add(payloadCol);
    }
}
