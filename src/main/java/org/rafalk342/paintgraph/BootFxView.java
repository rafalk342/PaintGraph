package org.rafalk342.paintgraph;

import org.rafalk342.paintgraph.gui.chart.ChartView;
import org.rafalk342.paintgraph.gui.draw.DrawView;
import org.rafalk342.paintgraph.gui.form.FormView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javafx.application.Platform;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;

@Component
public class BootFxView extends BorderPane {

	private static final Logger log = LoggerFactory.getLogger(BootFxView.class);

	TabPane tabPane = new TabPane();

	public BootFxView(ChartView chartView, FormView formView, DrawView drawView) {
		Platform.runLater(() -> {
			log.info("Initializing  [{}] ", getClass().getSimpleName());

			Tab drawTab = new Tab();
			drawTab.setText(drawView.getUserData().toString());
			drawTab.setContent(drawView);
			drawTab.setClosable(false);

			tabPane.getTabs().addAll(drawTab);
			setCenter(tabPane);
		});
	}

}
