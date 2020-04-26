package com.mycompany.app.UserInterface;

import com.mycompany.app.LevelStates.Level1State;
import com.mycompany.app.LevelStates.Level2State;
import com.mycompany.app.LevelStates.Level3State;

import java.util.ArrayList;
import java.awt.Graphics2D;

/**
 * this class manage which stage to switch to
 */
public class GameStateManager {
    public static final int MENUSTATE = 0;
    public static final int HOWTOPLAYSTATE = 1;
    public static final int LEVEL1STATE = 2;
    public static final int LEVEL2STATE = 3;
    public static final int LEVEL3STATE = 4;
    public static final int GAMEOVERSTATE = 5;
    public static final int GAMEWINSTATE = 6;
    public static final int LevelSelect = 7;


    private ArrayList<GameState> gameStates;
    private int currentState;

    public GameStateManager() {
        gameStates = new ArrayList<GameState>();
        gameStates.add(new MenuState(this));
        gameStates.add(new HowToPlayState(this));
        gameStates.add(new Level1State(this));
        gameStates.add(new Level2State(this));
        gameStates.add(new Level3State(this));
        gameStates.add(new GameOverState(this));
        gameStates.add(new WinState(this));
        gameStates.add(new LevelSelect(this));
    }

    public boolean setCurrentState(int state) {
        if (state >= 0 && state <= 7) {
            currentState = state;
            return true;
        }

        return false;
    }

    public int getCurrentState() {
        return currentState;
    }

    public void update() {
        gameStates.get(currentState).update();
    }

    public void draw(Graphics2D g) {
        gameStates.get(currentState).draw(g);
    }

    public void keyPressed(int key) {
        gameStates.get(currentState).keyPressed(key);
    }

    public void keyReleased(int key) {
        gameStates.get(currentState).keyReleased(key);
    }
}
