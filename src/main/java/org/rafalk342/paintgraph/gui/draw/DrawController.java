package org.rafalk342.paintgraph.gui.draw;

import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import org.rafalk342.paintgraph.gui.chart.ChartController;
import org.rafalk342.paintgraph.gui.utils.Dialogs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

@Component
public class DrawController {
    private static final Logger log = LoggerFactory.getLogger(ChartController.class);

    private final DrawModel model;
    private final DrawView view;

    private Random random = new Random();

    private Vertex selected;

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
        Vertex vertex = new Vertex();
        ContextMenu contextMenu = new ContextMenu();

        vertex.setCenterX(x);
        vertex.setCenterY(y);
        vertex.setRadius(15.0f);
        vertex.setFill(Color.BLACK);
        vertex.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                if (selected != null && selected != vertex) {
                    addEdgeViewModel(selected, vertex);
                } else {
                    contextMenu.show(vertex, Side.RIGHT, 0, 0);
                }
            } else {
                if (selected == null || selected == vertex)
                    contextMenu.show(vertex, Side.RIGHT, 0, 0);

            }
            mouseEvent.consume();
        });

        MenuItem addEdges = new MenuItem("Add edges");
        MenuItem stopAddingEdges = new MenuItem("Stop adding edges");
        MenuItem remove = new MenuItem("Remove");

        addEdges.setOnAction(event -> {
            vertex.setFill(Color.GREEN);
            selected = vertex;
            addEdges.setDisable(true);
            stopAddingEdges.setDisable(false);
        });

        stopAddingEdges.setOnAction(event -> {
            vertex.setFill(Color.BLACK);
            selected = null;
            addEdges.setDisable(false);
            stopAddingEdges.setDisable(true);
        });
        stopAddingEdges.setDisable(true);

        remove.setOnAction(event -> {
            model.removeVertex(vertex);
            for (Edge edge : vertex.edges) {
                view.pane.getChildren().remove(edge.line);
            }

            view.pane.getChildren().remove(vertex);
            selected = null;
        });
        contextMenu.getItems().addAll(addEdges, stopAddingEdges, remove);

        view.pane.getChildren().add(vertex);

        model.addVertex(vertex);
    }

    private void addEdgeViewModel(Vertex u, Vertex v) {
        Line line = new Line();
        line.setMouseTransparent(true);
        line.setStartX(u.getCenterX());
        line.setStartY(u.getCenterY());
        line.setEndX(v.getCenterX());
        line.setEndY(v.getCenterY());

        view.pane.getChildren().add(line);

        model.addEdgeModel(u, v, line);
    }

    public DrawView getView() {
        return view;
    }

    public void clear() {
        selected = null;
        model.clearGraph();
        view.pane.getChildren().clear();
    }

    public void openFromFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose your graph.");
        File file = fileChooser.showOpenDialog(view.getScene().getWindow());
        if (file != null) {
            clear();
            try (Scanner scanner = new Scanner(file)) {
                int n = scanner.nextInt();
                int m = scanner.nextInt();
                for (int i = 0; i < n; i++) {
                    double x = getRandomValue(0, view.getWidth());
                    double y = getRandomValue(0, view.getHeight());
                    createCircle(x, y);
                }
                for (int i = 0; i < m; i++) {
                    int u = scanner.nextInt();
                    int v = scanner.nextInt();
                    addEdgeViewModel(model.getVertex(u), model.getVertex(v));
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (Exception e){
                clear();
                Dialogs.showError("Error in openFromFile() :", "Wrong file format.");
            }
        }
    }

    private double getRandomValue(double min, double max) {
        return min + (max - min) * random.nextDouble();
    }
}
