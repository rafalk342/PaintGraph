package org.rafalk342.paintgraph;

import javafx.scene.control.*;
import org.rafalk342.paintgraph.gui.chart.ChartView;
import org.rafalk342.paintgraph.gui.draw.DrawController;
import org.rafalk342.paintgraph.gui.draw.DrawView;
import org.rafalk342.paintgraph.gui.form.FormView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javafx.application.Platform;
import javafx.scene.layout.BorderPane;

@Component
public class BootFxView extends BorderPane {

	private static final Logger log = LoggerFactory.getLogger(BootFxView.class);

	TabPane tabPane = new TabPane();

	public BootFxView(ChartView chartView, FormView formView, DrawController drawController) {
		Platform.runLater(() -> {
			log.info("Initializing  [{}] ", getClass().getSimpleName());

			Tab drawTab = new Tab();
			DrawView drawView = drawController.getView();
			drawTab.setText(drawView.getUserData().toString());
			drawTab.setContent(drawView);
			drawTab.setClosable(false);
			tabPane.getTabs().addAll(drawTab);
			setCenter(tabPane);

			Menu menu1 = new Menu("File");

			MenuItem itemNew = new MenuItem("New");
			itemNew.setOnAction(actionEvent -> drawController.clear());

			MenuItem itemOpenFile = new MenuItem("Open file");
			itemOpenFile.setOnAction(actionEvent -> drawController.openFromFile());

			MenuItem itemSaveToFile = new MenuItem("Save to file");
			itemSaveToFile.setOnAction(actionEvent -> drawController.saveToFile());
			menu1.getItems().addAll(itemNew, itemOpenFile, itemSaveToFile);

			Menu menu2 = new Menu("Options");
			Menu menu3 = new Menu("Help");
			MenuBar menuBar = new MenuBar();
			menuBar.getMenus().addAll(menu1, menu2, menu3);
			setTop(menuBar);
		});
	}

}
