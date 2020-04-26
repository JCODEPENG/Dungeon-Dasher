package com.mycompany.app.MovableGameObjects;

import com.mycompany.app.GameLogic.*;
import com.mycompany.app.MapObjects.Map;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/***
 * player test tests how the player operates moves and it's functionality
 * and how it interacts with other objects
 */
public class PlayerTest {

    private Map map = new Map("src/main/resources/level.txt", 64);
    private Player player = new Player(map);
    private Reward reward = new Reward(map, 1);
    private BonusReward bonusReward = new BonusReward(map, 1);

    private Door door = new Door(map);
    private Trap trap = new Trap(map, 1);
    private ExitPoint exit = new ExitPoint(map);

    @Before
    public void init() {
        map.loadTiles("assets/tileset.png");
        player = new Player(map);
        player.setX(100);
        player.setY(100);
        player.setDown(false);
        player.setUp(false);
        player.setRight(false);
        player.setLeft(false);
        //trap
        trap.setX(100);
        trap.setY(118);
    }

    @Test
    public void testPosition() {
        // Check if player positions are correct
        assertEquals("Test not pass because it not get the correct value of X",100, player.getX());
        assertEquals("Test not pass because it not get the correct value of Y",100, player.getY());

        player.setXResp(5);
        player.setYResp(5);
        player.respawn();

        assertEquals("Test not pass because it not get the correct value of X",5, player.getX());
        assertEquals("Test not pass because it not get the correct value of Y",5, player.getY());
    }

    @Test
    public void testLives() {
        assertTrue("Test not pass because player lives less than zero",player.getPlayerLives() >= 0);
        assertFalse("Test not pass because player not die",player.playerDead());

        player.decreaseLife();
        player.decreaseLife();
        player.decreaseLife();

        assertTrue("Test not pass because player not die yet",player.playerDead());
    }

    @Test
    public void testScore() {
        assertEquals("Test not pass because it not get the correct value of score",0, player.getScore());

        player.addScore(100);
        assertEquals("Test not pass because it not get the correct value of score",100, player.getScore());

        player.decreaseScore(50);
        assertEquals("Test not pass because it not get the correct value of score",50, player.getScore());

        player.resetScore();
        assertEquals("Test not pass because it not get the correct value of score",0, player.getScore());
    }

    @Test
    public void testMovement() {
        player.setDown(true);
        player.setRight(true);
        player.update();

        assertEquals("Test not pass because it not get the correct value of X",102, player.getX());
        assertEquals("Test not pass because it not get the correct value of Y",102, player.getY());

        player.setDown(false);
        player.setRight(false);

        player.setLeft(true);
        player.setUp(true);
        player.update();

        assertEquals("Test not pass because it not get the correct value of X",100, player.getX());
        assertEquals("Test not pass because it not get the correct value of Y",100, player.getY());

        player.setLeft(false);
        player.setUp(false);
    }


    @Test
    public void testCheckReward() {
        //test player reach reward
        player.resetScore();
        reward.respawn(1);
        player.setX(150);
        player.setY(180);
        reward.setY(150);
        reward.setX(150);
        for (int i = 0; i < 30; i++) {
            player.update();
            player.checkReward(reward);
            if (player.getScore() == 1) {
                assertEquals("Test not pass because it not get the correct value of score",1, player.getScore());
                break;
            }
            reward.respawn(1);
        }
    }

    @Test
    public void testCheckBonus() {
        //test player reach BonusReward
        player.resetScore();
        bonusReward.respawn(1);
        player.setX(150);
        player.setY(180);
        bonusReward.setY(150);
        bonusReward.setX(150);
        for (int i = 0; i < 30; i++) {
            player.update();
            player.checkBonus(bonusReward);
            if (player.getScore() == 1) {
                assertEquals("Test not pass because it not get the correct value of score",1, player.getScore());
                break;
            }
            bonusReward.respawn(1);
        }
    }


