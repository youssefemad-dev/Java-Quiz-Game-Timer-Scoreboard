/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quizgame;

/**
 *
 * @author LaLa.STORE
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomePanel extends JPanel {
    private JTextField nameField;
    private QuizGame mainFrame;

    public WelcomePanel(QuizGame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Enter your name:");
        nameField = new JTextField(20);
        JButton startButton = new JButton("Start Quiz");

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = nameField.getText().trim();
                if (!username.isEmpty()) {
                    mainFrame.showQuizPanel(username);
                }
            }
        });

        JPanel inputPanel = new JPanel();
        inputPanel.add(label);
        inputPanel.add(nameField);
        inputPanel.add(startButton);

        add(inputPanel, BorderLayout.CENTER);
    }

    public void reset() {
        nameField.setText("");
    }
}