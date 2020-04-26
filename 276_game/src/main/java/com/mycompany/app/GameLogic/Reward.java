package com.mycompany.app.GameLogic;

import com.mycompany.app.MapObjects.Map;
import com.mycompany.app.UserInterface.Animation;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;


/***
 * Reward class contains a value that if the player collects it
 * the players score will increment
 */
public class Reward {
    public static final int WIDTH = 32;
    public static final int HEIGHT = 32;

    private int xPos;
    private int yPos;

    private int value;
    private boolean pickedUp;

    private Map map;

    private Animation animation;
    private BufferedImage[] idleSprites;
    private BufferedImage openSprite;

    public Reward(Map map, int value) {
        this.map = map;
        this.value = value;
        pickedUp = false; // not picked up yet

        try {
            idleSprites = new BufferedImage[4];
            for (int i = 0; i < idleSprites.length; i++) {
                idleSprites[i] = ImageIO.read(new File("assets/item/c" + i + ".png"));
            }
            openSprite = ImageIO.read(new File("assets/item/chest_open_4.png"));

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
        int mapX = map.getX();
        int mapY = map.getY();

        if (!pickedUp)
            g.drawImage(animation.getImage(), mapX + xPos - WIDTH / 2, mapY + yPos - HEIGHT / 2, null);
        else
            g.drawImage(openSprite, mapX + xPos - WIDTH / 2, mapY + yPos - HEIGHT / 2, null);

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
