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

public class ResultsPanel extends JPanel {
    private JLabel scoreLabel;
    private int finalScore;
    private Timer animationTimer;
    private int currentScore;
    private QuizGame mainFrame;

    public ResultsPanel(String username, int score, QuizGame mainFrame) {
        this.finalScore = score;
        this.currentScore = 0;
        this.mainFrame = mainFrame;

        setLayout(new BorderLayout());

        JLabel congratsLabel = new JLabel("Congratulations, " + username + "!", SwingConstants.CENTER);
        congratsLabel.setFont(new Font("Arial", Font.BOLD, 24));
        scoreLabel = new JLabel("Your score: 0", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 36));
        JButton tryAgainButton = new JButton("Try Again");

        tryAgainButton.addActionListener(e -> mainFrame.showWelcomePanel());

        add(congratsLabel, BorderLayout.NORTH);
        add(scoreLabel, BorderLayout.CENTER);
        add(tryAgainButton, BorderLayout.SOUTH);

        startAnimation();
    }

    private void startAnimation() {
        final int ANIMATION_DURATION = 2000; // 2 seconds
        final int STEPS = Math.min(finalScore, 100); // Adjust steps for smoothness
        final int DELAY = ANIMATION_DURATION / STEPS;

        animationTimer = new Timer(DELAY, e -> {
            if (currentScore < finalScore) {
                currentScore = Math.min(currentScore + (int) Math.ceil(finalScore / (double) STEPS), finalScore);
                scoreLabel.setText("Your score: " + currentScore);
            } else {
                animationTimer.stop();
            }
        });
        animationTimer.start();
    }
}