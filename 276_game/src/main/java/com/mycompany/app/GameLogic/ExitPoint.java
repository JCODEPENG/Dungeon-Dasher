package com.mycompany.app.GameLogic;

import com.mycompany.app.MapObjects.Map;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/***
 * Exitpoint is an object that contains a switch statement
 * to be active or not. And when its active and the player interacts
 * with it the game will end
 */

public class ExitPoint {
    public static final int WIDTH = 20;
    public static final int HEIGHT = 20;

    private int xPos;
    private int yPos;

    private boolean setActive;

    private Map map;

    private BufferedImage closedSprite;
    private BufferedImage openSprite;

    public ExitPoint(Map map) {
        this.map = map;
        setActive = false; // not picked up yet

        try {
            closedSprite = ImageIO.read(new File("assets/exit/closed.png"));
            openSprite = ImageIO.read(new File("assets/exit/open.gif"));
        } catch (Exception e) {
        }

    }

    public void draw(Graphics2D g) {
        int mapX = map.getX();
        int mapY = map.getY();

        if (setActive) {
            g.drawImage(openSprite, mapX + xPos - WIDTH / 2, mapY + yPos - HEIGHT / 2, null);
        } else
            g.drawImage(closedSprite, mapX + xPos - WIDTH / 2, mapY + yPos - HEIGHT / 2, null);
    }

    public void setExitPoint() {
        setActive = true;
    }

    public void respawn() {
        setActive = false;
    }

    public int getX() {
        return xPos;
    }

    public int getY() {
        return yPos;
    }

    public boolean getActive() {
        return setActive;
    }

    public void setX(int x) {
        xPos = x;
    }

    public void setY(int y) {
        yPos = y;
    }

    public BufferedImage getClosedSprite() {
        return closedSprite;
    }

    public BufferedImage getOpenSprite() {
        return openSprite;
    }
}