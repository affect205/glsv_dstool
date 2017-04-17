package ru.glosav.dstool.gui.misc;

import javafx.application.Platform;
import javafx.scene.control.TextArea;
import ru.glosav.dstool.gui.utils.ConsoleUtils;

/**
 * Created by abalyshev on 20.07.16.
 */
public class ConsoleTextArea extends TextArea implements ConsoleListener {

    public ConsoleTextArea() {
        super();
        ConsoleUtils.addConsoleListener(this);
    }

    @Override
    public void println(String line) {
        Platform.runLater(() -> {
            StringBuilder sb = new StringBuilder(getText());
            sb.append(line);
            setText(sb.toString());
            setScrollTop(Double.MAX_VALUE);
        });
    }
}
