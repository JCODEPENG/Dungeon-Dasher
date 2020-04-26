package com.mycompany.app.StaticGameObjects;

import com.mycompany.app.GameLogic.BonusReward;
import com.mycompany.app.GameLogic.Player;
import com.mycompany.app.MapObjects.Map;
import com.mycompany.app.UserInterface.GamePanel;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.Assert.*;

/***
 * Bonus reward test state tests bonus reward conditions and functions
 */
public class BonusRewardTest {
    private BonusReward bonusReward;
    private Map map = new Map("src/main/resources/level1.txt", 64);
    private Player player = new Player(map);
    private BufferedImage img = new BufferedImage(GamePanel.PANEL_WIDTH, GamePanel.PANEL_HEIGHT, BufferedImage.TYPE_INT_RGB);
    private Graphics2D g2 = (Graphics2D) img.getGraphics();

    @Before
    public void setUp() {
        bonusReward = new BonusReward(map, 1);
    }

    @Test
    public void update() {
    }

    @Test
    public void collectReward() {
        bonusReward.collectReward();
        assertTrue("Test not pass because the reward not been collected.",bonusReward.isCollected());
    }

    @Test
    public void isCollected() {
        bonusReward.collectReward();
        assertTrue("Test not pass because the reward not been collected",bonusReward.isCollected());
    }

    @Test
    public void respawn() {
        bonusReward.respawn(1);
        assertFalse("Test not pass because after respawn the reward is still collected",bonusReward.isCollected());
    }

    @Test
    public void getX() {
        bonusReward.setX(100);
        assertEquals("Test not pass because it not get the correct value of X",100, bonusReward.getX());
    }

    @Test
    public void getY() {
        bonusReward.setY(100);
        assertEquals("Test not pass because it not get the correct value of Y",100, bonusReward.getY());
    }

    @Test
    public void setX() {
        bonusReward.setX(100);
        assertEquals("Test not pass because it not set the correct value of X",100, bonusReward.getX());
    }

    @Test
    public void setY() {
        bonusReward.setY(100);
        assertEquals("Test not pass because it not set the correct value of Y",100, bonusReward.getY());
    }

    @Test
    public void getValue() {
        assertEquals("Test not pass because it not get the correct number of value",1, bonusReward.getValue());
    }
}