package com.mycompany.app.GameLogic;


import com.mycompany.app.MapObjects.Map;
import com.mycompany.app.UserInterface.Animation;
import com.mycompany.app.UserInterface.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.ArrayList;


/***
 * The player is the core object that interacts with all the other objects,
 * it is passed information about where the other objects are so when they collide
 * and interact the player will do an action. Ie. pick up items or lose a life when
 * hit an enemy
 */
public class Player {

    public static final int DEFAULT_LIVES = 3;
    public static final int WIDTH = 32;
    public static final int HEIGHT = 32;

    private int playerLives;
    private int xPos;
    private int yPos;
    private int xResp;
    private int yResp;
    private int dx;
    private int dy;
    private int velocity;
    private int xDest;
    private int yDest;

    private boolean up;
    private boolean down;
    private boolean left;
    private boolean right;

    private boolean topLeft;
    private boolean botLeft;
    private boolean topRight;
    private boolean botRight;
    private boolean atDoorUp;
    private boolean atDoorDown;
    private boolean atDoorLeft;
    private boolean atDoorRight;

    private Animation animation;
    private BufferedImage[] idleSprites;
    private boolean facingRight;

    private Map map;
    private Rectangle playerObject;
    private int score = 0;

    private ArrayList<Reward> inventory;

    public Player(Map map) {
        this.map = map;
        inventory = new ArrayList<Reward>();
        playerLives = DEFAULT_LIVES;
        velocity = 2;


        //getting sprites for the player
        try {
            idleSprites = new BufferedImage[4];
            for (int i = 0; i < idleSprites.length; i++) {
                idleSprites[i] = ImageIO.read(new File("assets/player/p" + i + ".png"));
            }
        } catch (Exception e) {
        }

        animation = new Animation();
        facingRight = true;
    }

    public void setUp(boolean b) {
        up = b;
    }

    public void setDown(boolean b) {
        down = b;
    }

    public void setLeft(boolean b) {
        left = b;
    }

    public void setRight(boolean b) {
        right = b;
    }

    public void update() {
        //setting the inital velocity first of which direction to go
        if (left) {
            dx = -velocity;
        }
        if (right) {
            dx = velocity;
        }
        if (up) {
            dy = -velocity;
        }
        if (down) {
            dy = velocity;
        }

        // for collision checks
        //this is it's future move
        xDest = xPos + dx;
        yDest = yPos + dy;

        // determine collisions for moving up and down
        //checks if the future spot is on top of any other
        //objects and will prevent player from walking through it
        calculateBounds(xPos, yDest);
        if (dy < 0) {
            if (topLeft || topRight || atDoorUp) {
                dy = 0;
            }
        }
        if (dy > 0) {
            if (botLeft || botRight || atDoorDown) {
                dy = 0;
            }
        }

        // determine collisions for moving left and right
        calculateBounds(xDest, yPos);
        if (dx < 0) {
            if (topLeft || botLeft || atDoorLeft) {
                dx = 0;
            }
        }
        if (dx > 0) {
            if (topRight || botRight || atDoorRight) {
                dx = 0;
            }
        }

        xPos += dx;
        yPos += dy;


        //Map scrolls with center of player
        map.setX(GamePanel.PANEL_WIDTH / 2 - xPos);
        map.setY(GamePanel.PANEL_HEIGHT / 2 - yPos);

        //controlling player sprite
        animation.setFrames(idleSprites);
        animation.setDelay(100);
        animation.update();

        if (dx < 0) {
            facingRight = false;
        }

        if (dx > 0) {
            facingRight = true;
        }

        dx = 0;
        dy = 0;
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

        playerObject = new Rectangle(mapX + xPos - WIDTH / 2, mapY + yPos - HEIGHT / 2, WIDTH, HEIGHT);

        if (facingRight) {
            g2.drawImage(animation.getImage(), mapX + xPos - WIDTH / 2, mapY + yPos - HEIGHT / 2, null);
        } else {
            g2.drawImage(animation.getImage(), mapX + xPos - WIDTH / 2 + WIDTH, mapY + yPos - HEIGHT / 2, -WIDTH, HEIGHT, null);
        }

        g2.setColor(Color.RED);
        g2.setFont(new Font("arial", Font.BOLD, 15));
        g2.drawString("LIVES: " + playerLives, GamePanel.PANEL_WIDTH - 75, 25);
        g2.drawString("SCORE: " + score, 25, 25);
    }

