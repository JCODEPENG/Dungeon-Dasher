package com.mycompany.app.UserInterface;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * how to play class give player the explanation to how to play the game
 * how to control player and get the score
 */

public class HowToPlayState extends GameState {

    private String[] menuItems = {"Play", "Quit"};
    private int selection = 0;
    private BufferedImage img;

    public HowToPlayState(GameStateManager gm) {
        this.gm = gm;
        try {
            img = ImageIO.read(new File("assets/tileset.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {

    }

    public void draw(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, GamePanel.PANEL_WIDTH, GamePanel.PANEL_HEIGHT);
        Image tmp = img.getScaledInstance(GamePanel.PANEL_WIDTH, GamePanel.PANEL_HEIGHT, Image.SCALE_SMOOTH);
        //BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        //Graphics2D g2d = resized.createGraphics();
        g.drawImage(tmp, 0, 0, null);
        g.setColor(Color.WHITE);
        g.setFont(new Font("arial", Font.BOLD, 30));
        g.drawString("Press the up down left right key to Move", 150, 50);
        g.drawString("Press E to open the door", 150, 80);
        g.drawString("Walk on to the reward to collect points", 150, 110);
        g.drawString("Walk on a bonus reward for more points and a checkpoint", 150, 140);
        g.drawString("Try to avoid RED enemy, it will lose your one life", 150, 170);
        g.drawString("You have total 3 lives, go for it!", 150, 200);

        for (int i = 0; i < menuItems.length; i++) {
            if (i == selection)
                g.setColor(Color.RED);
            else
                g.setColor(Color.WHITE);

            g.drawString(menuItems[i], 350, 275 + (i * 75));
        }
    }

    public void keyPressed(int key) {
        if (key == KeyEvent.VK_ENTER)
            selectItem();

        if (key == KeyEvent.VK_DOWN) {
            selection++;
            if (selection == menuItems.length)
                selection = 0;
        }

        if (key == KeyEvent.VK_UP) {
            selection--;
            if (selection < 0)
                selection = menuItems.length - 1;
        }
    }

    public void selectItem() {
        switch (selection) {
            case 0:
                gm.setCurrentState(gm.LevelSelect);
                break;
            case 1:
                System.exit(0);
                break;
            default:
                break;
        }
    }

    public void keyReleased(int key) {
    }
}