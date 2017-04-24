package ru.glosav.dstool.dto;

import java.nio.ByteBuffer;

/**
 * Created by abalyshev on 24.04.17.
 */
public class EventDTO {
    public long devOp;
    public int devId;
    public int opId;
    public int type;
    public long span;
    public long ts;
    public ByteBuffer payload;
    public EventDTO(long devOp, int devId, int opId, int type, long span, long ts, ByteBuffer payload) {
        this.devOp = devOp;
        this.devId = devId;
        this.opId = opId;
        this.type = type;
        this.span = span;
        this.ts = ts;
        this.payload = payload;
    }
}
