package ru.glosav.dstool.gui.tabs.cassandra;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.glosav.cassandra.dao.ICassandraDaoLocal;
import ru.glosav.cassandra.dao.impl.CassandraGate;
import ru.glosav.dstool.dto.MessageDTO;
import ru.glosav.dstool.entity.CqlApiMethod;
import ru.glosav.dstool.gui.misc.ConsoleTextArea;
import ru.glosav.dstool.model.CqlApiIModel;
import ru.glosav.dstool.service.CassandraService;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ru.glosav.dstool.gui.misc.converter.Converters.STRING_TO_CQL_API_METHOD;
import static ru.glosav.dstool.gui.misc.converter.Converters.STRING_TO_METHOD;
import static ru.glosav.dstool.model.CqlApiIModel.getCqlApiMethods;

/**
 * Created by abalyshev on 18.04.17.
 */
@Component
public class CqlQueryPanel extends Stage {

    private static final Logger log = LoggerFactory.getLogger(CqlQueryPanel.class);

    @Autowired
    private CassandraService cassandraService;

    @Autowired
    private ConsoleTextArea consoleTA;

    private BorderPane root;
    private TextArea queryTA;
    private Button runBtn;
    private ComboBox<CqlApiMethod> apiCb;
    private VBox leftPanel;
    private Map<Integer, ArgPanel> argPanelCache;

    //private ComboBox<CqlApiMethod> apiCb;

    public CqlQueryPanel() {
        setWidth(720);
        setHeight(512);
    }

    @PostConstruct
    public void onInit() {
        MessageDTO testDTO = new MessageDTO(224214L, 213, 7, Instant.now().toEpochMilli(), Instant.now().toEpochMilli(), ByteBuffer.wrap(new byte[] {1, 2, 4, 5}));
        List<MessageDTO> testData = new LinkedList<>();
        testData.add(testDTO);

        TableView<MessageDTO> table = new TableView<>();

        TableColumn<MessageDTO, Integer> devIdCol = new TableColumn<>("device_id");
        devIdCol.setCellValueFactory(new PropertyValueFactory("devId"));
        TableColumn<MessageDTO, Integer> opIdCol = new TableColumn<>("operator_id");
        opIdCol.setCellValueFactory(new PropertyValueFactory("lastName"));
        TableColumn<MessageDTO, Long> devOpCol = new TableColumn<>("dev_op");
        devOpCol.setCellValueFactory(new PropertyValueFactory("devOp"));
        TableColumn<MessageDTO, String> spanCol = new TableColumn<>("span");
        spanCol.setCellValueFactory(new PropertyValueFactory("span"));
        TableColumn<MessageDTO, String> tsCol = new TableColumn<>("ts");
        tsCol.setCellValueFactory(new PropertyValueFactory("ts"));
        TableColumn<MessageDTO, String> payloadCol = new TableColumn<>("payload");
        payloadCol.setCellValueFactory(new PropertyValueFactory("payload"));

        DateTimeFormatter df = DateTimeFormatter
                .ofPattern("dd-MM-yyyy HH:mm:ss")
                .withZone(ZoneId.systemDefault());

        table.getColumns().setAll(devIdCol, opIdCol, devOpCol, spanCol, tsCol, payloadCol);
        table.setItems(FXCollections.observableArrayList(testData));

        devIdCol.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue().devId));
        opIdCol.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue().devId));
        devOpCol.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue().devOp));
        spanCol.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(df.format(Instant.ofEpochMilli(p.getValue().span))));
        tsCol.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(df.format(Instant.ofEpochMilli(p.getValue().ts))));
        payloadCol.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue().payload.array().toString()));

        argPanelCache = new ConcurrentHashMap<>();

        leftPanel = new VBox();
        leftPanel.setPrefWidth(320);
        leftPanel.setPrefHeight(400);

        root = new BorderPane();

        //apiLv = new ListView<>();
        apiCb = new ComboBox<>();
        // извлекаем api методы из CassandraGate
//        List<Method> apiList = Stream.of(CassandraService.class.getMethods())
//                .filter(m -> Stream.of(Object.class.getMethods())
//                        .noneMatch(om -> Objects.equals(om.getName(), m.getName()))
//                )
//                .collect(Collectors.toList());
//        apiLv.setItems(FXCollections.observableArrayList(apiList));
//        apiLv.setPrefHeight(300);
//        apiLv.setPrefWidth(210);
//        apiLv.setCellFactory(param -> new ListCell<Method>() {
//            @Override
//            protected void updateItem(Method item, boolean empty) {
//                super.updateItem(item, empty);
//                if (item != null) {
//                    setText(item.getName());
//                }
//            }
//        });
        apiCb.setItems(FXCollections.observableArrayList(getCqlApiMethods()));
        apiCb.setPromptText("available methods");
        apiCb.setConverter(STRING_TO_CQL_API_METHOD);
        apiCb.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            Platform.runLater(() -> {
                ArgPanel argPanel = argPanelCache.get(newValue.hashCode());
                if (argPanel == null) {
                    argPanel = new ArgPanel(newValue.getArgs());
                }
                leftPanel.getChildren().clear();
                leftPanel.getChildren().addAll(apiCb, argPanel);
            });
        });

        queryTA = new TextArea();

        runBtn = new Button(">");
        runBtn.setOnAction(event -> {
            String query = queryTA.getText();
            try {
                List<String> result = cassandraService.execute(query);
                consoleTA.println(result);
            } catch (Throwable e) {
                consoleTA.println(e.getMessage());
            }
        });

        leftPanel.getChildren().add(apiCb);

        root.setCenter(queryTA);
        root.setTop(runBtn);
        root.setLeft(leftPanel);
        root.setBottom(table);

        setScene(new Scene(root));
        initModality(Modality.WINDOW_MODAL);
    }
}
