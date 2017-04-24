package ru.glosav.dstool.gui.misc;

import javafx.application.Platform;
import javafx.scene.control.TextArea;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.glosav.dstool.gui.utils.ConsoleUtils;

import javax.annotation.PostConstruct;

import java.util.List;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

/**
 * Created by abalyshev on 20.07.16.
 */
@Component
@Scope(SCOPE_PROTOTYPE)
public class ConsoleTextArea extends TextArea /*implements ConsoleListener */{

    public ConsoleTextArea() {
        super();
        //ConsoleUtils.addConsoleListener(this);
    }

    @PostConstruct
    public void onInit() {

    }

    public void println(String line) {
        Platform.runLater(() -> {
            StringBuilder sb = new StringBuilder(getText());
            sb.append(line);
            setText(sb.toString());
            setScrollTop(Double.MAX_VALUE);
        });
    }

    public void println(List<String> lines) {
        Platform.runLater(() -> {
            lines.forEach(line -> {
                StringBuilder sb = new StringBuilder(getText());
                sb.append(line);
                sb.append("\n");
                setText(sb.toString());
                setScrollTop(Double.MAX_VALUE);
            });
        });
    }
}
