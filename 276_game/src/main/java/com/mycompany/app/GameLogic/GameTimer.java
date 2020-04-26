package com.mycompany.app.GameLogic;

import com.mycompany.app.UserInterface.GamePanel;

import java.util.Timer;
import java.util.TimerTask;
import java.awt.*;
import java.text.DecimalFormat;

/***
 * runs a separate thread for a timer that will increment a int
 * at a steady pace, emulating a timer and will display or pass
 * that time to whichever object that needs it
 */
public class GameTimer {

    private int secondsPassed;
    private Timer gameTimer;
    private String output;

    public GameTimer() {
        secondsPassed = 0;
        gameTimer = new Timer();
    }

    private TimerTask task = new TimerTask() {

        public void run() {
            secondsPassed++;
        }
    };

    public void update() {
        int seconds = secondsPassed % 60;
        int hours = secondsPassed / 60;
        int minutes = hours % 60;
        hours /= 60;
        DecimalFormat formatter = new DecimalFormat("00");
        String hourString = formatter.format(hours);
        String minutesString = formatter.format(minutes);
        String secondsString = formatter.format(seconds);
        output = hourString + ":" + minutesString + ":" + secondsString;
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.drawString(output, GamePanel.PANEL_WIDTH / 2 - 30, 25);
    }

    public void runTimer() {
        gameTimer.scheduleAtFixedRate(task, 1000, 1000);
    }

    public String stopTimer() {
        gameTimer.cancel();
        return output;
    }

    public int getSecondsPassed() {
        return secondsPassed;
    }
}
