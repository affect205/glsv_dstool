package ru.glosav.dstool.event;

import org.springframework.context.ApplicationEvent;

/**
 * Created by abalyshev on 24.04.17.
 */
public class CqlEvent extends ApplicationEvent {
    private String message;
    public CqlEvent(Object source, String message) {
        super(source);
        this.message = message;
    }
    public String getMessage() { return message; }
}
