package ru.glosav.dstool.gui.utils;

import ru.glosav.dstool.gui.misc.ConsoleListener;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by abalyshev on 20.07.16.
 */
public class ConsoleUtils {

    private static List<ConsoleListener> listeners;

    static {
        listeners = new LinkedList<>();
    }

    public static void println(String line) {
        System.out.println(line);
        listeners.forEach(l -> l.println(line));
    }

    public static void printf(String line, Object... args) {
        System.out.printf(line, args);
        listeners.forEach(l -> l.println(String.format(line, args)));
    }

    public static void addConsoleListener(ConsoleListener listener) {
        if (!listeners.contains(listener)) listeners.add(listener);
    }

    public static void removeConsoleListener(ConsoleListener listener) {
        listeners.remove(listener);
    }
}
