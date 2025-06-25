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

public class QuizGame extends JFrame {
    private CardLayout cardLayout;
    private JPanel contentPane;
    private WelcomePanel welcomePanel;

    public QuizGame() {
        cardLayout = new CardLayout();
        contentPane = new JPanel(cardLayout);
        add(contentPane);

        welcomePanel = new WelcomePanel(this);
        contentPane.add(welcomePanel, "Welcome");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setVisible(true);
    }

    public void showQuizPanel(String username) {
        QuizPanel quizPanel = new QuizPanel(username, this);
        contentPane.add(quizPanel, "Quiz");
        cardLayout.show(contentPane, "Quiz");
        contentPane.revalidate();
        contentPane.repaint();
    }

    public void showResultsPanel(String username, int score) {
        ResultsPanel resultsPanel = new ResultsPanel(username, score, this);
        contentPane.add(resultsPanel, "Results");
        cardLayout.show(contentPane, "Results");
        contentPane.revalidate();
        contentPane.repaint();
    }

    public void showWelcomePanel() {
        welcomePanel.reset();
        cardLayout.show(contentPane, "Welcome");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(QuizGame::new);
    }
}