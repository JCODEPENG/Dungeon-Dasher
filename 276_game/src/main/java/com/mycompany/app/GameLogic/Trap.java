package com.mycompany.app.GameLogic;

import com.mycompany.app.MapObjects.Map;
import com.mycompany.app.UserInterface.Animation;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/***
 * The trap is similar to the the reward class but instead
 * of a having a reward that increments the players score
 * it decrements the players score and reduces the players life
 */

public class Trap {
    public static final int WIDTH = 32;
    public static final int HEIGHT = 32;

    private int xPos;
    private int yPos;

    private boolean pickedUp;

    private Animation animation;
    private BufferedImage[] idleSprites;

    private Map map;

    private int value;

    public Trap(Map map, int val) {
        this.map = map;
        pickedUp = false; // not picked up yet
        value = val;

        try {
            idleSprites = new BufferedImage[4];
            for (int i = 0; i < idleSprites.length; i++) {
                idleSprites[i] = ImageIO.read(new File("assets/trap/t" + i + ".png"));
            }
        } catch (Exception e) {
        }

        animation = new Animation();
    }

    public void update() {
        animation.setFrames(idleSprites);
        animation.setDelay(200);
        animation.update();
    }

    public void draw(Graphics2D g) {
        int mapX = map.getX();
        int mapY = map.getY();
        g.drawImage(animation.getImage(), mapX + xPos - WIDTH / 2, mapY + yPos - HEIGHT / 2, null);
    }

    public void collectTrap() {
        pickedUp = true;
    }

    public boolean isPickedUp() {
        return pickedUp;
    }

    public void respawn() {
        pickedUp = false;
    }

    public int getX() {
        return xPos;
    }

    public int getY() {
        return yPos;
    }

    public void setX(int x) {
        xPos = x;
    }

    public void setY(int y) {
        yPos = y;
    }

    public int getValue() {
        return value;
    }
}