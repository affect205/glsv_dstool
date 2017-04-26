package ru.glosav.dstool.gui.tabs.cassandra;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
import ru.glosav.dstool.entity.CqlApiMethod;
import ru.glosav.dstool.entity.interfaces.IDTORow;
import ru.glosav.dstool.entity.rows.dto.DTOResult;
import ru.glosav.dstool.entity.rows.dto.MessageDTO;
import ru.glosav.dstool.event.CqlResultEvent;
import ru.glosav.dstool.event.ExecEvent;
import ru.glosav.dstool.gui.misc.ConsoleTextArea;
import ru.glosav.dstool.gui.misc.table.CqlTable;
import ru.glosav.dstool.gui.utils.TableUtils;
import ru.glosav.dstool.service.CqlAdapterService;
import ru.glosav.dstool.service.adapters.ICqlAdapter;
import ru.glosav.kiask.protobuf.generated.message.MessageProto;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static ru.glosav.dstool.gui.misc.converter.Converters.STRING_TO_CQL_API_METHOD;
import static ru.glosav.dstool.resolver.CqlApiResolver.getCqlApiMethods;

/**
 * Created by abalyshev on 18.04.17.
 */
@Component
public class CqlQueryPanel extends Stage implements ApplicationListener<ExecEvent> {
    private static final Logger log = LoggerFactory.getLogger(CqlQueryPanel.class);

    @Autowired
    private CqlAdapterService cqlAdapterService;

    @Autowired
    private ConsoleTextArea consoleTA;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private CqlTable cqlTable;

    private VBox argWrap;
    private VBox leftPanel;
    private VBox splitBottom;
    private VBox centerPanel;

    private SplitPane root;

    private BorderPane splitTop;
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
        IDTORow testDTO = new MessageDTO(224214L, 213, 7, Instant.now().toEpochMilli(), Instant.now().toEpochMilli(), MessageProto.MESSAGE.getDefaultInstance());
        IDTORow testDTO2 = new MessageDTO(224214L, 214, 8, Instant.now().toEpochMilli(), Instant.now().toEpochMilli(), MessageProto.MESSAGE.getDefaultInstance());
        List<IDTORow> testData = new LinkedList<>();
        testData.add(testDTO);
        testData.add(testDTO2);

        TableView<IDTORow> table = new TableView<>();
        List<TableColumn<IDTORow, ?>> columns = TableUtils.makeColumns(MessageDTO.class);
        table.getColumns().setAll(columns);
        table.setItems(FXCollections.observableArrayList(testData));

        argPanelCache = new ConcurrentHashMap<>();

        leftPanel = new VBox();
        leftPanel.setPrefWidth(320);
        leftPanel.setPrefHeight(400);

        centerPanel = new VBox();
        splitBottom = new VBox();
        splitBottom.setPrefHeight(120);

        argWrap = new VBox();

        splitTop = new BorderPane();

        apiCb = new ComboBox<>();

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
        splitBottom.getChildren().addAll(cqlTable);

        splitTop.setTop(runBtn);
        splitTop.setCenter(centerPanel);
        splitTop.setLeft(leftPanel);

        root = new SplitPane();
        root.setOrientation(Orientation.VERTICAL);
        root.getItems().addAll(splitTop, splitBottom);
        root.setDividerPositions(1.0);

        setScene(new Scene(root));
        initModality(Modality.WINDOW_MODAL);
    }

    @Override
    public void onApplicationEvent(ExecEvent event) {
        CqlApiMethod item = apiCb.getSelectionModel().getSelectedItem();
        if (item != null) {
            for (Method m : ICqlAdapter.class.getMethods()) {
                if (item.getName().equalsIgnoreCase(m.getName()) && item.getArgs().size() == m.getParameterCount()) {
                    if (argPanelCache.get(item.hashCode()) != null) {
                        try {
                            Object[] args = argPanelCache.get(item.hashCode()).getArgs();
                            Object result = m.invoke(cqlAdapterService, args);
                            String test = "and...";
                            if (result != null) {
                                eventPublisher.publishEvent(new CqlResultEvent(this, (DTOResult)result));
                            }
                        } catch (Exception e) {
                            log.error(e.toString());
                        }
                    }
                }
            }
        }
    }
}