    public void setX(int x) {
        xPos = x;
    }

    public void setY(int y) {
        yPos = y;
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

    public Rectangle getPlayerObject() {
        return playerObject;
    }

    public void decreaseLife() {
        playerLives--;
    }

    public boolean playerDead() {

        if (playerLives < 1) {
            playerLives = DEFAULT_LIVES;
            return true;
        }
        return false;
    }

    public void respawn() {
        xPos = xResp;
        yPos = yResp;
    }


    public boolean isMoving() {
        if (up || down || left || right) {
            return true;
        }
        return false;
    }

    public void addScore(int value) {
        score += value;
    }

    public void decreaseScore(int value) {
        score -= value;
    }

    public void checkReward(Reward reward) {
        // collect from under
        if (reward.getY() + reward.HEIGHT == yPos && !reward.isCollected()
                && xPos > reward.getX() - reward.WIDTH / 2 - WIDTH / 2
                && xPos < reward.getX() + reward.WIDTH / 2 + WIDTH / 2) {
            addScore(reward.getValue());
            reward.collectReward();
            inventory.add(reward);
        }

        // collect from above
        if (reward.getY() == yPos + HEIGHT - reward.HEIGHT / 2 && !reward.isCollected()
                && xPos > reward.getX() - reward.WIDTH / 2 - WIDTH / 2
                && xPos < reward.getX() + reward.WIDTH / 2 + WIDTH / 2) {
            addScore(reward.getValue());
            reward.collectReward();
            inventory.add(reward);
        }

        // collect from right
        if (xPos == reward.getX() - reward.WIDTH / 2 - WIDTH / 2 && !reward.isCollected()
                && reward.getY() < yPos + HEIGHT - reward.HEIGHT / 2
                && reward.getY() + reward.HEIGHT > yPos) {
            addScore(reward.getValue());
            reward.collectReward();
            inventory.add(reward);
        }

        // collect from left
        if (xPos == reward.getX() + reward.WIDTH / 2 + WIDTH / 2 && !reward.isCollected()
                && reward.getY() < yPos + HEIGHT - reward.HEIGHT / 2
                && reward.getY() + reward.HEIGHT > yPos) {
            addScore(reward.getValue());
            reward.collectReward();
            inventory.add(reward);
        }
    }

    public void checkBonus(BonusReward reward) {
        // collect from under
        if (reward.getY() + reward.HEIGHT == yPos && !reward.isCollected()
                && xPos > reward.getX() - reward.WIDTH / 2 - WIDTH / 2
                && xPos < reward.getX() + reward.WIDTH / 2 + WIDTH / 2) {
            addScore(reward.getValue());
            reward.collectReward();
            xResp = reward.getX();
            yResp = reward.getY(); // changes respawn points
        }

        // collect from above
        if (reward.getY() == yPos + HEIGHT - reward.HEIGHT / 2 && !reward.isCollected()
                && xPos > reward.getX() - reward.WIDTH / 2 - WIDTH / 2
                && xPos < reward.getX() + reward.WIDTH / 2 + WIDTH / 2) {
            addScore(reward.getValue());
            reward.collectReward();
            xResp = reward.getX();
            yResp = reward.getY(); // changes respawn points
        }

        // collect from right
        if (xPos == reward.getX() - reward.WIDTH / 2 - WIDTH / 2 && !reward.isCollected()
                && reward.getY() < yPos + HEIGHT - reward.HEIGHT / 2
                && reward.getY() + reward.HEIGHT > yPos) {
            addScore(reward.getValue());
            reward.collectReward();
            xResp = reward.getX();
            yResp = reward.getY(); // changes respawn points
        }

        // collect from left
        if (xPos == reward.getX() + reward.WIDTH / 2 + WIDTH / 2 && !reward.isCollected()
                && reward.getY() < yPos + HEIGHT - reward.HEIGHT / 2
                && reward.getY() + reward.HEIGHT > yPos) {
            addScore(reward.getValue());
            reward.collectReward();
            xResp = reward.getX();
            yResp = reward.getY(); // changes respawn points
        }
    }

    public void checkTrap(Trap trap) {
        // collect from under
        if (trap.getY() + trap.HEIGHT == yPos && !trap.isPickedUp()
                && xPos > trap.getX() - trap.WIDTH / 2 - WIDTH / 2
                && xPos < trap.getX() + trap.WIDTH / 2 + WIDTH / 2) {
            trap.collectTrap();
            decreaseScore(trap.getValue());
            decreaseLife();
        }

        // collect from above
        if (trap.getY() == yPos + HEIGHT - trap.HEIGHT / 2 && !trap.isPickedUp()
                && xPos > trap.getX() - trap.WIDTH / 2 - WIDTH / 2
                && xPos < trap.getX() + trap.WIDTH / 2 + WIDTH / 2) {
            trap.collectTrap();
            decreaseScore(trap.getValue());
            decreaseLife();
        }

        // collect from right
        if (xPos == trap.getX() - trap.WIDTH / 2 - WIDTH / 2 && !trap.isPickedUp()
                && trap.getY() < yPos + HEIGHT - trap.HEIGHT / 2
                && trap.getY() + trap.HEIGHT > yPos) {
            trap.collectTrap();
            decreaseScore(trap.getValue());
            decreaseLife();
        }

        // collect from left
        if (xPos == trap.getX() + trap.WIDTH / 2 + WIDTH / 2 && !trap.isPickedUp()
                && trap.getY() < yPos + HEIGHT - trap.HEIGHT / 2
                && trap.getY() + trap.HEIGHT > yPos) {
            trap.collectTrap();
            decreaseScore(trap.getValue());
            decreaseLife();
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
        if (door.getY() == yPos + HEIGHT && door.isClosed()
                && xPos > door.getX() - door.WIDTH / 2 - WIDTH / 2
                && xPos < door.getX() + door.WIDTH / 2 + WIDTH / 2) {
            atDoorDown = true;
            return true;
        }

        // enter from right
        if (xPos == door.getX() - door.WIDTH / 2 - WIDTH / 2 && door.isClosed()
                && door.getY() < yPos + HEIGHT
                && door.getY() + door.HEIGHT > yPos) {
            atDoorRight = true;
            return true;
        }

        // enter from left
        if (xPos == door.getX() + door.WIDTH / 2 + WIDTH / 2 && door.isClosed()
                && door.getY() < yPos + HEIGHT
                && door.getY() + door.HEIGHT > yPos) {
            atDoorLeft = true;
            return true;
        }
        atDoorUp = false;
        atDoorDown = false;
        atDoorLeft = false;
        atDoorRight = false;
        return false;
    }

    public boolean CheckGameWon(ExitPoint point) {
        boolean stopGame = false;
        // collect from under
        if (point.getY() + point.HEIGHT == yPos
                && xPos > point.getX() - point.WIDTH / 2 - WIDTH / 2
                && xPos < point.getX() + point.WIDTH / 2 + WIDTH / 2) {
            xResp = point.getX();
            yResp = point.getY(); // changes respawn points
            stopGame = true;
        }

        // collect from above
        if (point.getY() == yPos + HEIGHT - point.HEIGHT / 2
                && xPos > point.getX() - point.WIDTH / 2 - WIDTH / 2
                && xPos < point.getX() + point.WIDTH / 2 + WIDTH / 2) {
            xResp = point.getX();
            yResp = point.getY();
            stopGame = true;
        }

        // collect from right
        if (xPos == point.getX() - point.WIDTH / 2 - WIDTH / 2
                && point.getY() < yPos + HEIGHT - point.HEIGHT / 2
                && point.getY() + point.HEIGHT > yPos) {
            xResp = point.getX();
            yResp = point.getY();
            stopGame = true;
        }

        // collect from left
        if (xPos == point.getX() + point.WIDTH / 2 + WIDTH / 2
                && point.getY() < yPos + HEIGHT - point.HEIGHT / 2
                && point.getY() + point.HEIGHT > yPos) {
            xResp = point.getX();
            yResp = point.getY();
            stopGame = true;
        }
        return stopGame;
    }

    public int numOfRewardsCollected() {
        return inventory.size();
    }

    public int getScore() {
        return score;
    }

    public void resetScore() {
        score = 0;
    }

    public void resetLives(int lives) {
        playerLives = lives;
    }

    public void resetInventory() {
        inventory.clear();
    }

    public int getPlayerLives() {
        return playerLives;
    }
}
