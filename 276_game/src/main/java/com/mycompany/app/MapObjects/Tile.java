package com.mycompany.app.MapObjects;

import java.awt.image.BufferedImage;

/***
 * The tile contains a sprite based on where the pixel
 * also the tiles also contain information of if the spot is a wall or not
 */

public class Tile {
    private BufferedImage image;
    private boolean blocked;

    public Tile(BufferedImage image, boolean blocked) {
        this.image = image;
        this.blocked = blocked;
    }

    public BufferedImage getImage() {
        return image;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean b) {
        blocked = b;
    }
}
