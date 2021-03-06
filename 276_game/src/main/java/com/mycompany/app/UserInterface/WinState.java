package com.mycompany.app.UserInterface;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/***
 * Win state that shows if the player wins the game
 */

public class WinState extends GameState {

    private String[] menuItems = {"Return to Menu", "Quit"};
    private int selection = 0;
    private BufferedImage img;

    public WinState(GameStateManager gm) {
        this.gm = gm;
        try {
            img = ImageIO.read(new File("assets/tileset.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {

    }

    public void draw(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, GamePanel.PANEL_WIDTH, GamePanel.PANEL_HEIGHT);
        g.fillRect(0, 0, GamePanel.PANEL_WIDTH, GamePanel.PANEL_HEIGHT);
        Image tmp = img.getScaledInstance(GamePanel.PANEL_WIDTH, GamePanel.PANEL_HEIGHT, Image.SCALE_SMOOTH);
        //BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        //Graphics2D g2d = resized.createGraphics();
        g.drawImage(tmp, 0, 0, null);
        g.setColor(Color.WHITE);
        g.setFont(new Font("arial", Font.BOLD, 40));
        g.drawString("Congratulations!", 350, 100);
        g.drawString("You have beaten the hardest level ever", 250, 150);
        g.drawString("Your final time is " + time, 250, 200);
        g.drawString(" with the score " + GameState.playerScore, 250, 250);

        for (int i = 0; i < menuItems.length; i++) {
            if (i == selection)
                g.setColor(Color.RED);
            else
                g.setColor(Color.WHITE);

            g.drawString(menuItems[i], 350, 350 + (i * 75));
        }
    }

    public void keyPressed(int key) {
        if (key == KeyEvent.VK_ENTER)
            selectItem();

        if (key == KeyEvent.VK_DOWN) {
            selection++;
            if (selection == menuItems.length)
                selection = 0;
        }

        if (key == KeyEvent.VK_UP) {
            selection--;
            if (selection < 0)
                selection = menuItems.length - 1;
        }

    }

    private void selectItem() {
        switch (selection) {
            case 0:
                gm.setCurrentState(gm.MENUSTATE);
                break;
            case 1:
                System.exit(0);
                break;
            default:
                break;
        }
    }

    public void keyReleased(int key) {
    }
}