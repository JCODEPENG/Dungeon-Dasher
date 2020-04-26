package com.mycompany.app.StaticGameObjects;

//import org.jboss.arquillian.container.test.api.Deployment;
//import org.jboss.arquillian.junit.Arquillian;
//import org.jboss.shrinkwrap.api.ShrinkWrap;
//import org.jboss.shrinkwrap.api.asset.EmptyAsset;
//import org.jboss.shrinkwrap.api.spec.JavaArchive;

import com.mycompany.app.GameLogic.Door;
import com.mycompany.app.GameLogic.Player;
import com.mycompany.app.MapObjects.Map;
import com.mycompany.app.UserInterface.GamePanel;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.Assert.*;

/***
 * Door test state tests door conditions and functions and interactions between objects
 */
public class DoorTest {
    private Door door;
    private Map map = new Map("src/main/resources/level1.txt", 64);
    private Player player = new Player(map);
    private BufferedImage img = new BufferedImage(GamePanel.PANEL_WIDTH, GamePanel.PANEL_HEIGHT, BufferedImage.TYPE_INT_RGB);
    private Graphics2D g2 = (Graphics2D) img.getGraphics();

    @Before
    public void setUp() {
        door = new Door(map);
    }

    @Test
    public void useDoor() {
        door.useDoor();
        assertFalse("Test not pass because after use door the door is still closed",door.isClosed());
    }

    @Test
    public void setTrap() {
        door.setTrap(true);
        assertTrue("Test not pass because there is not trap after setTrap.",door.hasTrap());
    }

    @Test
    public void hasTrap() {
        door.setTrap(false);
        assertFalse("Test not pass because the trap is not set",door.hasTrap());
    }

    @Test
    public void respawnDoor() {
        door.respawnDoor();
        assertTrue("Test not pass because the door respawn and leave opening",door.isClosed());
    }

    @Test
    // same as above
    public void isClosed() {
        door.respawnDoor();
        assertTrue("Test not pass because the door respawn and leave opening",door.isClosed());
    }

    @Test
    public void draw() {

    }

    @Test
    public void getX() {
        door.setX(10);
        assertEquals("Test not pass because it not get the correct value of X",10, door.getX());
    }

    @Test
    public void getY() {
        door.setY(10);
        assertEquals("Test not pass because it not get the correct value of Y",10, door.getY());
    }

    @Test
    public void setX() {
        door.setX((10));
        assertEquals("Test not pass because it not get the correct value of X",10, door.getX());
    }

    @Test
    public void setY() {
        door.setY(10);
        assertEquals("Test not pass because it not get the correct value of Y",10, door.getY());
    }

    @Test
    public void getDoorID() {
        assertEquals("Test not pass because it not get the correct value of Door's ID",0, door.getDoorID());
    }
}
