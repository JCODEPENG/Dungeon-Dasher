package com.mycompany.app.StaticGameObjects;

import com.mycompany.app.GameLogic.GameTimer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/***
 * Game timer test tests the basic and necessary components of the game timer
 */
public class GameTimerTest {
    private GameTimer timer;

    @Before
    public void setUp() {
        timer = new GameTimer();
    }

    @Test
    public void timeNotNegative() {
        int time = timer.getSecondsPassed();
        assertTrue("Not pass the test because the time is less than zero",time >= 0);
    }

    @Test
    public void timeGivenInSeconds() {
        assertEquals("The test is not passed because it not get the correct value of secondpassed",0, timer.getSecondsPassed());
    }

    @Test
    public void stopTimerWorking() {
        timer.update();
        assertNotNull("The test is not passed because the stoptimer is null",timer.stopTimer());
    }

}
