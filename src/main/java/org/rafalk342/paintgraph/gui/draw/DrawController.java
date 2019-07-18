package org.rafalk342.paintgraph.gui.draw;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.rafalk342.paintgraph.gui.chart.ChartController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DrawController {
    private static final Logger log = LoggerFactory.getLogger(ChartController.class);

    private final DrawModel model;
    private final DrawView view;

    public DrawController(DrawModel model, DrawView view) {
        log.info("Initializing [{}]", getClass().getSimpleName());
        this.model = model;
        this.view = view;
        attachEvents();
    }

    private void attachEvents() {
        view.pane.setOnMouseClicked(mouseEvent -> {
            double x = mouseEvent.getX();
            double y = mouseEvent.getY();
            log.info("clicked x: {}, y: {}", x, y);

            createCircle(x, y);
        });
    }

    private void createCircle(double x, double y) {
        Circle circle = new Circle();
        circle.setCenterX(x);
        circle.setCenterY(y);
        circle.setRadius(10.0f);
        circle.setFill(Color.BLACK);
        view.pane.getChildren().add(circle);
    }
}
