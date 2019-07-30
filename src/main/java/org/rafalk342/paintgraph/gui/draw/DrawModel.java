package org.rafalk342.paintgraph.gui.draw;

import javafx.scene.shape.Line;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class DrawModel {
    private int idCounter = 0;
    private ArrayList<Vertex> graph = new ArrayList<>();

    void addVertex(Vertex vertex) {
        vertex.setVertexId(idCounter++);
        graph.add(vertex);
    }

    void addEdgeModel(Vertex u, Vertex v, Line line) {
        Edge forwardEdge = new Edge(v, line);
        Edge backwardEdge = new Edge(u, line);
        forwardEdge.setSymmetric(backwardEdge);
        backwardEdge.setSymmetric(forwardEdge);

        u.addEdge(forwardEdge);
        v.addEdge(backwardEdge);
    }

    void removeVertex(Vertex u) {
        for (Edge edge : u.edges) {
            edge.dest.edges.remove(edge.symmetric);
        }
        graph.remove(u);
    }

    void clearGraph() {
        graph = new ArrayList<>();
    }

    Vertex getVertex(int i){
        return graph.get(i);
    }
}
