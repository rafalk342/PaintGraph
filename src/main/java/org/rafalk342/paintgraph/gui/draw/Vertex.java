package org.rafalk342.paintgraph.gui.draw;

import javafx.scene.shape.Circle;

import java.util.ArrayList;

class Vertex extends Circle {
    ArrayList<Edge> edges;
    int vertexId;

    Vertex() {
        this.edges = new ArrayList<>();
    }

    void setVertexId(int vertexId) {
        this.vertexId = vertexId;
    }

    void addEdge(Edge edge){
        edges.add(edge);
    }
}
