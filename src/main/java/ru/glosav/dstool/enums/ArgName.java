package ru.glosav.dstool.enums;

import java.util.Collection;

import static java.lang.String.format;

/**
 * Created by abalyshev on 25.04.17.
 */
public enum ArgName {
    DEVOPS("devOps", Collection.class),
    FROM("from", Long.class),
    TO("to", Long.class),
    EVTTYPES("evtTypes", Collection.class),
    DEVICEID("deviceId", Integer.class),
    OPERATORID("operatorId", Integer.class),
    EDGES("edges", Collection.class);
    private String name;
    private Class<?> clazz;
    ArgName(String name, Class<?> clazz) {
        this.name = name;
        this.clazz = clazz;
    }
    public String getName() { return name; }
    public Class<?> getClazz() { return clazz; }
    @Override public String toString() { return format("%s:%s", name, clazz.getSimpleName()); }
}
