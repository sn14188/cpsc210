package ui;

import model.Player;
import model.Poop;

import javax.swing.*;
import java.awt.*;

// Represents the game panel
public class GamePanel extends JPanel {
    private AvoidPoopGame avoidPoopGame;

    // Constructs a score panel
    // EFFECTS: sets up the white panel with game's size
    public GamePanel(AvoidPoopGame avoidPoopGame) {
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(AvoidPoopGame.WIDTH, AvoidPoopGame.HEIGHT));
        this.avoidPoopGame = avoidPoopGame;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGame(g);
        if (avoidPoopGame.isGameOver()) {
            drawGameOver(g);
        }
    }

    // MODIFIES: g
    // EFFECTS:  draws the game onto g
    private void drawGame(Graphics g) {
        drawPlayer(g);
        drawPoops(g);
    }

    // MODIFIES: g
    // EFFECTS:  draws the player onto g
    private void drawPlayer(Graphics g) {
        Player player = avoidPoopGame.getPlayer();
        g.setColor(Player.COLOR);
        g.fillRect(player.getPlayerX() - Player.PLAYER_SIZE_X / 2,
                player.getPlayerY() - Player.PLAYER_SIZE_Y / 2,
                Player.PLAYER_SIZE_X,
                Player.PLAYER_SIZE_Y);
    }

    // MODIFIES: g
    // EFFECTS: draws the poops
    private void drawPoops(Graphics g) {
        for (Poop next : avoidPoopGame.getPoops()) {
            drawPoop(g, next);
        }
    }

    // MODIFIES: g
    // EFFECTS: draws a poop
    private void drawPoop(Graphics g, Poop poop) {
        g.setColor(Poop.COLOR);
        g.fillRect(poop.getPoopX() - Poop.POOP_SIZE_X / 2,
                poop.getPoopY() - Poop.POOP_SIZE_Y / 2,
                Poop.POOP_SIZE_X,
                Poop.POOP_SIZE_Y);
    }

    // Draws the "game over" message and save instructions
    // MODIFIES: g
    // EFFECTS:  draws "game over" and save instructions onto g
    private void drawGameOver(Graphics g) {
        g.setColor(new Color(200, 0, 0));
        g.setFont(new Font("Arial", 20, 15));
        FontMetrics fm = g.getFontMetrics();
        centreString("Game over. You've been pooped.", g, fm, AvoidPoopGame.HEIGHT / 2 - 10);
        centreString("S to save score.", g, fm, AvoidPoopGame.HEIGHT / 2 + 10);
    }

    // MODIFIES: g
    // EFFECTS:  draws the message that requires name onto g
    private void drawSaveScore(Graphics g) {
        g.setColor(new Color(200, 0, 0));
        g.setFont(new Font("Arial", 20, 15));
        FontMetrics fm = g.getFontMetrics();
        centreString("Input your name.", g, fm, AvoidPoopGame.HEIGHT / 2);
    }

    // Centres a string on the screen
    // modifies: g
    // effects:  centres the string horizontally onto g at vertical position y
    private void centreString(String str, Graphics g, FontMetrics fm, int y) {
        int width = fm.stringWidth(str);
        g.drawString(str, (AvoidPoopGame.WIDTH - width) / 2, y);
    }
}

