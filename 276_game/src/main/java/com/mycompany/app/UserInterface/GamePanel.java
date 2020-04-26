package com.mycompany.app.UserInterface;

import com.mycompany.app.UserInterface.GameStateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

/***
 * Gamepanel that puts the game within the frame
 * using Jpanel and sets up the frame where the game takes place
 * all game objects are placed on top of this class
 */

public class GamePanel extends JPanel implements Runnable, KeyListener {

    public static final int PANEL_WIDTH = 1032;
    public static final int PANEL_HEIGHT = 664;

    private Graphics2D g;
    private BufferedImage img;
    private GameStateManager manager;

    private int fps = 60;
    private int targetTime = 1000 / fps;

    private Thread gameThread;
    private boolean continuePlaying;

    public GamePanel() {
        super();
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setFocusable(true);
        requestFocus();
    }

    @Override
    public void addNotify() {
        super.addNotify();
        if (gameThread == null) {
            gameThread = new Thread(this);
            gameThread.start();
        }
        addKeyListener(this);
    }

    public void run() {
        continuePlaying = true;

        img = new BufferedImage(PANEL_WIDTH, PANEL_HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) img.getGraphics();
        manager = new GameStateManager();

        manager.setCurrentState(GameStateManager.MENUSTATE);

        long startTime;
        long elapsedTime;
        long waitTime;

        while (continuePlaying) {
            startTime = System.nanoTime();

            update();
            render();
            draw();

            elapsedTime = (System.nanoTime() - startTime) / 1000000; //divide by 10 ^ 6 to convert nano to milliseconds
            waitTime = targetTime - elapsedTime;

            try {
                gameThread.sleep(waitTime);
            } catch (Exception e) {
            }

        }
    }

    private void update() {
        manager.update();
    }

    private void render() {
        manager.draw(g);
    }

    private void draw() {
        Graphics g2 = this.getGraphics();
        g2.drawImage(img, 0, 0, PANEL_WIDTH, PANEL_HEIGHT, null);
        g2.dispose();
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        manager.keyPressed(key);

    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        manager.keyReleased(key);

    }

    public void keyTyped(KeyEvent e) {
    }

}
