package com.mycompany.app.StaticGameObjects;

import com.mycompany.app.GameLogic.Player;
import com.mycompany.app.GameLogic.Reward;
import com.mycompany.app.MapObjects.Map;
import com.mycompany.app.UserInterface.GamePanel;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.Assert.*;

/***
 * reward test state tests reward conditions and functions
 */
public class RewardTest {
    private Reward reward;
    private Map map = new Map("src/main/resources/level1.txt", 64);
    private Player player = new Player(map);
    private BufferedImage img = new BufferedImage(GamePanel.PANEL_WIDTH, GamePanel.PANEL_HEIGHT, BufferedImage.TYPE_INT_RGB);
    private Graphics2D g2 = (Graphics2D) img.getGraphics();

    @Before
    public void setUp() {
        reward = new Reward(map, 1);
    }

    @Test
    public void update() {
    }

    @Test
    public void collectReward() {
        reward.collectReward();
        assertTrue("Test not pass because the reward not been collected.",reward.isCollected());
    }

    @Test
    public void isPickedUp() {
        reward.collectReward();
        assertTrue("Test not pass because the reward not been collected.",reward.isCollected());
    }

    @Test
    public void respawn() {
        reward.respawn(1);
        assertFalse("Test not pass because the reward not been collected.",reward.isCollected());
    }

    @Test
    public void getX() {
        reward.setX(100);
        assertEquals("Test not pass because it not get the correct value of X",100, reward.getX());
    }

    @Test
    public void getY() {
        reward.setY(100);
        assertEquals("Test not pass because it not get the correct value of Y",100, reward.getY());
    }

    @Test
    public void setX() {
        reward.setX(100);
        assertEquals("Test not pass because it not get the correct value of X",100, reward.getX());
    }

    @Test
    public void setY() {
        reward.setY(100);
        assertEquals("Test not pass because it not get the correct value of Y",100, reward.getY());
    }

    @Test
    public void getValue() {
        assertEquals("Test not pass because it not get the correct number of value",1, reward.getValue());
    }
}

