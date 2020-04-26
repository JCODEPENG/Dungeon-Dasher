package com.mycompany.app.LevelStates;

import com.mycompany.app.GameLogic.*;
import com.mycompany.app.UserInterface.GameState;
import com.mycompany.app.MapObjects.Map;
import com.mycompany.app.UserInterface.GamePanel;
import com.mycompany.app.UserInterface.GameStateManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * this is our level where we tested our features not actually a real level
 */

//this "state" can be duplicated into several different level states
// ie) Level1State, Level2State, think of this as a template!
public class LevelTestState extends GameState {

    public static final int DEFAULT_ENEMIES = 1;
    public static final int LEVEL1_LIVES = 3;

    private Map map;
    private Player player;
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private GameTimer timer;
    private ExitPoint exitPoint;

    private ArrayList<Door> doors = new ArrayList<>();
    private ArrayList<Reward> rewards = new ArrayList<>();
    private ArrayList<BonusReward> bonusRewards = new ArrayList<>();
    private ArrayList<Trap> traps = new ArrayList<>();

    private int rewardValue = 1000;
    private int bonusRewardValue = 2000;
    private int trapValue = 500;
    private int totalRewards = 2;

    private boolean gameWinnable = false;

    public LevelTestState(GameStateManager gm) {
        this.gm = gm;
        initMap();
        initPlayers();
    }

    public void update() {
        if (player.playerDead()) {
            gm.setCurrentState(gm.GAMEOVERSTATE);
            restartGame();
        }

        for (Door door : doors) {
            player.checkDoor(door);
            enemies.get(0).checkDoor(door);
        }

        for (Enemy enemy : enemies) {
            enemy.update();
        }

        for (Reward reward : rewards) {
            reward.update();
            player.checkReward(reward);
        }
        for (BonusReward bonusReward : bonusRewards) {
            bonusReward.update();
            player.checkBonus(bonusReward);
        }
        for (Trap trap : traps) {
            trap.update();
            player.checkTrap(trap);
        }

        map.update();
        timer.update();
        player.update();
        spawnExit();
        if (gameWinnable && player.CheckGameWon(exitPoint)) {
            time = timer.stopTimer();
            playerScore = player.getScore();
            gm.setCurrentState(gm.GAMEWINSTATE);
            restartGame();
        }
    }

    public void draw(Graphics2D g) {
        //fill background black
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, GamePanel.PANEL_WIDTH, GamePanel.PANEL_HEIGHT);

        map.draw(g);
        timer.draw(g);
        for (Door door : doors) {
            door.draw(g);
        }
        for (Reward reward : rewards) {
            reward.draw(g);
        }
        for (BonusReward bonusReward : bonusRewards) {
            bonusReward.draw(g);
        }
        for (Trap trap : traps) {
            trap.draw(g);
        }
        for (Enemy enemy : enemies) {
            enemy.draw(g);
        }
        player.draw(g);
        exitPoint.draw(g);
    }

    public void keyPressed(int key) {
        if (key == KeyEvent.VK_UP) {
            player.setUp(true);
        }

        if (key == KeyEvent.VK_DOWN) {
            player.setDown(true);
        }

        if (key == KeyEvent.VK_LEFT) {
            player.setLeft(true);
        }

        if (key == KeyEvent.VK_RIGHT) {
            player.setRight(true);
        }

        if (key == KeyEvent.VK_P) {
            gm.setCurrentState(gm.MENUSTATE);
        }

        if (key == KeyEvent.VK_E) {
            for (Door door : doors) {
                if (player.checkDoor(door)) {
                    door.useDoor();
                    if (door.hasTrap()) {
                        Enemy enemy = new Enemy(map, player, rewardValue);
                        enemy.setX(door.getX());
                        enemy.setY(door.getY() - 20);
                        enemies.add(enemy);
                    }
                }
            }
        }
    }

    public void keyReleased(int key) {
        if (key == KeyEvent.VK_UP) {
            player.setUp(false);
        }

        if (key == KeyEvent.VK_DOWN) {
            player.setDown(false);
        }

        if (key == KeyEvent.VK_LEFT) {
            player.setLeft(false);
        }

        if (key == KeyEvent.VK_RIGHT) {
            player.setRight(false);
        }
    }

    private void initPlayers() {
        player = new Player(map);
        player.setUp(false);
        player.setDown(false);
        player.setRight(false);
        player.setLeft(false);
        player.setX(80);
        player.setY(80);
        player.setXResp(80);
        player.setYResp(80);

        Enemy enemy = new Enemy(map, player, rewardValue);
        enemy.setX(300);
        enemy.setY(300);
        enemies.add(enemy);
    }

    private void initMap() {
        map = new Map("level.txt", 64);
        map.loadTiles("assets/tileset.png");

        Door door = new Door(map, 1);
        door.setX(224);
        door.setY(240);
        door.setTrap(true);
        doors.add(door);

        Reward reward1 = new Reward(map, rewardValue);
        reward1.setX(150);
        reward1.setY(300);
        rewards.add(reward1);
        Reward reward2 = new Reward(map, rewardValue);
        reward2.setX(300);
        reward2.setY(300);
        rewards.add(reward2);

        exitPoint = new ExitPoint(map);
        exitPoint.setX(600);
        exitPoint.setY(600);

        BonusReward bonusReward = new BonusReward(map, bonusRewardValue);
        bonusReward.setX(150);
        bonusReward.setY(350);
        bonusRewards.add(bonusReward);


        Trap trap = new Trap(map, trapValue);
        trap.setX(150);
        trap.setY(450);
        traps.add(trap);

        timer = new GameTimer();
        timer.runTimer();

    }

    private void restartGame() {
        timer = new GameTimer();
        timer.runTimer();
        for (Reward reward : rewards) {
            reward.respawn(rewardValue);
        }
        for (Door door : doors) {
            door.respawnDoor();
        }
        for (BonusReward bonusReward : bonusRewards) {
            bonusReward.respawn(rewardValue);
        }
        for (Trap trap : traps) {
            trap.respawn();
        }
        exitPoint.respawn();
        resetPlayers();
        gameWinnable = false;

    }

    public void spawnExit() {
        if (player.numOfRewardsCollected() >= totalRewards) {
            exitPoint.setExitPoint();
            gameWinnable = true;
        }

    }

    private void resetPlayers() {
        player.setUp(false);
        player.setDown(false);
        player.setRight(false);
        player.setLeft(false);
        player.setX(80);
        player.setY(80);
        player.setXResp(80);
        player.setYResp(80);
        player.resetScore();
        player.resetLives(LEVEL1_LIVES);

        for (int i = DEFAULT_ENEMIES; i < enemies.size(); i++)
            enemies.remove(i);

        enemies.get(0).setX(300);
        enemies.get(0).setY(300);
    }

}
