package ru.glosav.dstool.entity.rows.dto;

import ru.glosav.dstool.entity.interfaces.IDTORow;
import ru.glosav.kiask.protobuf.generated.event.EventProto;

/**
 * Created by abalyshev on 24.04.17.
 */
public class EventDTO implements IDTORow {
    public int num;
    public long devOp;
    public int devId;
    public int opId;
    public int type;
    public long span;
    public long ts;
    public EventProto.EVENT payload;
    public EventDTO(int num, long devOp, int devId, int opId, int type, long span, long ts, EventProto.EVENT payload) {
        this.num = num;
        this.devOp = devOp;
        this.devId = devId;
        this.opId = opId;
        this.type = type;
        this.span = span;
        this.ts = ts;
        this.payload = payload;
    }
}
