package com.mycompany.app.GameStates;

import com.mycompany.app.UserInterface.GameStateManager;
import org.junit.Test;

import static org.junit.Assert.*;

/***
 * Game manager test pretty much just tests what current state it is right now
 */
public class GameStateManagerTest {

    GameStateManager gm = new GameStateManager();

    @Test
    public void testCurrentState() {
        assertTrue("Get the wrong state",gm.setCurrentState(0));
        assertTrue("Get the wrong state",gm.setCurrentState(1));
        assertTrue("Get the wrong state",gm.setCurrentState(2));
        assertTrue("Get the wrong state",gm.setCurrentState(3));
        assertTrue("Get the wrong state",gm.setCurrentState(4));
        assertTrue("Get the wrong state",gm.setCurrentState(5));
        assertTrue("Get the wrong state",gm.setCurrentState(6));
        assertTrue("Get the wrong state",gm.setCurrentState(7));
        assertFalse("Get the wrong state",gm.setCurrentState(8));
        assertFalse("Get the wrong state",gm.setCurrentState(-1));

        assertEquals("Get the wrong state that not meet the expectation",7, gm.getCurrentState());
    }
}
