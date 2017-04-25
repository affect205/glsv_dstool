package ru.glosav.dstool.event;

import org.springframework.context.ApplicationEvent;

/**
 * Created by abalyshev on 25.04.17.
 */
public class ExecEvent extends ApplicationEvent {
    public ExecEvent(Object source) {
        super(source);
    }
}
