package ru.glosav.dstool.gui;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import ru.glosav.dstool.gui.utils.GuiUtils;
import ru.glosav.dstool.utils.AnyUtils;

/**
 * Created by abalyshev on 20.07.16.
 */
public class UploadPanel extends GridPane {

    private MainPanel rootPanel;
    private TextField pointCntFld;
    private TextField rangeFld;
    private Button runBtn;
    private TextField idRangeFromFld;
    private TextField idRangeToFld;

    public UploadPanel(MainPanel rootPanel) {
        super();
        this.rootPanel = rootPanel;
        pointCntFld = new TextField();
        pointCntFld.setText("");
        pointCntFld.textProperty().addListener(GuiUtils.getNumberListener(pointCntFld));

        rangeFld = new TextField("Шаг при выводе");
        rangeFld.setText("");
        rangeFld.textProperty().addListener(GuiUtils.getNumberListener(rangeFld));

        idRangeFromFld = new TextField();
        idRangeFromFld.setText("");
        idRangeFromFld.textProperty().addListener(GuiUtils.getNumberListener(idRangeFromFld));

        idRangeToFld = new TextField();
        idRangeToFld.setText("");
        idRangeToFld.textProperty().addListener(GuiUtils.getNumberListener(idRangeToFld));

        runBtn = new Button("Запустить");
        runBtn.setOnAction(event -> {
//            int pointCount = GuiUtils.parseInt(pointCntFld.getText(), UploadTask.POINTS_COUNT);
//            int rangeDiapason = GuiUtils.parseInt(rangeFld.getText(), UploadTask.RANGE_DIAPASON);
//            int idRangeFrom = GuiUtils.parseInt(idRangeFromFld.getText(), UploadTask.ID_RANGE_FROM);
//            int idRangeTo = GuiUtils.parseInt(idRangeToFld.getText(), UploadTask.ID_RANGE_TO);
//            if (idRangeFrom > idRangeTo) {
//                ConsoleUtils.printf("Error! idRangeFrom(%s) > idRangeTo(%s)", idRangeFrom, idRangeTo);
//                return;
//            }
//            try {
//                Upload.runUpload(IntStream.range(idRangeFrom, idRangeTo), pointCount, rangeDiapason);
//            } catch(Exception e) {
//                e.printStackTrace();
//            }
        });

        setHgap(14);
        setVgap(14);
        addRow(1, new Label("ID девайсов с"), idRangeFromFld, new Label("по"), idRangeToFld);
        addRow(2, new Label("Количество записей"), pointCntFld);
        addRow(3, new Label("Шаг при выводе"), rangeFld);
        addRow(4, new Label(""), runBtn);
    }
}
