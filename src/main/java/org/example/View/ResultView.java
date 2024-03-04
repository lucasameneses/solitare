package org.example.View;

import org.example.Controller.GameController;

import javax.swing.*;
import java.awt.*;

import java.util.Objects;

public class ResultView extends JPanel {

    public ResultView(GameController gameController) {

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(200, 0, 0, 0));

        JLabel title = new JLabel();
        if(Objects.equals(gameController.result, "winner")){
            title.setText("VITÓRIA");
            title.setForeground(Color.GREEN);
        }else if (Objects.equals(gameController.result, "loser")){
            title.setText("DERROTA");
            title.setForeground(Color.RED);
        }

        title.setFont(new Font("Arial", Font.BOLD, 34));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setVerticalAlignment(SwingConstants.CENTER);
        add(title, BorderLayout.NORTH);

        //TODO: WIP

//        JPanel buttonPanel = new JPanel();
//        buttonPanel.setLayout(new FlowLayout());
//        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
//
//        JButton serverButton = new JButton("Revanche");
//        serverButton.setPreferredSize(new Dimension(100, 30));
//        serverButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//            }
//        });
//        buttonPanel.add(serverButton);
//
//
//        JButton clientButton = new JButton("Sair");
//        clientButton.setPreferredSize(new Dimension(100, 30));
//        clientButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//            }
//        });
//
//
//        buttonPanel.add(clientButton);
//        add(buttonPanel, BorderLayout.CENTER);
    }

}
