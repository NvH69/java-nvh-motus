package com.nvh.motus.view;

import com.nvh.motus.Launcher;

import javax.swing.text.BadLocationException;
import java.awt.*;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AnimationPlay extends Thread {

    public final long dtreset;
    public boolean keepCounting;
    public long dt;
    public long nowTimer;
    public String LCD;
    public StringBuffer tempLCD;
    public Calendar c;
    private boolean isWinningTurn;
    private boolean keepWorking;


    public AnimationPlay() {
        this.isWinningTurn = false;
        this.keepWorking = false;
        this.dt = dt + System.currentTimeMillis();
        dtreset = dt;
        keepCounting = false;
        LCD = null;
        tempLCD = new StringBuffer("");
        this.c = Calendar.getInstance();
        start();
    }

    public void setWinningTurn(boolean winningTurn) {
        this.isWinningTurn = winningTurn;
    }

    @Override
    public void run() {
        while (true) {
            if (this.keepWorking) {
                nowTimer = System.currentTimeMillis();
                c.setTimeInMillis(dt - nowTimer);

//                limite temps atteinte : changement de joueur
                if (c.getTimeInMillis() < 1000 /*&& this == MainFrame.miniSwingTimer1 */&&
                        Launcher.mainFrame.game.getActivePlayer() == 0 &&
                        Launcher.mainFrame.getOffset() < Launcher.mainFrame.game.getWordLenght()) {
                    Launcher.mainFrame.ting.play();
                    this.suspendOn();
//                    Launcher.mainFrame.swapPlayer();
                }
                if (c.getTimeInMillis() < 1000 && /*this == MainFrame.miniSwingTimer2 &&*/
                        Launcher.mainFrame.game.getActivePlayer() == 1 &&
                        Launcher.mainFrame.getOffset() < Launcher.mainFrame.game.getWordLenght()) {
                    Launcher.mainFrame.ting.play();
                    this.suspendOn();
                    //                    Launcher.mainFrame.swapPlayer();
                }
            }

                //tour gagné
                if (Launcher.mainFrame.game.getCurrentTurn().isAllFound()) {
                    this.suspendOn();
                }
                //tour perdu
                if (Launcher.mainFrame.game.getCurrentTurn().getActiveRow() > 7) {
                    this.suspendOn();

                }

                if (this.isWinningTurn) {
                    int loopCount = 0;
                while (loopCount <4) {
                    for (int i = 0; i < Launcher.mainFrame.game.getWordLenght() - 1; i++) {
                        try {

                            Launcher.mainFrame.getActiveColorPane()
                                    .replace(Launcher.mainFrame.letterFont, Launcher.mainFrame.fontsize, Color.RED,
                                            Launcher.mainFrame.getActiveColorPane().getText(i, 1), i, i + 1);
                        } catch (BadLocationException e) {
                            e.printStackTrace();
                        }
                        int j = i + 1;
                        if (j > Launcher.mainFrame.game.getWordLenght()) j = 0;
                        try {
                            Launcher.mainFrame.getActiveColorPane()
                                    .replace(Launcher.mainFrame.letterFont, Launcher.mainFrame.fontsize,
                                            Color.RED.brighter(),
                                            Launcher.mainFrame.getActiveColorPane().getText(j, 1), j, j + 1);
                        } catch (BadLocationException e) {
                            e.printStackTrace();
                        }
                        int k = i - 1;
                        if (k < 0) k = Launcher.mainFrame.game.getWordLenght() - 1;
                        try {
                            Launcher.mainFrame.getActiveColorPane()
                                    .replace(Launcher.mainFrame.letterFont, Launcher.mainFrame.fontsize - 1,
                                            Color.RED.darker(), Launcher.mainFrame.getActiveColorPane().getText(k, 1), k, k + 1);
                        } catch (BadLocationException e) {
                            e.printStackTrace();
                        }
                        try {
                            if (Launcher.mainFrame.game.getWordLenght() == 9) Thread.sleep(68);
                            if (Launcher.mainFrame.game.getWordLenght() == 8) Thread.sleep(80);
                            if (Launcher.mainFrame.game.getWordLenght() == 7) Thread.sleep(96);
                            if (Launcher.mainFrame.game.getWordLenght() == 6) Thread.sleep(110);
                            if (Launcher.mainFrame.game.getWordLenght() == 5) Thread.sleep(136);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    Launcher.mainFrame.getActiveColorPane()
                            .replace(Launcher.mainFrame.letterFont, Launcher.mainFrame.fontsize,
                                    Color.RED, Launcher.mainFrame.game.getCurrentTurn().getWord(), 0,
                                    Launcher.mainFrame.game.getWordLenght());
                    loopCount++;
                }
                    this.isWinningTurn = false;
                }
            }
        }


    public void reset() {
        this.keepWorking = true;
    }

    public void suspendOn() {
        this.keepWorking = false;
    }
}
