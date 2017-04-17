package ru.glosav.dstool.gui;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.glosav.dstool.gui.tabs.cassandra.CassandraTab;
import ru.glosav.dstool.gui.misc.ConsoleTextArea;

import javax.annotation.PostConstruct;

/**
 * Created by abalyshev on 20.07.16.
 */
@Component
public class MainPanel extends BorderPane {

    @Autowired
    private CassandraTab cassandraTab;

    ConsoleTextArea outputBar;
    TabPane tabPane;
    Tab uploadTab;
    Tab fetchTab;
    SplitPane splitCenter;

    public MainPanel() {}

    @PostConstruct
    public void onInit() {
        outputBar = new ConsoleTextArea();

        tabPane = new TabPane();
        uploadTab = new Tab();
        uploadTab.setText("Загрузка данных");
        uploadTab.setContent(new UploadPanel(this));
        uploadTab.setClosable(false);

        fetchTab = new Tab();
        fetchTab.setText("Выгрузка данных");
        fetchTab.setContent(new FetchPanel(this));
        fetchTab.setClosable(false);

//        tabPane.getTabs().add(uploadTab);
//        tabPane.getTabs().add(fetchTab);
        tabPane.getTabs().add(cassandraTab);

        Button clearBtn = new Button("X");
        clearBtn.setTooltip(new Tooltip("Clear output"));
        VBox bottomToolbar = new VBox(clearBtn);
        bottomToolbar.setAlignment(Pos.TOP_RIGHT);
        bottomToolbar.setMaxWidth(20);
        bottomToolbar.autosize();
        HBox bottom = new HBox(outputBar, bottomToolbar);
        HBox.setHgrow(outputBar, Priority.ALWAYS);
        clearBtn.setOnAction(event -> {
            outputBar.clear();
        });

        splitCenter = new SplitPane();
        splitCenter.setOrientation(Orientation.VERTICAL);
        splitCenter.getItems().addAll(tabPane, bottom);
        splitCenter.setDividerPositions(1.0);

        setCenter(splitCenter);
    }
}
