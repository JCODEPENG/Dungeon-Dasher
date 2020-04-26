package com.mycompany.app.StaticGameObjects;

import com.mycompany.app.GameLogic.ExitPoint;
import com.mycompany.app.MapObjects.Map;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/***
 * Exit point tests exitpoint conditions and it's functionality
 */
public class ExitPointTest {
    private ExitPoint exitPoint;
    private Map map = new Map("src/main/resources/level1.txt", 64);

    @Before
    public void setExitPoint() {
        exitPoint = new ExitPoint(map);
        exitPoint.setX(4);
        exitPoint.setY(2);
    }

    @Test
    public void notActive() {
        assertFalse("Test not pass because exitpoint active",exitPoint.getActive());
        exitPoint.setExitPoint();
        assertTrue("Test not pass because exitpoint not active",exitPoint.getActive());
        exitPoint.respawn();
        assertFalse("Test not pass because exitpoint active",exitPoint.getActive());
    }

    @Test
    public void correctPositions() {
        assertEquals("Test not pass because it not get the correct value of X",4, exitPoint.getX());
        assertEquals("Test not pass because it not get the correct value of Y",2, exitPoint.getY());
    }

    @Test
    public void checkDrawings() {
        assertNotNull("Test not pass because it getopensprite",exitPoint.getClosedSprite());
        assertNotNull("Test not pass because it getclosedsprite",exitPoint.getOpenSprite());
    }
}
