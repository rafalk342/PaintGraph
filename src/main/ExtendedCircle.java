package main;

import javafx.scene.shape.Circle;

import java.util.ArrayList;

class ExtendedCircle extends Circle {
    ArrayList<ExtendedCircle> edges;
    int vertexId;

    ExtendedCircle() {
        this.edges = new ArrayList<>();
    }

    void setVertexId(int vertexId) {
        this.vertexId = vertexId;
    }

    void addEdge(ExtendedCircle circle){
        edges.add(circle);
    }
}
