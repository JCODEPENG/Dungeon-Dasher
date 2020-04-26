package com.mycompany.app.GameLogic;

import com.mycompany.app.MapObjects.Map;
import com.mycompany.app.UserInterface.Animation;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.Math;

/***
 * Enemy class that takes in a player to search for and a map to determine
 * where the enemy will be displayed
 * similar to the player class
 */
public class Enemy {
    public static final int WIDTH = 32;
    public static final int HEIGHT = 32;

    private int xPos;
    private int yPos;
    private int xResp;
    private int yResp;
    private int xDest;
    private int yDest;
    private int directionX;
    private int directionY;

    private boolean topLeft;
    private boolean botLeft;
    private boolean topRight;
    private boolean botRight;
    private boolean atDoorUp;
    private boolean atDoorDown;
    private boolean atDoorLeft;
    private boolean atDoorRight;

    private boolean alive = false;

    private Animation animation;
    private BufferedImage[] idleSprites;
    private boolean facingRight;

    private Map map;
    private Player player;
    private Rectangle enemyObject;
    private int value;

    public Enemy(Map map, Player player, int value) {
        this.map = map;
        this.player = player;
        this.value = value;
        alive = true;

        try {
            idleSprites = new BufferedImage[4];
            for (int i = 0; i < idleSprites.length; i++) {
                idleSprites[i] = ImageIO.read(new File("assets/enemy/e" + i + ".png"));
            }
        } catch (Exception e) {
        }

        animation = new Animation();
        facingRight = true;
    }

    public void setX(int x) {
        xPos = x;
        xResp = x;
    }

    public void setY(int y) {
        yPos = y;
        yResp = y;
    }

    public void setXResp(int x) {
        xResp = x;
    }

    public void setYResp(int y) {
        yResp = y;
    }

    public int getX() {
        return xPos;

    }

    public int getY() {
        return yPos;

    }

    public int getXResp() {
        return xResp;

    }

    public int getYResp() {
        return yResp;

    }

    public BufferedImage[] getImage() {
        return idleSprites;
    }

    public Rectangle getEnemyObject() {
        return enemyObject;
    }

    public void update() {

        if (player.isMoving()) {

            directionX = (int) Math.signum(player.getX() - xPos);
            directionY = (int) Math.signum(player.getY() - yPos);


            xDest = xPos + directionX;
            yDest = yPos + directionY;

            // determine collisions for moving up and down
            calculateBounds(xPos, yDest);
            if (directionY < 0) {
                if (topLeft || topRight || atDoorUp) {
                    directionY = 0;
                }
            }
            if (directionY > 0) {
                if (botLeft || botRight || atDoorDown) {
                    directionY = 0;
                }
            }

            // determine collisions for moving left and right
            calculateBounds(xDest, yPos);
            if (directionX < 0) {
                if (topLeft || botLeft || atDoorLeft) {
                    directionX = 0;
                }
            }
            if (directionX > 0) {
                if (topRight || botRight || atDoorRight) {
                    directionX = 0;
                }
            }

            enemyHitPlayer(player);

            xPos += directionX;
            yPos += directionY;

            if (directionX < 0) {
                facingRight = false;
            }

            if (directionX > 0) {
                facingRight = true;
            }

            directionX = 0;
            directionX = 0;
        }

        animation.setFrames(idleSprites);
        animation.setDelay(100);
        animation.update();
    }

    private void calculateBounds(int x, int y) {
        int leftTile = map.getYTile(x - WIDTH / 2);
        int rightTile = map.getYTile((x + WIDTH / 2) - 1);
        int topTile = map.getXTile(y - HEIGHT / 2);
        int botTile = map.getXTile((y + HEIGHT / 2) - 1);

        topLeft = map.isBlocked(topTile, leftTile);
        botLeft = map.isBlocked(botTile, leftTile);
        topRight = map.isBlocked(topTile, rightTile);
        botRight = map.isBlocked(botTile, rightTile);
    }

    // draws the player relative to the map
    public void draw(Graphics2D g2) {
        animation.setFrames(idleSprites);
        animation.setDelay(100);
        animation.update();
        int mapX = map.getX();
        int mapY = map.getY();

        enemyObject = new Rectangle(mapX + xPos - WIDTH / 2, mapY + yPos - HEIGHT / 2, WIDTH, HEIGHT);

        if (facingRight && alive) {
            g2.drawImage(animation.getImage(), mapX + xPos - WIDTH / 2, mapY + yPos - HEIGHT / 2, null);
        } else {
            g2.drawImage(animation.getImage(), mapX + xPos - WIDTH / 2 + WIDTH, mapY + yPos - HEIGHT / 2, -WIDTH, HEIGHT, null);
        }
    }


    public void respawn() {
        xPos = xResp;
        yPos = yResp;
    }

    public void enemyHitPlayer(Player player) {
        try {
            Rectangle playerObject = player.getPlayerObject();
            if (playerObject.intersects(enemyObject)) {
                player.decreaseLife();
                player.decreaseScore(value);
                player.respawn();
                respawn();
            }
        } catch (Exception e) {
        }
    }

    public boolean checkDoor(Door door) {
        // enter from below
        if (door.getY() + door.HEIGHT == yPos && door.isClosed()
                && xPos > door.getX() - door.WIDTH / 2 - WIDTH / 2
                && xPos < door.getX() + door.WIDTH / 2 + WIDTH / 2) {
            atDoorUp = true;
            return true;
        }


        // enter from above
        else if (door.getY() == yPos + HEIGHT && door.isClosed()
                && xPos > door.getX() - door.WIDTH / 2 - WIDTH / 2
                && xPos < door.getX() + door.WIDTH / 2 + WIDTH / 2) {
            atDoorDown = true;
            return true;
        }

        // enter from right
        else if (xPos == door.getX() - door.WIDTH / 2 - WIDTH / 2 && door.isClosed()
                && door.getY() < yPos + HEIGHT
                && door.getY() + door.HEIGHT > yPos) {
            atDoorRight = true;
            return true;
        }

        // enter from left
        else if (xPos == door.getX() + door.WIDTH / 2 + WIDTH / 2 && door.isClosed()
                && door.getY() < yPos + HEIGHT
                && door.getY() + door.HEIGHT > yPos) {
            atDoorLeft = true;
            return true;
        } else {
            atDoorUp = false;
            atDoorDown = false;
            atDoorLeft = false;
            atDoorRight = false;
            return false;
        }
    }
}
