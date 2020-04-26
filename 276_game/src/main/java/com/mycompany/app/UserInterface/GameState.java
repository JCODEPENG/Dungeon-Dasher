package com.mycompany.app.UserInterface;

import com.mycompany.app.UserInterface.GameStateManager;

import java.awt.Graphics2D;

/***
 * The GameState abstract class that acts as the base class that all the level states
 * game states use to switch between states
 */
public abstract class GameState {

    protected GameStateManager gm;
    public static int playerScore = 0;
    public static String time;

    public abstract void update();

    public abstract void draw(Graphics2D g);

    public abstract void keyPressed(int key);

    public abstract void keyReleased(int key);
}
