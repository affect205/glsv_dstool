package ru.glosav.dstool.gui;

import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by abalyshev on 20.07.16.
 */
public class FetchPanel extends GridPane {

    public static final String DEVICEID_SEPARATOR = "-|;";
    private MainPanel rootPanel;
    private TextField deviceIdsFld;
    private Button runBtn;
    private DatePicker dateFrom;
    private DatePicker dateTo;

    public FetchPanel(MainPanel rootPanel) {
        super();
        this.rootPanel = rootPanel;
        deviceIdsFld = new TextField();
        dateFrom = new DatePicker(Instant.now().atZone(ZoneId.systemDefault()).toLocalDate());
        dateTo = new DatePicker(Instant.now().atZone(ZoneId.systemDefault()).toLocalDate());
        runBtn = new Button("Запустить");
        runBtn.setOnAction(event -> {
            Instant from = dateFrom.getValue().atStartOfDay().toInstant(ZoneOffset.UTC);
            Instant to = dateTo.getValue().atStartOfDay().toInstant(ZoneOffset.UTC);
            List<Integer> deviceIds = new LinkedList<>();
            try {
                deviceIds = Arrays.asList(deviceIdsFld.getText()
                        .split(DEVICEID_SEPARATOR))
                        .stream()
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());
                if (deviceIdsFld.getText().contains("-")) {
                    deviceIds = IntStream.range(deviceIds.get(0), deviceIds.get(1)+1).boxed().collect(Collectors.toList());
                }
            } catch(Exception e) {
                //deviceIds = Fetch.devices;
            }
            //Fetch.runFetch(deviceIds, from, to);
        });

        setHgap(14);
        setVgap(14);
        addRow(1, new Label("ID девайсов"), deviceIdsFld);
        addRow(2, new Label("Время нач."), dateFrom);
        addRow(3, new Label("Время кон."), dateTo);
        addRow(4, new Label(""), runBtn);
    }
}
