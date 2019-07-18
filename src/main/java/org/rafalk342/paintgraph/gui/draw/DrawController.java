package org.rafalk342.paintgraph.gui.draw;

import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import org.rafalk342.paintgraph.gui.chart.ChartController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DrawController {
    private static final Logger log = LoggerFactory.getLogger(ChartController.class);

    private final DrawModel model;
    private final DrawView view;

    private Circle selected;

    public DrawController(DrawModel model, DrawView view) {
        log.info("Initializing [{}]", getClass().getSimpleName());
        this.model = model;
        this.view = view;
        attachEvents();
    }

    private void attachEvents() {
        view.pane.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                double x = mouseEvent.getX();
                double y = mouseEvent.getY();
                log.info("clicked x: {}, y: {}", x, y);

                createCircle(x, y);
            }
            mouseEvent.consume();
        });
    }

    private void createCircle(double x, double y) {
        Circle vertex = new Circle();
        ContextMenu contextMenu = new ContextMenu();

        vertex.setCenterX(x);
        vertex.setCenterY(y);
        vertex.setRadius(15.0f);
        vertex.setFill(Color.BLACK);
        vertex.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                if (selected != null) {
                    addEdge(selected, vertex);
                }
            } else {
                if (selected == null || selected == vertex)
                    contextMenu.show(vertex, Side.RIGHT, 0, 0);

            }
            mouseEvent.consume();
        });

        MenuItem addEdges = new MenuItem("Add edges");
        addEdges.setOnAction(event -> {
            vertex.setFill(Color.GREEN);
            selected = vertex;
        });
        MenuItem stopAddingEdges = new MenuItem("Stop adding edges");
        stopAddingEdges.setOnAction(event -> {
            vertex.setFill(Color.BLACK);
            selected = null;
        });
        MenuItem remove = new MenuItem("Remove");
        remove.setOnAction(event -> view.pane.getChildren().remove(vertex));
        contextMenu.getItems().addAll(addEdges, stopAddingEdges, remove);

        view.pane.getChildren().add(vertex);
    }

    private void addEdge(Circle u, Circle v) {
        Line line = new Line();
        line.setMouseTransparent(true);
        line.setStartX(u.getCenterX());
        line.setStartY(u.getCenterY());
        line.setEndX(v.getCenterX());
        line.setEndY(v.getCenterY());
        view.pane.getChildren().add(line);
    }
}
