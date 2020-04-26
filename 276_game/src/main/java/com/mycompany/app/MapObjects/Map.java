package com.mycompany.app.MapObjects;

import com.mycompany.app.UserInterface.GamePanel;

import java.io.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.Scanner;

/***
 * The map class sets up the game environment for the game
 * All the other the game objects use this class to determine where the game objects are place
 * relative to the coordinates of the map
 * must need to load tiles in order to form
 * must take in the map from the 2d array thats passed in
 */
public class Map {

    private int[][] map;
    private int mapRows; // height of the map
    private int mapCols; // width of the map
    private int tileSize;
    private int x;
    private int y;
    private int minX;
    private int minY;
    private int maxX;
    private int maxY;

    private BufferedImage tileSet;
    private Tile[][] tiles;

    public Map(String level, int tileSize) {

        this.tileSize = tileSize;
        maxX = 0;
        maxY = 0;

        try {
            File file = new File(level);
            Scanner scan = new Scanner(file);

            mapRows = scan.nextInt();
            mapCols = scan.nextInt();
            map = new int[mapRows][mapCols];

            minX = GamePanel.WIDTH - mapCols * tileSize;
            minY = GamePanel.HEIGHT - mapRows * tileSize;

            for (int i = 0; i < mapRows; i++)
                for (int j = 0; j < mapCols; j++)
                    map[i][j] = scan.nextInt();

            scan.close();
        } catch (IOException e) {
        }
    }

    //loaded the sprites for the tiles based on the map array thats passed into
    public void loadTiles(String file) {
        try {
            tileSet = ImageIO.read(new File(file));
            int numTilesAcross = (tileSet.getWidth() / tileSize);
            tiles = new Tile[numTilesAcross][numTilesAcross];

            BufferedImage subImage;
            for (int i = 0; i < numTilesAcross - 1; i++) {
                for (int j = 0; j < numTilesAcross; j++) {
                    subImage = tileSet.getSubimage(j * tileSize, i * tileSize, tileSize, tileSize);
                    tiles[i][j] = new Tile(subImage, true);
                }
            }

            // remove collision from floors
            for (int i = 1; i < 4; i++) {
                for (int j = 1; j < 5; j++) {
                    tiles[i][j].setBlocked(false);
                }
            }


        } catch (Exception e) {
        }

    }

    public void setX(int x) {
        this.x = x;

        if (this.x < minX)
            this.x = -minX;

        if (x > maxX)
            this.x = maxX;
    }

    public void setY(int y) {
        this.y = y;

        if (this.y < minY)
            this.y = minY;

        if (this.y > maxY)
            this.y = maxY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getXTile(int x) {
        return x / tileSize;
    }

    public int getYTile(int y) {
        return y / tileSize;
    }


    public boolean isBlocked(int x, int y) {
        int tile = map[x][y];
        int row = tile / tiles[0].length;
        int col = tile % tiles[0].length;

        return tiles[row][col].isBlocked();
    }

    public void update() {

    }

    public void draw(Graphics2D g) {

        for (int i = 0; i < mapRows; i++)
            for (int j = 0; j < mapCols; j++) {
                int tile = map[i][j]; // current index;

                int row = tile / tiles[0].length;
                int col = tile % tiles[0].length;

                g.drawImage(tiles[row][col].getImage(), x + j * tileSize, y + i * tileSize, null);

            }
    }
}
