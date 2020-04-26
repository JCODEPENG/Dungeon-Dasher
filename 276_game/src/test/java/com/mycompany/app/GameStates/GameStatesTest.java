package com.mycompany.app.GameStates;

import com.mycompany.app.UserInterface.GameStateManager;
import com.mycompany.app.UserInterface.MenuState;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.awt.event.KeyEvent;

/***
 * Tests toggling different states
 */
public class GameStatesTest {
    GameStateManager gameStateManager = new GameStateManager();
    MenuState menuState;

    @Before
    public void setStates() {
        menuState = new MenuState(gameStateManager);
    }

    @Test
    public void keyPressTesting() {
        menuState.keyPressed(KeyEvent.VK_ENTER);
        assertEquals("Get the wrong selection",0, menuState.getSelection());
        menuState.keyPressed(KeyEvent.VK_DOWN);
        assertEquals("Get the wrong selection",1, menuState.getSelection());
        menuState.keyPressed(KeyEvent.VK_DOWN);
        assertEquals("Get the wrong selection",2, menuState.getSelection());
        menuState.keyPressed(KeyEvent.VK_DOWN);
        assertEquals("Get the wrong selection",0, menuState.getSelection());
        menuState.keyPressed(KeyEvent.VK_UP);
        assertEquals("Get the wrong selection",2, menuState.getSelection());
        menuState.keyPressed(KeyEvent.VK_UP);
        assertEquals("Get the wrong selection",1, menuState.getSelection());
    }
}
