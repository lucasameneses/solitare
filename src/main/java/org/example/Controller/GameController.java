package org.example.Controller;

import org.example.Model.ProfileType;
import org.example.View.ResultView;
import org.example.View.WaitingView;
import org.example.View.GameView;
import org.example.View.MainView;

import javax.swing.*;
import java.awt.*;

public class GameController {
    MainController mainController;
    MainView mainScreen;
    Communication communication;
    public JButton[][] pieces;

    public JTextArea chatTextArea;

    public int[][] board;

    public String result;

    GameView gameView;

    public GameController(MainController mainController) {
        this.mainController = mainController;
        this.mainScreen = mainController.mainView;
        this.communication = mainController.communication;

        newBoard();

        chatTextArea = new JTextArea();
        pieces = setPieces();

        gameView = new GameView(this);
        mainScreen.mainPanel.add(gameView, "gameView");

    }

    public void sendMessage(String text) {
        chatTextArea.append("EU: " + text + "\n");
        communication.sendMessage("chat://" + text);
    }

    public void receiveMessage(String data) {
        SwingUtilities.invokeLater(() -> {
            chatTextArea.append("OPONENTE: " + data + "\n");

        });
    }

    private void showBoard(){
        gameView.boardPanel.setVisible(true);
        gameView.waitingPanel.setVisible(false);
    }

    private void showWaiting(){
        gameView.boardPanel.setVisible(false);
        gameView.waitingPanel.setVisible(true);
    }

    public void startGame(ProfileType profileType) {

        boolean gameStarted = profileType.equals(ProfileType.SERVER);
        if (!gameStarted) {
            showWaiting();
        } else {
            showBoard();
        }
        ((CardLayout) mainScreen.mainPanel.getLayout()).show(mainScreen.mainPanel, "gameView");
    }

    private void newBoard() {
        int size = 7;
        this.board = new int[7][7];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if ((i < 2 || i > size - 3) && (j < 2 || j > size - 3)) {
                    this.board[i][j] = -1; // inválido
                } else {
                    this.board[i][j] = 1; // peça
                }
            }
        }
        this.board[3][3] = 0;  //peça branca

    }

    private JButton[][] setPieces() {
        JButton[][] pieces = new JButton[7][7];
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                pieces[i][j] = new JButton();
            }
        }
        return pieces;
    }

    public void updateButtons(JButton[][] buttons) {
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                if (board[i][j] == 0) {
                    buttons[i][j].setBackground(Color.black);
                } else if (board[i][j] == 1) {
                    buttons[i][j].setBackground(Color.blue);
                } else if (board[i][j] == -1) {
                    buttons[i][j].setEnabled(false);
                }
            }
        }
        buttons[6][6].setBackground(Color.GRAY);
        buttons[6][6].setLabel("DESISTIR");
        buttons[6][6].setEnabled(true);
    }

    public void makeAndSendMove(String data) {
        if (isValidMove(data)) {
            makeMove(data);
            if(isWinner()){
                sendResult("winner");
            }else {
                communication.sendMessage("game://" + data);
                showWaiting();
            }
        }
    }

    private boolean isWinner() {
        for(int i = 0; i < 7; i++) {
            for(int j = 0; j < 7; j++) {
                if(board[i][j] == 1) {
                    if (i >= 2 && board[i-1][j] == 1 && board[i-2][j] == 0) {
                        System.out.println("cima");
                        return false;
                    }
                    if (i <= 4 && board[i+1][j] == 1 && board[i+2][j] == 0) {
                        System.out.println("baixo");
                        return false;
                    }
                    if (j >= 2 && board[i][j-1] == 1 && board[i][j-2] == 0) {
                        System.out.println("esquerda");
                        return false;
                    }
                    if (j <= 4 && board[i][j+1] == 1 && board[i][j+2] == 0) {
                        System.out.println("direita");
                        return false;
                    }
                }
            }
        }
        return true;
    }


    public void receiveMove(String data) {
        makeMove(data);
        showBoard();
        updateButtons(pieces);
    }

    public void makeMove(String data) {
        String from = data.split("->")[0];
        String to = data.split("->")[1];
        int fromX = Integer.parseInt(from.split(",")[0]);
        int fromY = Integer.parseInt(from.split(",")[1]);
        int toX = Integer.parseInt(to.split(",")[0]);
        int toY = Integer.parseInt(to.split(",")[1]);

        board[fromX][fromY] = 0;

        board[toX][toY] = 1;

        int middleX = (fromX + toX) / 2;
        int middleY = (fromY + toY) / 2;
        board[middleX][middleY] = 0;
    }

    public boolean isValidMove(String data) {
        String from = data.split("->")[0];
        String to = data.split("->")[1];
        int fromX = Integer.parseInt(from.split(",")[0]);
        int fromY = Integer.parseInt(from.split(",")[1]);
        int toX = Integer.parseInt(to.split(",")[0]);
        int toY = Integer.parseInt(to.split(",")[1]);

        if (fromX < 0 || fromX >= board.length || fromY < 0 || fromY >= board[0].length || toX < 0 || toX >= board.length || toY < 0 || toY >= board[0].length) {
            System.out.println("Movimento fora do tabuleiro.");
            return false;
        }

        if (board[fromX][fromY] != 1) {
            System.out.println("Posição de origem inválida.");
            return false;
        }

        if (board[toX][toY] != 0) {
            System.out.println("Posição de destino ocupada.");
            return false;
        }

        if (fromX != toX && fromY != toY) {
            System.out.println("Movimento inválido.");
            return false;
        }

        if (fromY == toY && Math.abs(fromX - toX) != 2) {
            System.out.println("Movimento inválido.");
            return false;
        }

        if (fromX == toX && Math.abs(fromY - toY) != 2) {
            System.out.println("Movimento inválido.");
            return false;
        }

        return true;
    }

    public void sendResult(String data) {
        if (data == "loser") {
            this.result = data;
            communication.sendMessage("result://winner");
        } else if (data == "winner") {
            this.result = data;
            communication.sendMessage("result://loser");
        }
        ResultView resultView = new ResultView(this);
        mainScreen.mainPanel.add(resultView, "resultView");
        ((CardLayout) mainScreen.mainPanel.getLayout()).show(mainScreen.mainPanel, "resultView");


    }


    public void receiveResult(String data) {
        this.result = data;

        ResultView resultView = new ResultView(this);
        mainScreen.mainPanel.add(resultView, "resultView");
        ((CardLayout) mainScreen.mainPanel.getLayout()).show(mainScreen.mainPanel, "resultView");

    }
}
