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
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuizPanel extends JPanel {
    private List<Question> questions;
    private int currentQuestionIndex;
    private int score;
    private Timer timer;
    private JProgressBar progressBar;
    private JLabel questionLabel;
    private JPanel optionsPanel;
    private JButton submitButton;
    private String username;
    private QuizGame mainFrame;
    private Clip correctClip;
    private Clip incorrectClip;

    public QuizPanel(String username, QuizGame mainFrame) {
        this.username = username;
        this.mainFrame = mainFrame;
        initializeQuestions();
        currentQuestionIndex = 0;
        score = 0;

        setLayout(new BorderLayout());

        questionLabel = new JLabel("", SwingConstants.CENTER);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 24));
        optionsPanel = new JPanel(new GridLayout(0, 1));
        submitButton = new JButton("Submit Answer");
        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(100);
        progressBar.setStringPainted(true);

        loadSounds();

        // Create bottom panel to hold submit button and progress bar
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(submitButton, BorderLayout.WEST);
        bottomPanel.add(progressBar, BorderLayout.CENTER);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkAnswer();
            }
        });

        add(questionLabel, BorderLayout.NORTH);
        add(optionsPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        loadQuestion(currentQuestionIndex);
        startTimer();
    }

    private void loadSounds() {
        try {
            correctClip = AudioSystem.getClip();
            AudioInputStream ais = AudioSystem.getAudioInputStream(
                getClass().getResource("/sounds/correct.wav"));
            correctClip.open(ais);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            incorrectClip = AudioSystem.getClip();
            AudioInputStream ais = AudioSystem.getAudioInputStream(
                getClass().getResource("/sounds/incorrect.wav"));
            incorrectClip.open(ais);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initializeQuestions() {
        questions = new ArrayList<>();
        questions.add(new Question("What is 2+2?", 
            new String[]{"3", "4", "5"}, 1));
        questions.add(new Question("Capital of France?", 
            new String[]{"London", "Paris", "Berlin"}, 1));
        questions.add(new Question("Java is a...", 
            new String[]{"Language", "Country", "Drink"}, 0));
    }

    private void loadQuestion(int index) {
        Question q = questions.get(index);
        questionLabel.setText(q.getQuestionText());
        optionsPanel.removeAll();
        
        ButtonGroup group = new ButtonGroup();
        for (String option : q.getOptions()) {
            JRadioButton rb = new JRadioButton(option);
            rb.setFont(new Font("Arial", Font.PLAIN, 18));
            group.add(rb);
            optionsPanel.add(rb);
        }
        
        optionsPanel.revalidate();
        optionsPanel.repaint();
    }

    private void startTimer() {
        timer = new Timer(150, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int value = progressBar.getValue();
                if (value <= 0) {
                    timer.stop();
                    checkAnswer();
                } else {
                    progressBar.setValue(value - 1);
                }
            }
        });
        timer.start();
    }

    private void checkAnswer() {
        timer.stop();
        Question currentQuestion = questions.get(currentQuestionIndex);
        
        // Find selected answer
        int selected = -1;
        for (Component comp : optionsPanel.getComponents()) {
            JRadioButton rb = (JRadioButton) comp;
            if (rb.isSelected()) {
                selected = Arrays.asList(currentQuestion.getOptions())
                    .indexOf(rb.getText());
                break;
            }
        }

        // Check answer and update score
        if (selected == currentQuestion.getCorrectOptionIndex()) {
            score++;
            playSound(correctClip);
        } else {
            playSound(incorrectClip);
        }

        // Move to next question or show results
        currentQuestionIndex++;
        if (currentQuestionIndex < questions.size()) {
            loadQuestion(currentQuestionIndex);
            progressBar.setValue(100);
            timer.restart();
        } else {
            mainFrame.showResultsPanel(username, score);
        }
    }

    private void playSound(Clip clip) {
        if (clip != null) {
            clip.setFramePosition(0);
            clip.start();
        }
    }

    @Override
    public void removeNotify() {
        super.removeNotify();
        if (correctClip != null) correctClip.close();
        if (incorrectClip != null) incorrectClip.close();
    }
}