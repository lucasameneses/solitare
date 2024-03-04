package org.example.View;

import org.example.Controller.GameController;

import javax.swing.*;
import java.awt.*;

public class ChatView extends JPanel {

    public ChatView(GameController gameController) {

        setLayout(new BorderLayout());

        Dimension minSize = new Dimension(300, 200);
        setMinimumSize(minSize);

        JTextArea chatTextArea = gameController.chatTextArea;
        chatTextArea.setEditable(false);
        chatTextArea.setLineWrap(true);
        chatTextArea.setAlignmentX(Component.RIGHT_ALIGNMENT);
        JScrollPane scrollChat = new JScrollPane(chatTextArea);
        add(scrollChat, BorderLayout.CENTER);


        JTextField campoTextoChat = new JTextField();
        campoTextoChat.addActionListener(e -> {
            gameController.sendMessage(campoTextoChat.getText());
            campoTextoChat.setText("");
        });
        add(campoTextoChat, BorderLayout.SOUTH);

        chatTextArea.append("Bem-vindos ao jogo!" + "\n");

    }

}
