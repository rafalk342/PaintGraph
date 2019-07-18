package org.rafalk342.paintgraph.gui.draw;


import javafx.application.Platform;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DrawView extends BorderPane {
    private static final Logger log = LoggerFactory.getLogger(DrawView.class);
    private final DrawModel drawModel;
    public Pane pane = new Pane();

    public DrawView(DrawModel drawModel) {
        log.info("Initializing [{}], {}", getClass().getSimpleName());
        this.drawModel = drawModel;
        Platform.runLater(() -> {
            setUserData(DrawConstants.TAB_NAME);
            layoutForm();
        });
    }

    private void layoutForm() {
        setCenter(pane);
    }
}
