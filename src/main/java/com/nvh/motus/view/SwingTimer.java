//package com.nvh.motus.view;
//
//import com.nvh.motus.Launcher;
//
//import java.util.ArrayList;
//import java.util.Calendar;
//
//public class SwingTimer extends Thread {
//    public final long dtreset;
//    public boolean keepCounting;
//    public long dt;
//    public long nowTimer;
//    public String LCD;
//    public StringBuffer tempLCD;
//    public Calendar c;
//
//    public SwingTimer(long dt) {
//        this.dt = dt + System.currentTimeMillis();
//        dtreset = dt;
//        keepCounting = false;
//        LCD = null;
//        tempLCD = new StringBuffer("");
//        this.c = Calendar.getInstance();
//        start();
//    }
//
//    @Override
//    public void run() {
//        while (true) {
//
//            if (keepCounting) {
//                nowTimer = System.currentTimeMillis();
//                c.setTimeInMillis(dt - nowTimer);
//
//                //limite temps atteinte : chnagement de joueur
//                if (c.getTimeInMillis() < 1000 && this == MainFrame.miniSwingTimer1 &&
//                        Launcher.mainFrame.game.getActivePlayer() == 0 &&
//                        Launcher.mainFrame.offset < Launcher.mainFrame.game.getWordLenght()) {
//                    Launcher.mainFrame.ting.play();
//                    this.suspendOn();
//                    Launcher.mainFrame.swapPlayer();
//                }
//                if (c.getTimeInMillis() < 1000 && this == MainFrame.miniSwingTimer2 &&
//                        Launcher.mainFrame.game.getActivePlayer() == 1 &&
//                        Launcher.mainFrame.offset < Launcher.mainFrame.game.getWordLenght()) {
//                    Launcher.mainFrame.ting.play();
//                    this.suspendOn();
//                    Launcher.mainFrame.swapPlayer();
//                }
//            }
//
//            //Jeu gagné
//            if ((this == MainFrame.miniSwingTimer1 && Launcher.mainFrame.game.getActivePlayer() == 0) ||
//                    (this == MainFrame.miniSwingTimer2 && Launcher.mainFrame.game.getActivePlayer() == 1)) {
//                if ((Launcher.mainFrame.game.getScores().get(0) > 5 && (Launcher.mainFrame.game.getScores().get(0) -
//                        Launcher.mainFrame.game.getScores().get(1)) > 1) ||
//                        (Launcher.mainFrame.game.getScores().get(1) > 5 &&
//                                (Launcher.mainFrame.game.getScores().get(1) -
//                                        Launcher.mainFrame.game.getScores().get(0)) > 1) ||
//                        Launcher.mainFrame.game.getScores().get(0) > 9 ||
//                        Launcher.mainFrame.game.getScores().get(1) > 9) {
//                    Launcher.mainFrame.offset = 0;
//                    Launcher.mainFrame.game.getCurrentTurn().setActiveRow(0);
//                    Launcher.mainFrame.gameMPVictory();
//                }
//                //tour gagné
//                if (Launcher.mainFrame.game.getCurrentTurn().isAllFound()) {
//                    Launcher.mainFrame.getActiveTimer().suspendOn();
//                }
//                //tour perdu
//                if (Launcher.mainFrame.game.getCurrentTurn().getActiveRow() > 7) {
//                    Launcher.mainFrame.getActiveTimer().suspendOn();
//
//                }
//                //case remplie => vérif mot
//                if (Launcher.mainFrame.offset == Launcher.mainFrame.game.getWordLenght()
//                        && Launcher.mainFrame.getActiveTimer().getRTime() > 1000) {
//                    Launcher.mainFrame.offset = 0;
//                    Launcher.mainFrame.getActiveTimer().suspendOn();
//                    Launcher.mainFrame.lineResultDisplay
//                            ((ArrayList<Integer>) Launcher.mainFrame.game.getCurrentTurn()
//                                    .lineResult(Launcher.mainFrame.getActiveColorPane().getText().toUpperCase()));
//                    if (Launcher.mainFrame.game.getCurrentTurn().getActiveRow() >= 7) {
//                        Launcher.mainFrame.turnDefeat();
//                    }
//                }
//            }
//        }
//    }
//
//    public void reset() {
//        this.dt = this.dtreset + System.currentTimeMillis();
//        this.keepCounting = true;
//    }
//
//    public void suspendOn() {
//        this.keepCounting = false;
//    }
//
//    public long getRTime() {
//        return c.getTimeInMillis();
//    }
//
//    public void minus(long t) {
//        dt -= t;
//    }
//
//    public void major(long t) {
//        dt += t;
//    }
//}
