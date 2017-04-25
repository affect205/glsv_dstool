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
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import ru.glosav.dstool.dto.MessageDTO;
import ru.glosav.dstool.entity.CqlApiMethod;
import ru.glosav.dstool.event.ExecEvent;
import ru.glosav.dstool.gui.misc.ConsoleTextArea;
import ru.glosav.dstool.service.CqlService;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static ru.glosav.dstool.gui.misc.converter.Converters.STRING_TO_CQL_API_METHOD;
import static ru.glosav.dstool.model.CqlApiIModel.getCqlApiMethods;

/**
 * Created by abalyshev on 18.04.17.
 */
@Component
public class CqlQueryPanel extends Stage
        implements ApplicationListener<ExecEvent> {
    private static final Logger log = LoggerFactory.getLogger(CqlQueryPanel.class);

    @Autowired
    private CqlService cqlService;

    @Autowired
    private ConsoleTextArea consoleTA;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    private VBox argWrap;
    private VBox leftPanel;
    private VBox bottomPanel;
    private VBox centerPanel;

    private BorderPane root;
    private TextArea queryTA;
    private Button runBtn;
    private ComboBox<CqlApiMethod> apiCb;
    private Map<Integer, ArgPanel> argPanelCache;

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

        centerPanel = new VBox();
        bottomPanel = new VBox();
        bottomPanel.setPrefHeight(120);

        argWrap = new VBox();

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
                ArgPanel ap = argPanelCache.get(newValue.hashCode());
                if (ap == null) {
                    ap = new ArgPanel(newValue.getArgs());
                    argPanelCache.put(newValue.hashCode(), ap);
                }
                argWrap.getChildren().clear();
                argWrap.getChildren().addAll(ap);
            });
        });

        queryTA = new TextArea();

        runBtn = new Button(">");
        runBtn.setOnAction(event -> {
            eventPublisher.publishEvent(new ExecEvent(this));
        });

        leftPanel.getChildren().addAll(apiCb, argWrap);
        centerPanel.getChildren().addAll(queryTA);
        bottomPanel.getChildren().addAll(table);

        root.setTop(runBtn);
        root.setCenter(centerPanel);
        root.setLeft(leftPanel);
        root.setBottom(bottomPanel);

        setScene(new Scene(root));
        initModality(Modality.WINDOW_MODAL);
    }

    @Override
    public void onApplicationEvent(ExecEvent event) {
        CqlApiMethod item = apiCb.getSelectionModel().getSelectedItem();
        if (item != null) {
            for (Method m : CqlService.class.getMethods()) {
                if (item.getName().equalsIgnoreCase(m.getName()) && item.getArgs().size() == m.getParameterCount()) {
                    if (argPanelCache.get(item.hashCode()) != null) {
                        try {
                            Object[] args = argPanelCache.get(item.hashCode()).getArgs();
                            Object result = m.invoke(cqlService, args);
                            String test = "and...";
                        } catch (Exception e) {
                            log.error(e.toString());
                        }
                    }
                }
            }
        }
    }
}
