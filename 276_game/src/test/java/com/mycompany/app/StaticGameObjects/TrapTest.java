package com.mycompany.app.StaticGameObjects;

//import org.jboss.arquillian.container.test.api.Deployment;
//import org.jboss.arquillian.junit.Arquillian;
//import org.jboss.shrinkwrap.api.ShrinkWrap;
//import org.jboss.shrinkwrap.api.asset.EmptyAsset;
//import org.jboss.shrinkwrap.api.spec.JavaArchive;

import com.mycompany.app.GameLogic.Player;
import com.mycompany.app.GameLogic.Trap;
import com.mycompany.app.MapObjects.Map;
import com.mycompany.app.UserInterface.GamePanel;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.Assert.*;

/***
 * Trap test state tests trap conditions and functions
 */
public class TrapTest {
    private Trap trap;
    private Map map = new Map("src/main/resources/level1.txt", 64);
    private Player player = new Player(map);
    private BufferedImage img = new BufferedImage(GamePanel.PANEL_WIDTH, GamePanel.PANEL_HEIGHT, BufferedImage.TYPE_INT_RGB);
    private Graphics2D g2 = (Graphics2D) img.getGraphics();

    @Before
    public void setUp() {
        trap = new Trap(map, 1);
    }

    @Test
    public void update() {
    }

    @Test
    public void collectTrap() {
        trap.collectTrap();

        assertTrue("Test not pass because the trap did not collect.",trap.isPickedUp());
    }

    @Test
    public void isPickedUp() {
        trap.collectTrap();
        assertTrue("Test not pass because the trap is not collect.",trap.isPickedUp());
    }

    @Test
    public void respawn() {
        trap.respawn();
        assertFalse("Test not pass because the trap is picked up and not respawn",trap.isPickedUp());
    }

    @Test
    public void getX() {
        trap.setX(10);
        assertEquals("Test not pass because it not get the correct value of X",10, trap.getX());
    }

    @Test
    public void getY() {
        trap.setY(10);
        assertEquals("Test not pass because it not get the correct value of Y",10, trap.getY());
    }

    @Test
    public void setX() {
        trap.setX(10);
        assertEquals("Test not pass because it not get the correct value of X",10, trap.getX());
    }

    @Test
    public void setY() {
        trap.setY(10);
        assertEquals("Test not pass because it not get the correct value of Y",10, trap.getY());
    }

    @Test
    public void getValue() {
        assertEquals("Test not pass because it not get the correct number of Value",1, trap.getValue());
    }
}
