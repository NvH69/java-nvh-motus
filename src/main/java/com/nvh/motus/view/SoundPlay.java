package com.nvh.motus.view;

import com.nvh.motus.Launcher;

public class SoundPlay extends Thread {

    private boolean isWinningTurn;
    private boolean keepWorking;

    public SoundPlay() {
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
                System.out.println("SoundPlay est en route !");
                if (this.isWinningTurn) {
                    Launcher.mainFrame.applause.play();
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
