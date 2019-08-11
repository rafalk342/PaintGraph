package org.rafalk342.paintgraph.gui.draw;

import javafx.scene.shape.Line;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class DrawModel {
    private ArrayList<Vertex> graph = new ArrayList<>();

    void addVertex(Vertex vertex) {
        graph.add(vertex);
    }

    void addEdgeModel(Vertex u, Vertex v, Line line) {
        Edge forwardEdge = new Edge(u, v, line);
        Edge backwardEdge = new Edge(v, u, line);
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

    Vertex getVertex(int i) {
        return graph.get(i);
    }

    int getGraphSize() {
        return graph.size();
    }

    ArrayList<Edge> getEdges() {
        ArrayList<Edge> edges = new ArrayList<>();
        for (Vertex vertex : graph) {
            edges.addAll(vertex.getEdges());
        }
        return edges;
    }

    int getVertexId(Vertex vertex){
        return graph.indexOf(vertex);
    }
}
