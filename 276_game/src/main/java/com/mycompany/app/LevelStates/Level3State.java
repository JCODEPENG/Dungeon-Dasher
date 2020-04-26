package com.mycompany.app.LevelStates;

import com.mycompany.app.GameLogic.*;
import com.mycompany.app.MapObjects.Map;
import com.mycompany.app.UserInterface.GamePanel;
import com.mycompany.app.UserInterface.GameState;
import com.mycompany.app.UserInterface.GameStateManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Level3State extends GameState {
    public static final int LEVEL1_LIVES = 3;

    private Map map;
    private Player player;
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private GameTimer timer;
    private ExitPoint exitPoint;
    private boolean startTimer = false;

    private ArrayList<Door> doors = new ArrayList<>();
    private ArrayList<Reward> rewards = new ArrayList<>();
    private ArrayList<BonusReward> bonusRewards = new ArrayList<>();
    private ArrayList<Trap> traps = new ArrayList<>();

    private final int REWARD_VALUE = 1000;
    private final int BONUS_REWARD_VALUE = 2000;
    private final int TRAP_VALUE = 500;
    private final int TOTAL_REWARDS = 4;

    private boolean gameWinnable = false;

    public Level3State(GameStateManager gm) {
        this.gm = gm;
        initMap();
        initPlayers();
    }

    public void update() {
        if(!startTimer){
            startTimer = true;
            timer.runTimer();
        }
        //this is for the other repeat of this game
        if (player.playerDead()) {
            gm.setCurrentState(gm.GAMEOVERSTATE);
            restartGame();
        }

        //check door for player or enemies
        for (Door door : doors) {
            if (player.checkDoor(door))
                break;
        }

        //enemy movement
        for (Enemy enemy : enemies) {
            enemy.update();
            for (Door door : doors)
                if (enemy.checkDoor(door))
                    break;
        }

        //checking if player touch rewards or traps
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
        //if player has gotten all mandatory rewards make exitpoint appear
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

    //for ui game input handling
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
                    //depending on door id, determines which object to spawn
                    if (door.hasTrap() && door.getDoorID() == 3) {
                        Enemy enemy = new Enemy(map, player, REWARD_VALUE);
                        enemy.setX(door.getX());
                        enemy.setY(door.getY() - 50);
                        enemies.add(enemy);
                    }

                    if (door.hasBonus() && door.getDoorID() == 1) {
                        System.out.println(true);
                        BonusReward bonusReward = new BonusReward(map, BONUS_REWARD_VALUE);
                        bonusReward.setX(1100);
                        bonusReward.setY(94);
                        bonusRewards.add(bonusReward);
                    }
                    if (door.hasBonus() && door.getDoorID() == 2) {
                        BonusReward bonusReward = new BonusReward(map, BONUS_REWARD_VALUE);
                        bonusReward.setX(door.getX());
                        bonusReward.setY(door.getY()-150);
                        bonusRewards.add(bonusReward);
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


    //initializing where the players and enemies are on the map
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

        Enemy enemy1 = new Enemy(map, player, REWARD_VALUE);
        enemy1.setX(700);
        enemy1.setY(500);
        enemies.add(enemy1);

        Enemy enemy2 = new Enemy(map, player, REWARD_VALUE);
        enemy2.setX(300);
        enemy2.setY(99);
        enemies.add(enemy2);

    }


    //initializing the objects, rewards, traps and doors, on the map
    private void initMap() {
        map = new Map("src/main/resources/level4.txt", 64);
        map.loadTiles("assets/tileset.png");

        Door door1 = new Door(map, 1);
        door1.setX(993);
        door1.setY(300);
        door1.setTrap(false);
        door1.setbonusReward(true);
        doors.add(door1);

        Door door2 = new Door(map, 2);
        door2.setX(100);
        door2.setY(780);
        door2.setTrap(false);
        door2.setbonusReward(true);
        doors.add(door2);

        Door door3 = new Door(map, 3);
        door3.setX(481);
        door3.setY(1070);
        door3.setTrap(true);
        door3.setbonusReward(false);
        doors.add(door3);

        Reward reward1 = new Reward(map, REWARD_VALUE);
        reward1.setX(900);
        reward1.setY(100);
        rewards.add(reward1);
        Reward reward2 = new Reward(map, REWARD_VALUE);
        reward2.setX(1150);
        reward2.setY(600);
        rewards.add(reward2);
        Reward reward3 = new Reward(map, REWARD_VALUE);
        reward3.setX(350);
        reward3.setY(150);
        rewards.add(reward3);
        Reward reward4 = new Reward(map, REWARD_VALUE);
        reward4.setX(214);
        reward4.setY(1058);
        rewards.add(reward4);


        exitPoint = new ExitPoint(map);
        exitPoint.setX(1170);
        exitPoint.setY(1174);


        Trap trap = new Trap(map, TRAP_VALUE);
        trap.setX(150);
        trap.setY(450);
        traps.add(trap);

        Trap trap2 = new Trap(map, TRAP_VALUE);
        trap2.setX(400);
        trap2.setY(730);
        traps.add(trap2);

        Trap trap3 = new Trap(map, TRAP_VALUE);
        trap3.setX(770);
        trap3.setY(674);
        traps.add(trap3);

        Trap trap4 = new Trap(map, TRAP_VALUE);
        trap4.setX(680);
        trap4.setY(996);
        traps.add(trap4);

        Trap trap7 = new Trap(map, TRAP_VALUE);
        trap7.setX(950);
        trap7.setY(608);
        traps.add(trap7);

        timer = new GameTimer();
        //timer.runTimer();

    }

    //resetting all the positions for all game objects
    private void restartGame() {
        timer = new GameTimer();
        startTimer = false;
        for (Reward reward : rewards) {
            reward.respawn(REWARD_VALUE);
        }
        for (Door door : doors) {
            door.respawnDoor();
        }
        bonusRewards.clear();
        for (Trap trap : traps) {
            trap.respawn();
        }
        exitPoint.respawn();
        resetPlayers();
        gameWinnable = false;

    }

    //check if the total rewards collected is the amount needed to win
    public void spawnExit() {
        if (player.numOfRewardsCollected() >= TOTAL_REWARDS) {
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
        player.resetInventory();

        enemies.clear();
        Enemy enemy1 = new Enemy(map, player, REWARD_VALUE);
        enemy1.setX(700);
        enemy1.setY(500);
        enemies.add(enemy1);

        Enemy enemy2 = new Enemy(map, player, REWARD_VALUE);
        enemy2.setX(300);
        enemy2.setY(99);
        enemies.add(enemy2);

    }

}
