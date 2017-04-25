package ru.glosav.dstool.gui.tabs.cassandra;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import ru.glosav.dstool.enums.ArgName;

import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by abalyshev on 25.04.17.
 */
public class ArgPanel extends GridPane {
    private List<Pair<ArgName, Control>> controls;

    public ArgPanel(List<ArgName> args) {
        super();
        setHgap(14);
        setVgap(14);
        this.controls = new ArrayList<>();
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
                    TextArea ta = new TextArea();
                    ta.setPrefWidth(140);
                    c = ta;
                    break;
            }
            if (c != null) {
                add(new Label(arg.getName()), 1, row);
                add(c, 2, row++);
                controls.add(new Pair<>(arg, c));
            }
        }
    }

    public List<Pair<ArgName, Control>> getControls() {
        return controls;
    }

    public Object[] getArgs() {
        List<Object> result = new ArrayList<>();
        controls.forEach(p -> {
            String[] split = {};
            Object value = null;
            String text = "";
            switch (p.getKey()) {
                case DEVICEID:
                case OPERATORID:
                    text = ((TextField)p.getValue()).getText();
                    value = Integer.parseInt(text);
                    break;
                case FROM:
                case TO:
                    value = ((DatePicker)p.getValue()).getValue().atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli();
                    break;
                case DEVOPS:
                case EDGES:
                    split = ((TextArea)p.getValue()).getText().split(",");
                    value = Stream.of(split).map(Long::parseLong).collect(Collectors.toList());
                    break;
                case EVTTYPES:
                    split = ((TextArea)p.getValue()).getText().split(",");
                    value = Stream.of(split).map(Integer::parseInt).collect(Collectors.toList());
                    break;
            }
            if (value != null) {
                result.add(value);
            }
        });
        return result.toArray();
    }
}
