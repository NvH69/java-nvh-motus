package com.nvh.motus.view.swing;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;

class AnimationPlay extends Thread {

    private long dt;
    private Calendar c;
    private boolean isWinningTurn;
    private MainFrame frame;
    private boolean isLosingTurn;

    /* Implémentations futures
    private String LCD;
    private boolean keepCounting;
    private StringBuffer tempLCD;
    private final long dtreset;
    */

    AnimationPlay(MainFrame frame) {
        this.isWinningTurn = false;
        this.isLosingTurn = false;
        this.dt = dt + System.currentTimeMillis();
        this.c = Calendar.getInstance();
        this.frame = frame;

        /*
        dtreset = dt;
        keepCounting = false;
        LCD = null;
        tempLCD = new StringBuffer("");
        */

        start();
    }

    void setWinningTurn(boolean winningTurn) {
        this.isWinningTurn = winningTurn;
    }

    void setLosingTurn(boolean losingTurn) {
        isLosingTurn = losingTurn;
    }

    @Override
    public void run() {
        while (true) {
            long nowTimer = System.currentTimeMillis();
            c.setTimeInMillis(dt - nowTimer);

////                limite temps atteinte : changement de joueur
//            if (c.getTimeInMillis() < 1000 /*&& this == MainFrame.miniSwingTimer1 */ &&
//                    frame.game.getActivePlayer() == 0 &&
//                    frame.getOffset() < frame.game.getWordLenght()) {
//                frame.ting.play();
//                this.suspendOn();
////                    frame.swapPlayer();
//            }
//            if (c.getTimeInMillis() < 1000 && /*this == MainFrame.miniSwingTimer2 &&*/
//                    frame.game.getActivePlayer() == 1 &&
//                    frame.getOffset() < frame.game.getWordLenght()) {
//                frame.ting.play();
//                this.suspendOn();
//                //                    frame.swapPlayer();
//            }

            if (this.isWinningTurn) {
                for (int color = 0; color < 255; color++) {
                    frame.getActiveColorPane().replace(frame.letterFont, frame.letterSize, new Color(255 - color, 0, color),
                            frame.getActiveColorPane().getText(), 0, frame.game.getWordLenght());
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                this.isWinningTurn = false;
            }

            if (this.isLosingTurn) {
                frame.lines.get(6).setText(frame.game.getCurrentTurn().getWord().toString());
                frame.lines.get(5).setBorder(BorderFactory.createLineBorder(Color.white, 3));
                frame.lines.get(6).setBorder(BorderFactory.createLineBorder(Color.red, 3));
                frame.lines.get(6).setVisible(true);
                for (int color = 0; color < 255; color++) {
                    frame.lines.get(6).replace(frame.letterFont, frame.letterSize, new Color(color, 0, 0),
                            frame.game.getCurrentTurn().getWord(), 0, frame.game.getWordLenght());
                    try {
                        Thread.sleep(15);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                this.isLosingTurn = false;
            }
        }
    }
}
