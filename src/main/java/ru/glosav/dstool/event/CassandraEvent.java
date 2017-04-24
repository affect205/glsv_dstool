package ru.glosav.dstool.event;

import org.springframework.context.ApplicationEvent;

/**
 * Created by abalyshev on 24.04.17.
 */
public class CassandraEvent extends ApplicationEvent {
    private String message;
    public CassandraEvent(Object source, String message) {
        super(source);
        this.message = message;
    }
    public String getMessage() { return message; }
}
