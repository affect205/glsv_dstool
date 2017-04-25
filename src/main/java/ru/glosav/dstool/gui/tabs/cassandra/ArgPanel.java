package ru.glosav.dstool.gui.tabs.cassandra;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import ru.glosav.dstool.enums.ArgName;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by abalyshev on 25.04.17.
 */
public class ArgPanel extends GridPane {
    private Map<ArgName, Control> controls;

    public ArgPanel(List<ArgName> args) {
        super();
        setHgap(14);
        setVgap(14);
        this.controls = new HashMap<>();
        int row = 1;
        for (ArgName arg : args) {
            Control c = null;
            switch (arg) {
                case DEVICEID:
                case OPERATORID:
                    c = new TextField();
                    break;
                case FROM:
                case TO:
                    c = new DatePicker();
                    break;
                case DEVOPS:
                case EDGES:
                case EVTTYPES:
                    c = new TextArea();
                    break;
            }
            if (c != null) {
                add(new Label(arg.getName()), 1, row);
                add(c, 2, row++);
                controls.put(arg, c);
            }
        }
    }

    public Map<ArgName, Control> getControls() {
        return controls;
    }
}
