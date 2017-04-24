package ru.glosav.dstool.gui.misc.dialog;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import ru.glosav.dstool.entity.CqlConnection;

import static javafx.scene.control.ButtonBar.ButtonData.OK_DONE;
import static ru.glosav.dstool.utils.AnyUtils.isEmpty;

/**
 * Created by abalyshev on 17.04.17.
 */
public class DialogFactory {
    public static synchronized Dialog<CqlConnection> createCqlConnDialog() {
        Dialog<CqlConnection> cqlConnDlg = new Dialog<>();
        cqlConnDlg.setTitle("New connection");
        cqlConnDlg.setHeaderText(null);
        cqlConnDlg.setResizable(false);
        Label hostLbl = new Label("Host: ");
        Label keySpaceLbl = new Label("Key space: ");
        TextField hostTf = new TextField();
        TextField keySpaceTf = new TextField();
        GridPane grid = new GridPane();
        grid.add(hostLbl, 1, 1);
        grid.add(hostTf, 2, 1);
        grid.add(keySpaceLbl, 1, 2);
        grid.add(keySpaceTf, 2, 2);
        cqlConnDlg.getDialogPane().setContent(grid);
        ButtonType buttonTypeOk = new ButtonType("Ok", OK_DONE);
        cqlConnDlg.getDialogPane().getButtonTypes().add(buttonTypeOk);
        cqlConnDlg.setResultConverter(b -> {
            if (b == buttonTypeOk && !isEmpty(hostTf.getText()) && !isEmpty(keySpaceTf.getText())) {
                return new CqlConnection(hostTf.getText(), keySpaceTf.getText());
            }
            return null;
        });
        cqlConnDlg.setOnHidden(event -> {
            hostTf.clear();
            keySpaceTf.clear();
        });
        return cqlConnDlg;
    }
}
