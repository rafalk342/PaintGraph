package org.rafalk342.paintgraph.gui.chart;

import static org.rafalk342.paintgraph.gui.chart.ChartConstants.CSS_PATH;
import static org.rafalk342.paintgraph.gui.chart.ChartConstants.PROJECT_TITLE;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

@SpringBootApplication
public class ChartApp extends Application {
	
	private static final Logger log = LoggerFactory.getLogger(ChartApp.class);

	private ConfigurableApplicationContext springContext;

	private Scene scene;

	public static void main(final String[] args)  {
		launch(ChartApp.class, args);
	}

	@Override
	public void init() throws Exception {
		springContext = SpringApplication.run(ChartApp.class);
		ChartController controller = springContext.getBean(ChartController.class);
		scene = new Scene(controller.getView());
		scene.getStylesheets().add(CSS_PATH);
	}

	@Override
	public void stop() throws Exception {
		springContext.stop();
	}

	@Override
	public void start(Stage stage) throws Exception {
		startApplication(stage);
	}

	private void startApplication(final Stage primaryStage) {
		log.info("Starting {}!", PROJECT_TITLE);
		primaryStage.setTitle(PROJECT_TITLE);
		primaryStage.setHeight(Screen.getPrimary().getVisualBounds().getHeight() / 2);
		primaryStage.setWidth(Screen.getPrimary().getVisualBounds().getWidth() / 2);
		primaryStage.centerOnScreen();
		primaryStage.setOnCloseRequest(e -> {
			Platform.exit();
			System.exit(0);
		});
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
