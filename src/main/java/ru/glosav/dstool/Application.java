package ru.glosav.dstool;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Lazy;
import ru.glosav.dstool.app.AbstractJavaFxApp;
import ru.glosav.dstool.gui.MainPanel;

@Lazy
@SpringBootApplication
public class Application extends AbstractJavaFxApp {
	@Value("${ui.title:JavaFX приложение}")
	private String windowTitle;

	@Autowired
	private MainPanel mainPanel;

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle(windowTitle);
		Scene scene = new Scene(new Group());
		stage.setWidth(720);
		stage.setHeight(512);
		stage.setScene(scene);
		scene.setRoot(mainPanel);
		stage.setResizable(true);
		stage.centerOnScreen();
		stage.show();
	}

	public static void main(String[] args) {
		launchApp(Application.class, args);
	}
}
