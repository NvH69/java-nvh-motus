package com.nvh.motus.view;

import com.nvh.motus.Launcher;

import javax.swing.text.BadLocationException;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AnimationPlay extends Thread {

    private boolean isWinningTurn;
    private boolean keepWorking;

    public AnimationPlay() {
        this.isWinningTurn = false;
        this.keepWorking = false;
        start();
    }

    public void setWinningTurn(boolean winningTurn) {
        this.isWinningTurn = winningTurn;
    }

    @Override
    public void run() {
        while (true) {
            if (this.keepWorking) {
                System.out.println("AnimationPlay est en route !");
                if (this.isWinningTurn) {
                    Launcher.mainFrame.applause.play();
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
                            if (Launcher.mainFrame.game.getWordLenght() == 9) Thread.sleep(136);
                            if (Launcher.mainFrame.game.getWordLenght() == 8) Thread.sleep(160);
                            if (Launcher.mainFrame.game.getWordLenght() == 7) Thread.sleep(188);
                            if (Launcher.mainFrame.game.getWordLenght() == 6) Thread.sleep(220);
                            if (Launcher.mainFrame.game.getWordLenght() == 5) Thread.sleep(272);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    Launcher.mainFrame.getActiveColorPane()
                            .replace(Launcher.mainFrame.letterFont, Launcher.mainFrame.fontsize,
                                    Color.RED, Launcher.mainFrame.game.getCurrentTurn().getWord(), 0,
                                    Launcher.mainFrame.game.getWordLenght());
                    this.isWinningTurn = false;
                }
            }
        }
    }

    public void reset() {
        this.keepWorking = true;
    }

    public void pause() {
        this.keepWorking = false;
    }
}
