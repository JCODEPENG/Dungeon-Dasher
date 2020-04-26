package com.mycompany.app.MovableGameObjects;

import com.mycompany.app.GameLogic.Door;
import com.mycompany.app.GameLogic.Enemy;
import com.mycompany.app.GameLogic.Player;
import com.mycompany.app.MapObjects.Map;
import com.mycompany.app.UserInterface.GamePanel;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.Assert.*;

/***
 * Enemy test tests how the enemy operates moves and it's functionality
 */
public class EnemyTest {
    private Enemy enemy;
    private Map map = new Map("src/main/resources/level.txt", 64);
    private Player player = new Player(map);
    private Door door = new Door(map);
    private BufferedImage img = new BufferedImage(GamePanel.PANEL_WIDTH, GamePanel.PANEL_HEIGHT, BufferedImage.TYPE_INT_RGB);
    private Graphics2D g2 = (Graphics2D) img.getGraphics();

    @Before
    public void setUp() {
        map.loadTiles("assets/tileset.png");
        enemy = new Enemy(map, player, 100);
        enemy.setX(4);
        enemy.setY(8);
        enemy.draw(g2);
        assertEquals("Test not pass because it not get the correct value of X",4, enemy.getXResp());
        assertEquals("Test not pass because it not get the correct value of Y",8, enemy.getYResp());

        player = new Player(map);
        player.setX(100);
        player.setY(100);

    }

    @Test
    public void enemyRespawnPos() {
        assertEquals("Test not pass because it not get the correct value of X",4, enemy.getXResp());
        assertEquals("Test not pass because it not get the correct value of Y",8, enemy.getYResp());
    }


    @Test
    public void enemyNotMoving() {
        int x = enemy.getX();
        int y = enemy.getY();
        enemy.update();
        if (!player.isMoving()) {
            assertEquals("Test not pass because it not get the correct value of X",x, enemy.getX());
            assertEquals("Test not pass because it not get the correct value of Y",y, enemy.getY());
        }
    }

    @Test
    public void enemyIsMoving() {
        int x = enemy.getX();
        int y = enemy.getY();
        player.setDown(true);
        player.setRight(true);
        player.update();
        enemy = new Enemy(map, player, 100);
        assertNotEquals("Test not pass because it not get the correct value of X",x, enemy.getX());
        assertNotEquals("Test not pass because it not get the correct value of Y",y, enemy.getY());
    }

    @Test
    public void checkDoorTest() {
        enemy.setX(96);
        enemy.setY(42);
        door.setX(50);
        door.setY(10);
        assertTrue("Test not pass because no door detected",enemy.checkDoor(door));
        door.setX(2);
        door.setY(2);
        assertFalse("Test not pass because  door detected but actually there is no door over here",enemy.checkDoor(door));
        door.setY(74);
        door.setX(50);
        assertTrue("Test not pass because no door detected",enemy.checkDoor(door));
        door.setX(48);
        door.setY(10);
        assertFalse("Test not pass because  door detected but actually there is no door over here",enemy.checkDoor(door));
        door.setY(73);
        door.setX(144);
        assertTrue("Test not pass because no door detected",enemy.checkDoor(door));
        door.setX(73);
        door.setX(145);
        assertFalse("Test not pass because  door detected but actually there is no door over here",enemy.checkDoor(door));
        door.setX(48);
        door.setY(73);
        assertTrue("Test not pass because no door detected",enemy.checkDoor(door));
    }

    @Test
    public void testEnemyHitPlayer() {
        player.setX(26);
        player.setY(26);
        player.setXResp(200);
        player.setYResp(201);
        enemy.setY(27);
        enemy.setX(27);
        enemy.setXResp(4);
        enemy.setYResp(4);

        Rectangle theEnemy = enemy.getEnemyObject();
        player.draw(g2);
        enemy.draw(g2);
        Rectangle thePlayer = player.getPlayerObject();
        if (theEnemy.intersects(thePlayer)) {
            enemy.enemyHitPlayer(player);
            assertEquals("Test not pass because it not get the correct value of X",4, enemy.getX());
            assertEquals("Test not pass because it not get the correct value of Y",4, enemy.getY());
            assertEquals("Test not pass because it not get the correct value of X",200, player.getX());
            assertEquals("Test not pass because it not get the correct value of Y",201, player.getY());
            assertEquals("Test not pass because it not get the correct value of playerlives",2, player.getPlayerLives());
        }
    }
}
