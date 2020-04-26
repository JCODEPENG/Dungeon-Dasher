package com.mycompany.app.GameLogic;

import com.mycompany.app.MapObjects.Map;
import com.mycompany.app.UserInterface.Animation;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;


/***
 * Bonus Reward is the same as a reward
 * but is just a seperate object to store
 * a reward to give more points
 */
public class BonusReward {
    public static final int WIDTH = 32;
    public static final int HEIGHT = 32;

    private int xPos;
    private int yPos;

    int value;
    boolean pickedUp;

    private Map map;

    private Animation animation;
    private BufferedImage[] idleSprites;

    public BonusReward(Map map, int value) {
        this.map = map;
        this.value = value;
        pickedUp = false;

        try {
            idleSprites = new BufferedImage[4];
            for (int i = 0; i < idleSprites.length; i++) {
                idleSprites[i] = ImageIO.read(new File("assets/item2/i" + i + ".png"));
            }

        } catch (Exception e) {
        }

        animation = new Animation();
    }

    public void update() {
        if (!pickedUp) {
            animation.setFrames(idleSprites);
            animation.setDelay(200);
            animation.update();
        }
    }

    public void draw(Graphics2D g) {
        animation.setFrames(idleSprites);
        animation.setDelay(200);
        animation.update();
        if (!pickedUp) {

            int mapX = map.getX();
            int mapY = map.getY();
            g.drawImage(animation.getImage(), mapX + xPos - WIDTH / 2, mapY + yPos - HEIGHT / 2, null);
        }
    }

    public void collectReward() {
        pickedUp = true;
        value = 0;
    }

    public boolean isCollected() {
        return pickedUp;
    }

    public void respawn(int value) {
        pickedUp = false;
        this.value = value;
    }

    public int getValue() {
        return value;
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
}