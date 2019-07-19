package org.rafalk342.paintgraph.gui.draw;

import javafx.scene.shape.Line;

class Edge {
    Vertex dest;
    Edge symmetric;
    Line line;

    Edge(Vertex dest, Line line) {
        this.dest = dest;
        this.line = line;
    }

    void setSymmetric(Edge symmetric) {
        this.symmetric = symmetric;
    }
}
