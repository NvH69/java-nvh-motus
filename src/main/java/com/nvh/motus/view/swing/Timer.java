/**
 * Timer pour implémentation finale

package com.nvh.motus.view.swing;

import com.nvh.motus.Launcher;

import java.util.Calendar;

public class SwingTimer extends Thread {
    public final long dtreset;
    public boolean keepCounting;
    public long dt;
    public long nowTimer;
    public String LCD;
    public StringBuffer tempLCD;
    public Calendar c;

    public SwingTimer(long dt) {
        this.dt = dt + System.currentTimeMillis();
        dtreset = dt;
        keepCounting = false;
        LCD = null;
        tempLCD = new StringBuffer("");
        this.c = Calendar.getInstance();
        start();
    }

    @Override
    public void run() {
        while (true) {

            if (keepCounting) {
                nowTimer = System.currentTimeMillis();
                c.setTimeInMillis(dt - nowTimer);

                //limite temps atteinte:
                //chnagement de joueur
                if (c.getTimeInMillis() < 1000 && this == MainFrame.miniSwingTimer1 &&
                        Launcher.mainFrame.game.getActivePlayer() == 0 &&
                        Launcher.mainFrame.offset < Launcher.mainFrame.game.getWordLenght()) {
                    Launcher.mainFrame.ting.play();
                    this.suspendOn();
                    Launcher.mainFrame.swapPlayer();
                }
                if (c.getTimeInMillis() < 1000 && this == MainFrame.miniSwingTimer2 &&
                        Launcher.mainFrame.game.getActivePlayer() == 1 &&
                        Launcher.mainFrame.offset < Launcher.mainFrame.game.getWordLenght()) {
                    Launcher.mainFrame.ting.play();
                    this.suspendOn();
                    Launcher.mainFrame.swapPlayer();
                }
            }

            //tour gagné
            if (Launcher.mainFrame.game.getCurrentTurn().isAllFound()) {
                Launcher.mainFrame.getActiveTimer().suspendOn();
            }
            //tour lostTurn
            if (Launcher.mainFrame.game.getCurrentTurn().getActiveRow() > 7) {
                Launcher.mainFrame.getActiveTimer().suspendOn();

            }
        }
    }

}

    public void reset() {
        this.dt = this.dtreset + System.currentTimeMillis();
        this.keepCounting = true;
    }

    public void suspendOn() {
        this.keepCounting = false;
    }

    public long getRTime() {
        return c.getTimeInMillis();
    }

    public void minus(long t) {
        dt -= t;
    }

    public void major(long t) {
        dt += t;
    }
}
 */