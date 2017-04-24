package ru.glosav.dstool.event;

import org.springframework.context.ApplicationEvent;

/**
 * Created by abalyshev on 24.04.17.
 */
public class ConsoleEvent extends ApplicationEvent {
    private String message;
    public ConsoleEvent(Object source, String message) {
        super(source);
        this.message = message;
    }
    public String getMessage() { return message; }
}
