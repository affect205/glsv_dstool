package ru.glosav.dstool.entity;

import ru.glosav.dstool.enums.ArgName;

import java.util.*;

/**
 * Created by abalyshev on 25.04.17.
 */
public class CqlApiMethod {
    private String name;
    private List<ArgName> args;
    private Class type;
    public CqlApiMethod(String name, Class type, ArgName... args) {
        this.name = name;
        this.type = type;
        this.args = new ArrayList<>();
        for (ArgName arg : args) {
            this.args.add(arg);
        }
    }
    public String getName() { return name; }
    public List<ArgName> getArgs() { return args; }
    public Class getType() { return type; }
    @Override
    public String toString() {
        return String.format("%s(%s): %s", name, Arrays.toString(args.toArray()), type.getName());
    }
}
