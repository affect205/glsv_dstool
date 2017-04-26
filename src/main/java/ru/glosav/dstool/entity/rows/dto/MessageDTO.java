package ru.glosav.dstool.entity.rows.dto;

import ru.glosav.dstool.entity.interfaces.IDTORow;
import ru.glosav.kiask.protobuf.generated.message.MessageProto;

/**
 * Created by abalyshev on 24.04.17.
 */
public class MessageDTO implements IDTORow {
    public long devOp;
    public int devId;
    public int opId;
    public long span;
    public long ts;
    public MessageProto.MESSAGE payload;
    public MessageDTO(long devOp, int devId, int opId, long span, long ts, MessageProto.MESSAGE payload) {
        this.devOp = devOp;
        this.devId = devId;
        this.opId = opId;
        this.span = span;
        this.ts = ts;
        this.payload = payload;
    }
}
