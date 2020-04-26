package com.mycompany.app.StaticGameObjects;

import com.mycompany.app.MapObjects.Map;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/***
 * Map Test class Checks if the map is detecting what is walls
 */
public class MapTest {
    Map map;

    @Before
    public void setUpMap() {
        map = new Map("src/main/resources/level.txt", 64);
        map.loadTiles("assets/tileset.png");
    }

    @Test
    public void checkWallTest() {
        assertTrue("not blocked",map.isBlocked(0, 0));
        assertTrue("not blocked",map.isBlocked(0, 1));
        assertTrue("not blocked",map.isBlocked(0, 1));
        assertFalse("not blocked",map.isBlocked(1, 1));
        assertFalse("not blocked",map.isBlocked(15, 7));
    }

}
