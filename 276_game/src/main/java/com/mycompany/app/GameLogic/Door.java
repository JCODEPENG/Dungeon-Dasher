package com.mycompany.app.GameLogic;

import com.mycompany.app.MapObjects.Map;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

/**
 * Door acts similar to the rewards and walls
 * such that it stores the coordinates for the player to know
 * where it is and not to walk through it
 * also stores if the door will be a trap door or not
 */

public class Door {
    public static final int WIDTH = 64;
    public static final int HEIGHT = 32;

    private Map map;
    private boolean closed;
    private boolean trapDoor;
    private boolean bonusDoor;
    private int xPos;
    private int yPos;
    private int id = 0;

    private BufferedImage image;

    public Door(Map map) {
        closed = true; // closed
        this.map = map;

        try {
            image = ImageIO.read(new File("assets/door1.png"));
        } catch (Exception e) {
        }
    }

    //used for bonus rewards to determine
    public Door(Map map, int id) {
        this.id = id;
        closed = true; // closed
        this.map = map;

        try {
            image = ImageIO.read(new File("assets/door1.png"));
        } catch (Exception e) {
        }
    }

    public void useDoor() {
        if (closed)
            closed = false;
        else
            closed = true;
    }

    public void setTrap(boolean isCondition) {
        trapDoor = isCondition;
    }

    public boolean hasTrap() {
        return trapDoor;
    }

    public void setbonusReward(boolean isCondition) {
        bonusDoor = isCondition;
    }

    public boolean hasBonus() {
        return bonusDoor;
    }

    public void respawnDoor() {
        closed = true;
    }

    public boolean isClosed() {
        return closed;
    }

    public void draw(Graphics2D g) {
        int mapX = map.getX();
        int mapY = map.getY();
        if (closed) {
            g.drawImage(image, mapX + xPos - WIDTH / 2, mapY + yPos - HEIGHT / 2, null);
        }
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

    public int getDoorID() {
        return this.id;
    }
}
