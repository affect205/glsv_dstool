package ru.glosav.dstool;

import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import ru.glosav.dstool.gui.AbstractJavaFxApp;
import ru.glosav.dstool.gui.MainPanel;

@Lazy
@SpringBootApplication
public class Application extends AbstractJavaFxApp {
	private static final Logger logger = LoggerFactory.getLogger(Application.class);

//	@Value("${ui.title:JavaFX приложение}")
//	private String windowTitle;

	@Bean
	public Stage getStage() {
		Stage newStage = new Stage(StageStyle.DECORATED);
		newStage.setTitle("JavaFX by Spring Boot");
		return newStage;
	}

	@Override
	public void start(Stage stage) throws Exception {
		Task<Object> worker = new Task<Object>() {
			@Override protected Object call() throws Exception {
				context = SpringApplication.run(Application.class, savedArgs);
				return null;
			}
		};
		worker.run();

		worker.setOnSucceeded(event -> {
			try {
				logger.info("Loading Spring successful, Application will come soon.");
                String windowTitle = context.getEnvironment().getProperty("ui.title");
                MainPanel mainPanel = context.getBean(MainPanel.class);
				stage.setScene(new Scene(mainPanel, 960, 680));
				stage.centerOnScreen();
				stage.setResizable(true);
				stage.show();
				stage.setTitle(windowTitle);
			} catch (Exception ex) {
				logger.error("Loading Application Error.", ex);
			}
		});

		worker.setOnFailed(event -> {
			try {
				logger.error("Loading Spring Failing, Application will shutdown now.");
			} catch (Exception ex) {
				logger.error("Shutdown Application Error.", ex);
			}
		});
	}

	public static void main(String[] args) {
		launchApp(Application.class, args);
	}
}
