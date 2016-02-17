package com.nvh.motus.view;

import com.nvh.motus.Launcher;

import javax.swing.text.BadLocationException;
import java.awt.*;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SwingTimer extends Thread {
    public final long dtreset;
    public boolean keepCounting;
    public long dt;
    public long nowTimer;
    public String LCD;
    public StringBuffer tempLCD;
    public Calendar c;

    boolean flagEnd = false;
    int nb_arpege;
    int ajout;


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


                if (c.getTimeInMillis() < 1000 && this == Launcher.miniSwingTimer1 && MainFrame.joueuractif == 1 && MainFrame.offset < MainFrame.L) {
                    MainFrame.ting.play();
                    this.suspendOn();
                    MainFrame.changeJoueur();
                }
                if (c.getTimeInMillis() < 1000 && this == Launcher.miniSwingTimer2 && MainFrame.joueuractif == 2 && MainFrame.offset < MainFrame.L) {
                    MainFrame.ting.play();
                    this.suspendOn();
                    MainFrame.changeJoueur();
                }
                //   else {}
            }
            if (MainFrame.flagAnim)


            {
                if (nb_arpege < 5) {
                    for (int i = 0; i < MainFrame.L - 1; i++)
                        try {
                            MainFrame.getActive().replace(MainFrame.fonte1, MainFrame.fontsize - 3, Color.RED, MainFrame.getActive().getText(i, 1), i, i + 1);
                            int j = i + 1;
                            if (j > MainFrame.L) j = 0;
                            MainFrame.getActive().replace(MainFrame.fonte1, MainFrame.fontsize + 1, Color.RED.brighter(), MainFrame.getActive().getText(j, 1), j, j + 1);
                            int k = i - 1;
                            if (k < 0) k = MainFrame.L - 1;
                            MainFrame.getActive().replace(MainFrame.fonte1, MainFrame.fontsize - 1, Color.RED.darker(), MainFrame.getActive().getText(k, 1), k, k + 1);
                            try {
                                if (MainFrame.L == 9) Thread.sleep(68);
                                if (MainFrame.L == 8) Thread.sleep(80);
                                if (MainFrame.L == 7) Thread.sleep(94);
                                if (MainFrame.L == 6) Thread.sleep(110);
                                if (MainFrame.L == 5) Thread.sleep(136);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(SwingTimer.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        } catch (BadLocationException ex) {
                            Logger.getLogger(SwingTimer.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    nb_arpege++;
                } else {
                    MainFrame.getActive().replace(MainFrame.fonte1, MainFrame.fontsize, Color.RED, MainFrame.reponse, 0, MainFrame.L);
                }
            } else nb_arpege = 0;


            if ((this == Launcher.miniSwingTimer1 && MainFrame.joueuractif == 1) || (this == Launcher.miniSwingTimer2 && MainFrame.joueuractif == 2)) {
                if ((MainFrame.score1 > 5 && (MainFrame.score1 - MainFrame.score2) > 1) || (MainFrame.score2 > 5 && (MainFrame.score2 - MainFrame.score1) > 1) || MainFrame.score1 > 9 || MainFrame.score2 > 9) {
                    MainFrame.offset = 0;
                    MainFrame.nombre_lettres = 0;
                    MainFrame.ligneactive = 0;
                    flagEnd = true;
                    MainFrame.jeuGagne();
                }

                if (MainFrame.nombre_lettres == MainFrame.L) {
                    MainFrame.getActiveTimer().suspendOn();
                    MainFrame.offset = 0;
                    MainFrame.nombre_lettres = 0;
                    MainFrame.gagne();
                }

                if (MainFrame.ligneactive > 7) {
                    MainFrame.getActiveTimer().suspendOn();
                    MainFrame.offset = 0;
                    MainFrame.nombre_lettres = 0;
                    MainFrame.ligneactive = 0;
                    MainFrame.perdu();
                }

                if (MainFrame.offset == MainFrame.L && MainFrame.getActiveTimer().getRTime() > 1000) {
                    MainFrame.offset = 0;
                    MainFrame.getActiveTimer().suspendOn();
                    MainFrame.testmot(MainFrame.getActive().getText().toUpperCase());
                }


                if (MainFrame.flagAnim2) {
                    MainFrame.getActive().setText("");
                    MainFrame.getActive().append(MainFrame.fonte1, MainFrame.fontsize, Color.RED, String.valueOf(MainFrame.reponse.charAt(0)).toUpperCase());
                    for (int x = 1; x < MainFrame.L; x++) {
                        if (MainFrame.trouve[x])
                            MainFrame.getActive().append(MainFrame.fonte1, MainFrame.fontsize, Color.white, String.valueOf(MainFrame.reponse.charAt(x)).toUpperCase());
                        else MainFrame.getActive().append(MainFrame.fonte1, MainFrame.fontsize, Color.white, " ");
                    }

                    for (int i = 0; i < MainFrame.L; i++) {
                        if (!MainFrame.trouve[i]) {
                            MainFrame.trouve[i] = true;
                            ajout = i;
                            break;
                        }
                    }


                    for (int j = 0; j < 255; j++) {
                        MainFrame.getActive().replace(MainFrame.fonte1, MainFrame.fontsize, new Color(j, j, j), String.valueOf(MainFrame.reponse.charAt(ajout)).toUpperCase(), ajout, ajout + 1);

                        MainFrame.ct.begin();
                        while (MainFrame.ct.get() < 4) Thread.yield();
                    }
                    MainFrame.getActive().replace(MainFrame.fonte1, MainFrame.fontsize, Color.white, String.valueOf(MainFrame.reponse.charAt(ajout)).toUpperCase(), ajout, ajout + 1);

                    MainFrame.flagAnim2 = false;

                }
                if (MainFrame.flagAnim3) {

                    for (int i = -128; i < 128; i++) {
                        MainFrame.boite_dialogue.setForeground(new Color(Math.abs(i) + 20, 0, 0));
                        MainFrame.ct.begin();
                        while (MainFrame.ct.get() < 40) Thread.yield();
                        if (!MainFrame.flagAnim3) break;
                    }
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
