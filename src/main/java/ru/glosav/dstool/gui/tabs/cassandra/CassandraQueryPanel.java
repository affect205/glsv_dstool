package ru.glosav.dstool.gui.tabs.cassandra;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.glosav.cassandra.dao.ICassandraDaoLocal;
import ru.glosav.dstool.dto.MessageDTO;
import ru.glosav.dstool.gui.misc.ConsoleTextArea;
import ru.glosav.dstool.gui.misc.converter.Converters;
import ru.glosav.dstool.service.CassandraService;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ru.glosav.dstool.gui.misc.converter.Converters.STRING_TO_METHOD;

/**
 * Created by abalyshev on 18.04.17.
 */
@Component
public class CassandraQueryPanel extends Stage {

    @Autowired
    private CassandraService cassandraService;

    @Autowired
    private ConsoleTextArea consoleTA;

    private BorderPane root;
    private TextArea queryTA;
    private Button runBtn;
    //private ListView<Method> apiLv;
    private ComboBox<Method> apiCb;

    public CassandraQueryPanel() {
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
        payloadCol.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>("0xaabbcc1122"));

        root = new BorderPane();

        //apiLv = new ListView<>();
        apiCb = new ComboBox<>();
        List<Method> apiList = Stream.of(ICassandraDaoLocal.class.getMethods())
                .collect(Collectors.toList());
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
        apiCb.setItems(FXCollections.observableArrayList(apiList));
        apiCb.setPromptText("available methods");
        apiCb.setConverter(STRING_TO_METHOD);

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

        root.setCenter(queryTA);
        root.setTop(runBtn);
        root.setLeft(apiCb);
        root.setBottom(table);

        setScene(new Scene(root));
        initModality(Modality.WINDOW_MODAL);
    }
}
