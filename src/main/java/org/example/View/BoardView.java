package org.example.View;

import org.example.Controller.GameController;

import javax.swing.*;
import java.awt.*;

public class BoardView extends JPanel {

    Integer selectedI = null;
    Integer selectedJ = null;

    int size = 7;

    public BoardView(GameController gameController) {

        setLayout(new BorderLayout());
        JButton[][] pieces = gameController.pieces;
        int[][] matrix = gameController.board;
        JPanel buttonsPanel = new JPanel(new GridLayout(size, size));


        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                if (matrix[i][j] == 0) {
                    pieces[i][j].setBackground(Color.black);
                } else if (matrix[i][j] == 1) {
                    pieces[i][j].setBackground(Color.blue);
                } else if (matrix[i][j] == -1) {
                    pieces[i][j].setEnabled(false);
                }
                pieces[6][6].setBackground(Color.GRAY);
                pieces[6][6].setLabel("DESISTIR");
                pieces[6][6].setEnabled(true);

                int currentI = i;
                int currentJ = j;

                pieces[i][j].addActionListener(e -> {
                    if (selectedI == null && selectedJ == null && pieces[currentI][currentJ].getBackground() == Color.blue) {
                        pieces[currentI][currentJ].setBackground(Color.green);
                        selectedI = currentI;
                        selectedJ = currentJ;

                    } else if (selectedI != null && selectedJ != null && pieces[currentI][currentJ].getBackground() == Color.black) {
                        String data = selectedI + "," + selectedJ + "->" + currentI + "," + currentJ;
                        selectedI = null;
                        selectedJ = null;
                        gameController.makeAndSendMove(data);
                        gameController.updateButtons(pieces);
                    } else if (pieces[currentI][currentJ].getBackground() == Color.GRAY) {
                        gameController.sendResult("loser");
                    } else {
                        selectedI = null;
                        selectedJ = null;
                        gameController.updateButtons(pieces);
                    }

                });
                buttonsPanel.add(pieces[i][j]);
            }
        }
        add(buttonsPanel, BorderLayout.CENTER);
    }


}