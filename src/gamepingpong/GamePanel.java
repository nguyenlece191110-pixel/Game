/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamepingpong;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;


public class GamePanel extends JPanel implements ActionListener, KeyListener {

    Ball ball;
    Paddle redPaddle, greenPaddle;
    Score score;
    Timer timer;
    boolean gameRunning = true;

    BufferedImage redImg, blueImg, ballImg, logoImg;

    long totalElapsedTime = 0;
    long startTime = 0;
    String lastWinner = "";

    public GamePanel() {
        setPreferredSize(new Dimension(640, 480));
        setBackground(Color.WHITE);
        loadImages();
        initGame();
        addKeyListener(this);
        setFocusable(true);
        timer = new Timer(10, this);
        timer.start();
    }

    public void loadImages() {
        try {
            redImg = ImageIO.read(new File("images/red.jpg"));
            blueImg = ImageIO.read(new File("images/blue.jpg"));
            ballImg = ImageIO.read(new File("images/ball.jpg"));
            logoImg = ImageIO.read(new File("images/logo.png"));
        } catch (Exception e) {
            System.out.println("Lỗi load ảnh: " + e.getMessage());
        }
    }

    public void initGame() {
        if (score == null) {
            score = new Score();
        }

        ball = new Ball(230, 305, ballImg);
        redPaddle = new Paddle(20, 190, redImg);
        greenPaddle = new Paddle(590, 190, blueImg);

        startTime = System.currentTimeMillis();
    }

    public String getElapsedTime() {
        long now = System.currentTimeMillis();
        long runningTime = gameRunning ? (now - startTime) : 0;
        long elapsed = totalElapsedTime + runningTime;

        long minutes = elapsed / 1000 / 60;
        long seconds = (elapsed / 1000) % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(new Color(220, 255, 210));
        g.fillRect(0, 0, 640, 40);

        if (logoImg != null) {
            g.drawImage(logoImg, 90, 5, 140, 30, null);
            g.drawImage(logoImg, 400, 5, 140, 30, null);
        }

        g.setFont(new Font("Arial", Font.BOLD, 14));

        g.setColor(new Color(255, 100, 100));
        g.drawString("Nguyen: " + score.red, 10, 25);

        g.setColor(new Color(100, 150, 255));
        g.drawString("Thai: " + score.green, 585, 25);

        g.setColor(Color.MAGENTA.darker());
        g.drawString("Time: " + getElapsedTime(), 280, 25);

        if (ball != null) {
            ball.draw(g);
        }
        if (redPaddle != null) {
            redPaddle.draw(g);
        }
        if (greenPaddle != null) {
            greenPaddle.draw(g);
        }

        if (!gameRunning) {
            g.setColor(Color.GREEN.darker());
            g.setFont(new Font("Arial", Font.BOLD, 15));
            g.drawString(lastWinner + " wins! > < Press SPACE to play again.", 200, 60);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameRunning) {
            ball.move();
            checkCollision();
        }
        repaint();
    }

    public void checkCollision() {
        if (ball.getBounds().intersects(redPaddle.getBounds())
                || ball.getBounds().intersects(greenPaddle.getBounds())) {
            ball.xVelocity = -ball.xVelocity;
        }

        if (ball.x <= 0) {
            score.green++;
            lastWinner = "Thai";
            stopGame();
        } else if (ball.x >= 620) {
            score.red++;
            lastWinner = "Nguyen";
            stopGame();
        }
    }

    public void stopGame() {
        gameRunning = false;
        totalElapsedTime += System.currentTimeMillis() - startTime;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            redPaddle.moveUp();
        }
        if (code == KeyEvent.VK_S) {
            redPaddle.moveDown();
        }
        if (code == KeyEvent.VK_UP) {
            greenPaddle.moveUp();
        }
        if (code == KeyEvent.VK_DOWN) {
            greenPaddle.moveDown();
        }

        if (code == KeyEvent.VK_SPACE && !gameRunning) {
            gameRunning = true;
            initGame();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
