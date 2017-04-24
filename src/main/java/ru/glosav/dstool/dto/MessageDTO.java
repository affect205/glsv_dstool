package ru.glosav.dstool.dto;

import java.nio.ByteBuffer;

/**
 * Created by abalyshev on 24.04.17.
 */
public class MessageDTO {
    public long devOp;
    public int devId;
    public int opId;
    public long span;
    public long ts;
    public ByteBuffer payload;
    public MessageDTO(long devOp, int devId, int opId, long span, long ts, ByteBuffer payload) {
        this.devOp = devOp;
        this.devId = devId;
        this.opId = opId;
        this.span = span;
        this.ts = ts;
        this.payload = payload;
    }
}
