package ru.glosav.dstool.gui.tabs.cassandra;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by abalyshev on 18.04.17.
 */
@Component
public class CassandraQueryPanel extends Stage {
    private VBox panel;
    private TextArea queryTA;
    private Button runBtn;

    public CassandraQueryPanel() {
        setWidth(720);
        setHeight(512);
    }

    @PostConstruct
    public void onInit() {
        panel = new VBox();
        queryTA = new TextArea();
        runBtn = new Button(">");
        panel.getChildren().addAll(queryTA, runBtn);

        setScene(new Scene(panel));
        initModality(Modality.WINDOW_MODAL);
    }
}
