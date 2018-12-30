package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Controller {

    @FXML
    Pane board;

    @FXML
    MenuBar menuBar;

    ExtendedCircle selected;

    Integer numberOfVertices, numberOfEdges;
    ArrayList<ExtendedCircle> graph;
    Random random;

    @FXML
    public void initialize() {
        board.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        menuBar.prefWidthProperty().bind(board.widthProperty());

        graph = new ArrayList<>();
        numberOfVertices = 0;
        numberOfEdges = 0;

        random = new Random();
    }

    @FXML
    public void clickedMouse(MouseEvent mouseEvent) {
        double sceneX = mouseEvent.getSceneX();
        double sceneY = mouseEvent.getSceneY();
        if (sceneY < menuBar.getHeight() + 10)
            return;

        System.out.println("New circle " + sceneX + " " + sceneY);
        createCircle(sceneX, sceneY);
    }

    private void createCircle(double sceneX, double sceneY) {
        ExtendedCircle circle = new ExtendedCircle();
        circle.setCenterX(sceneX);
        circle.setCenterY(sceneY);
        circle.setRadius(10.0f);
        circle.setFill(Color.BLACK);
        circle.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            if (circle.getFill() == Color.BLACK) {
                circle.setFill(Color.RED);
            }
        });
        circle.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            if (circle.getFill() == Color.RED) {
                circle.setFill(Color.BLACK);
            }
        });
        circle.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if (circle.getFill() == Color.RED) {
                if (selected == null) {
                    circle.setFill(Color.GREEN);
                    selected = circle;
                } else {
                    addEdge(selected, circle);
                }
            } else {
                circle.setFill(Color.BLACK);
                selected = null;
            }
            e.consume();

        });
        board.getChildren().add(circle);
        circle.setVertexId(graph.size());

        graph.add(circle);
        numberOfVertices++;
    }

    private void addEdge(ExtendedCircle u, ExtendedCircle v) {
        Line line = new Line();
        line.setMouseTransparent(true);
        line.setStartX(u.getCenterX());
        line.setStartY(u.getCenterY());
        line.setEndX(v.getCenterX());
        line.setEndY(v.getCenterY());
        board.getChildren().add(line);

        u.addEdge(v);
        numberOfEdges++;
    }

    @FXML
    public void saveToFile() {
        System.out.println("Saving graph.");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save graph.");
        File file = fileChooser.showSaveDialog(board.getScene().getWindow());
        if (file != null) {
            try {
                FileWriter fileWriter = new FileWriter(file);
                PrintWriter printWriter = new PrintWriter(fileWriter);
                printWriter.println(numberOfVertices + " " + numberOfEdges);
                for (ExtendedCircle vertex : graph) {
                    for (ExtendedCircle edge : vertex.edges) {
                        printWriter.println(vertex.vertexId + " " + edge.vertexId);
                    }
                }
                printWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void openFromFile() {
        System.out.println("Opening graph.");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose your graph.");
        File file = fileChooser.showOpenDialog(board.getScene().getWindow());
        if (file != null) {
            clearGraph();
            try (Scanner scanner = new Scanner(file)) {
                int n = scanner.nextInt();
                int m = scanner.nextInt();
                for (int i = 0; i < n; i++) {
                    createCircle();
                }
                for (int i = 0; i < m; i++) {
                    int u = scanner.nextInt();
                    int v = scanner.nextInt();
                    addEdge(graph.get(u), graph.get(v));
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void clearGraph() {
        selected = null;
        numberOfVertices = 0;
        numberOfEdges = 0;
        graph = new ArrayList<>();
        Node node = board.getChildren().get(0);
        board.getChildren().clear();
        board.getChildren().add(node);
    }

    private double getRandomValue(double min, double max) {
        return min + (max - min) * random.nextDouble();
    }

    private void createCircle() {
        double x = getRandomValue(0, board.getWidth());
        double y = getRandomValue(menuBar.getHeight() + 10, board.getHeight());
        createCircle(x, y);
    }

    public void showAbout(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Author: Rafał Kaszuba");

        alert.showAndWait();
    }

    public void showUsage(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Usage Dialog");
        alert.setHeaderText(null);
        alert.setContentText(
                "- click anywhere to add vertex\n" +
                        "- click on vertex to start adding its edges\n" +
                        "- click on vertex again to exit edges mode\n" +
                        "- feel free to save your work\n" +
                        "- open any file in standard TCS graph format"
        );

        alert.showAndWait();
    }
}
