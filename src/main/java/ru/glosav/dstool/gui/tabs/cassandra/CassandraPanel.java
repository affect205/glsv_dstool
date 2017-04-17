package ru.glosav.dstool.gui.tabs.cassandra;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.layout.GridPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.glosav.dstool.conn.ClusterConnector;
import ru.glosav.dstool.entity.CqlConnection;
import ru.glosav.dstool.gui.misc.dialog.DialogFactory;
import ru.glosav.dstool.gui.utils.ConsoleUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import static ru.glosav.dstool.gui.converter.Converters.STRING_TO_CQL_CONNECTION;

/**
 * Created by abalyshev on 14.04.17.
 */
@Component
public class CassandraPanel extends GridPane {
    @Autowired
    private ClusterConnector connector;

    private Dialog<CqlConnection> cqlConnDlg;
    private ComboBox<CqlConnection> hostCmb;
    private Button connectBtn;
    private Button addBtn;

    public CassandraPanel() {
        super();
        setHgap(10);
        setVgap(10);
    }

    @PostConstruct
    public void onInit() {
        ObservableList<CqlConnection> hosts = FXCollections.observableList(new ArrayList<>(Arrays.asList(new CqlConnection("10.0.1.25", "ks"))));

        hostCmb = new ComboBox<>();
        hostCmb.setPromptText("host and keyspace...");
        hostCmb.setItems(hosts);
        hostCmb.setConverter(STRING_TO_CQL_CONNECTION);

        Platform.runLater(() -> {
            cqlConnDlg = DialogFactory.createCqlConnDialog();
        });

        addBtn = new Button("+");
        addBtn.setOnAction(event -> {
            Optional<CqlConnection> result = cqlConnDlg.showAndWait();
            if (result.isPresent()) {
                hosts.add(result.get());
            }
        });

        connectBtn = new Button("connect");
        connectBtn.setOnAction(event -> {
            Optional.ofNullable(hostCmb.getValue())
                    .ifPresent(cqlConn -> {
                        try {
                            connector.connect(cqlConn);
                            ConsoleUtils.printf("Connection to %s has successfully established", Objects.toString(cqlConn));
                        } catch (Exception e) {
                            ConsoleUtils.printf("Error! %s\n", e.getMessage());
                        }
                    });
        });

        add(addBtn, 1, 1);
        add(hostCmb, 2, 1);
        add(connectBtn, 3, 1);
    }
}