    //player moving on player
    @Test
    public void checkTestDoor() {
        player.setX(92);
        player.setY(42);
        door.setX(50);
        door.setY(10);
        assertTrue("Test not pass because no door detected",player.checkDoor(door));
        door.setX(40);
        door.setY(10);
        assertFalse("Test not pass because there should be a door over here",player.checkDoor(door));
        door.setX(50);
        door.setY(43);
        assertFalse("Test not pass because there should be a door over here",player.checkDoor(door));
        door.setY(74);
        door.setX(46);
        assertTrue("Test not pass because no door detected",player.checkDoor(door));
        door.setX(0);
        door.setY(0);
        assertFalse("Test not pass because there should be a door over here",player.checkDoor(door));
        door.setY(73);
        door.setX(140);
        assertTrue("Test not pass because no door detected",player.checkDoor(door));
        door.setX(44);
        door.setY(73);
        assertTrue("Test not pass because no door detected",player.checkDoor(door));
    }

    @Test
    public void testCheckTrap() {
        //test player reach trap form above
        player.setDown(true);
        player.update();
        player.checkTrap(trap);
        assertEquals("Test not pass because it is not get the correct playerlives",2, player.getPlayerLives());
        assertEquals("Test not pass because it is not get the correct score",-1, player.getScore());

        //test player reach trap form under
        player.resetScore();
        player.resetLives(3);
        trap.respawn();
        player.setX(100);
        player.setY(152);
        player.setDown(false);
        player.setUp(true);
        player.update();
        player.checkTrap(trap);
        assertEquals("Test not pass because it is not get the correct playerlives",2, player.getPlayerLives());
        assertEquals("Test not pass because it is not get the correct score",-1, player.getScore());

        //test player reach trap form left
        player.resetScore();
        player.resetLives(3);
        trap.respawn();
        player.setX(130);
        player.setY(118);
        player.setUp(false);
        player.setRight(true);
        player.update();
        player.checkTrap(trap);
        assertEquals("Test not pass because it is not get the correct playerlives",2, player.getPlayerLives());
        assertEquals("Test not pass because it is not get the correct score",-1, player.getScore());

        //test player reach trap form right
        player.resetScore();
        player.resetLives(3);
        trap.respawn();
        player.setX(120);
        player.setY(150);
        trap.setY(150);
        trap.setX(150);
        player.setRight(false);
        player.setLeft(true);
        player.update();
        player.checkTrap(trap);
        assertEquals("Test not pass because it is not get the correct playerlives",2, player.getPlayerLives());
        assertEquals("Test not pass because it is not get the correct score",-1, player.getScore());
    }

    @Test
    public void testExit() {
        //Test entering the exit from above
        exit.setX(player.getX());
        exit.setY(player.getY() + 30);
        player.setDown(true);
        for (int i = 0; i < 30; i++) {
            player.update();
            if (player.CheckGameWon(exit))
                assertTrue("game not won",player.CheckGameWon(exit));
        }
        player.setDown(false);

        //Test entering the exit from below
        exit.setY(player.getY() - 30);
        player.setUp(true);
        for (int i = 0; i < 30; i++) {
            player.update();
            if (player.CheckGameWon(exit))
                assertTrue("game not won",player.CheckGameWon(exit));
        }
        player.setUp(false);

        //Test entering the exit from the right
        exit.setX(player.getX() + 30);
        player.setRight(true);
        for (int i = 0; i < 30; i++) {
            player.update();
            if (player.CheckGameWon(exit))
                assertTrue("game not won",player.CheckGameWon(exit));
        }
        player.setRight(false);

        //Test entering the exit from the left
        exit.setX(player.getX() - 30);
        player.setLeft(true);
        for (int i = 0; i < 30; i++) {
            player.update();
            if (player.CheckGameWon(exit))
                assertTrue("game not won",player.CheckGameWon(exit));
        }
        player.setLeft(false);

        //Test to see if the exit behaves properly while the player is not entering it
        exit.setX(player.getX() + 100);
        exit.setY(player.getY() + 100);
        assertFalse("game won",player.CheckGameWon(exit));
    }
}
