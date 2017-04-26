package ru.glosav.dstool.event;

import org.springframework.context.ApplicationEvent;
import ru.glosav.dstool.entity.rows.dto.DTOResult;

/**
 * Created by abalyshev on 26.04.17.
 */
public class CqlResultEvent extends ApplicationEvent {
    private DTOResult result;
    public CqlResultEvent(Object source, DTOResult result) {
        super(source);
        this.result = result;
    }
    public DTOResult getResult() { return result; }
}
