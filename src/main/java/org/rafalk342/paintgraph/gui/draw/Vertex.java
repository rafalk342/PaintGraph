package org.rafalk342.paintgraph.gui.draw;

import javafx.scene.shape.Circle;

import java.util.ArrayList;

class Vertex extends Circle {
    public ArrayList<Edge> getEdges() {
        return edges;
    }

    ArrayList<Edge> edges;

    Vertex() {
        this.edges = new ArrayList<>();
    }

    void addEdge(Edge edge){
        edges.add(edge);
    }


}
