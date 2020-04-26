package com.mycompany.app;

import com.mycompany.app.UserInterface.GamePanel;

import javax.swing.JFrame;

/***
 * the main class that runs the game
 */

public class Game {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setTitle("Group 9 Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new GamePanel());
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }
}
