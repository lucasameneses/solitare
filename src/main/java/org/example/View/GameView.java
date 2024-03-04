package org.example.View;

import org.example.Controller.GameController;

import javax.swing.*;
import java.awt.*;

public class GameView extends JPanel {

    public JPanel boardPanel;
    public JPanel waitingPanel;

    public GameView(GameController gameController) {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        boardPanel = new BoardView(gameController);
        waitingPanel = new WaitingView();
        JPanel chatPanel = new ChatView(gameController);

        add(boardPanel);
        add(waitingPanel);
        add(chatPanel);

//        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, boardPanel, chatPanel);
//        splitPane.setResizeWeight(0.9);
//        splitPane.setDividerLocation(0.5);
//        add(splitPane);
    }

}
