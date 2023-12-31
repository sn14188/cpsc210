// Work cited: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
//             https://github.students.cs.ubc.ca/CPSC210/B02-SpaceInvadersBase.git

package ui;

import model.Score;
import model.ScoreRecord;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

// Represents avoid poop game GUI
public class AvoidPoopGameWindow extends JFrame {
    private AvoidPoopGame avoidPoopGame;
    private static ScoreRecord scoreRecord = new ScoreRecord("Avoid Poop Game Score Record");
    private GamePanel gamePanel;
    private ScorePanel scorePanel;
    private static JTextField textField = new JTextField();

    private Timer timer;
    private static final int INTERVAL = 50;

    public static void main(String[] args) {
        new AvoidPoopGameWindow();
    }

    // Constructs main window
    public AvoidPoopGameWindow() {
        avoidPoopGame = new AvoidPoopGame();
        avoidPoopGame.loadScoreRecord();

        JFrame frame = new JFrame("Avoid Poop Game");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));

        JButton startBtn = new JButton("START A GAME");
        JButton loadBtn = new JButton("LOAD THE SCORE RECORD");

        panel.add(startBtn);
        panel.add(loadBtn);

        startBtn.addActionListener(e -> createGame());
        startBtn.addActionListener(e -> displaySaveScore());
        loadBtn.addActionListener(e -> displayScoreRecord());

        frame.add(panel);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(300, 500));
        frame.setSize(300, 500);
        frame.setLocationRelativeTo(null);
    }

    public static ScoreRecord getScoreRecord() {
        return scoreRecord;
    }

    public static void setScoreRecord(ScoreRecord s) {
        scoreRecord = s;
    }

    // EFFECTS: starts a new game when start button is pressed
    private void createGame() {
        avoidPoopGame = new AvoidPoopGame();

        gamePanel = new GamePanel(avoidPoopGame);
        scorePanel = new ScorePanel(avoidPoopGame);

        add(gamePanel);
        add(scorePanel, BorderLayout.NORTH);

        addKeyListener(new KeyHandler());

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        addTimer();
    }

    // EFFECTS: accepts user's key input
    private class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            avoidPoopGame.keyEventHandling(e.getKeyCode());
        }
    }

    // EFFECTS: initializes a timer that updates game
    private void addTimer() {
        timer = new Timer(INTERVAL, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                avoidPoopGame.nextGameState();
                gamePanel.repaint();
                scorePanel.update();
                if (avoidPoopGame.isGameOver()) {
                    timer.stop();
                    dispose();
                }
            }
        });
        timer.start();
    }

    // EFFECTS: builds loading page frame
    //          retrieves the saved score record from json file
    public void displayScoreRecord() {
        JFrame frame = new JFrame("Score Record");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));

        JLabel label = new JLabel();
        avoidPoopGame.loadScoreRecord();
        List<Score> scores = scoreRecord.getScoreRecord();
        String i = "";
        for (Score s : scores) {
            i += s;
            i += "\n";
        }
        label.setText(i);

        JButton btn = new JButton("GO BACK");
        btn.addActionListener(e -> frame.dispose());

        panel.add(label, BorderLayout.CENTER);
        panel.add(btn);

        frame.add(panel);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(300, 500));
        frame.setSize(300, 500);
        frame.setLocationRelativeTo(null);
    }

    // EFFECTS: builds saving score page frame
    public void displaySaveScore() {
        JFrame frame = new JFrame("Save Score");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        JLabel label = new JLabel("<html>You've been pooped.<br/> Input your name:</html>", SwingConstants.CENTER);
        JButton btn = new JButton("SAVE");

        panel.add(label);
        panel.add(textField);
        panel.add(btn);

        //AvoidPoopGameWindow.getScoreRecord().addScore(new Score(playerName, avoidPoopGame.getTurnCount()));
        btn.addActionListener(e -> avoidPoopGame.addScoreWithName());

        frame.add(panel);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(300, 500));
        frame.setSize(300, 500);
        frame.setLocationRelativeTo(null);
    }

    public static JTextField getTextField() {
        return textField;
    }
}
