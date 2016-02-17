package com.nvh.motus.view;

import com.nvh.motus.Launcher;
import com.nvh.motus.model.ColorPane;
import com.nvh.motus.model.SampledSon;
import com.nvh.motus.service.Chrono;
import com.nvh.motus.service.Dictionaries;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.KeyListener;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainFrame extends JFrame implements KeyListener {

    public static final String fonte1 = "DropCaps Sans";
    public static final SampledSon ting = new SampledSon( "/ting.wav");
    public static final SampledSon bip_bp = new SampledSon( "/BASSOONh.wav");
    public static final SampledSon bip_mp = new SampledSon( "/BASSOON.wav");
    public static final SampledSon bip_np = new SampledSon( "/BASSOONb.wav");
    public static final SampledSon applause = new SampledSon( "/GAGNER.wav");
    public static final SampledSon perdu = new SampledSon( "/fin_perdu.wav");
    public static final SampledSon jeugagne = new SampledSon( "/rock_002.wav");
    public static final SampledSon change = new SampledSon( "/Body.wav");
    public static java.util.Random random = new java.util.Random(System.currentTimeMillis());
    public static boolean flagAnim, flagAnim2, flagAnim3 = true, flagDoubleChange = false;
    public static JLabel boite_score1 = new JLabel();
    public static JLabel boite_score2 = new JLabel();
    public static JLabel boite_dialogue = new JLabel();
    public static JPanel boite_centrale = new JPanel();
    public static ColorPane ligne1 = new ColorPane();
    public static ColorPane ligne2 = new ColorPane();
    public static ColorPane ligne3 = new ColorPane();
    public static ColorPane ligne4 = new ColorPane();
    public static ColorPane ligne5 = new ColorPane();
    public static ColorPane ligne6 = new ColorPane();
    public static ColorPane ligne7 = new ColorPane();
    public static int fontsize;
    static Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    //Variables de jeu
    static short ligneactive, offset = 0;
    static String reponse, intro = "<ESPACE> pour commencer";
    static char tempchar;
    static boolean[] temps = new boolean[9];
    static int[] tempreponse = new int[9];
    static boolean[] trouve = new boolean[9];
    static int L = 7, score1 = 0, score2 = 0, nombre_lettres, joueuractif = 1;
    static Chrono ct = new Chrono(0, false);
    static int dimbase;
    GridLayout layboite_centrale = new GridLayout(7, 1, 5, 5);
    GridBagLayout winlay = new GridBagLayout();

    public MainFrame() {

        if (dim.width > 1440) {
            setSize(1440, 768);
            fontsize = 128;
        } else {
            fontsize = 92;
            setSize(1024, 600);
        }

        setLocation(dim.width / 2 - getWidth() / 2, dim.height / 2 - getHeight() / 2);
        setTitle("MOTUS");
        //setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setFocusableWindowState(true);
        getContentPane().setBackground(Color.black);
        getContentPane().setLayout(winlay);
        setFocusCycleRoot(false);

        //Tailles
        boite_centrale.setBorder(BorderFactory.createLineBorder(Color.blue.brighter(), 20));
        boite_score1.setBorder(BorderFactory.createLineBorder(Color.red, 5));
        //Divers
        boite_centrale.setBackground(Color.black);
        ligne1.setFocusable(false);
        ligne2.setFocusable(false);
        ligne3.setFocusable(false);
        ligne4.setFocusable(false);
        ligne5.setFocusable(false);
        ligne6.setFocusable(false);
        ligne7.setFocusable(false);
        ligne7.setOpaque(true);

        boite_dialogue.setFont(new Font("Arcade Rounded", Font.PLAIN, 10));
        boite_dialogue.setBackground(Color.black);

        boite_score1.setFont(new Font("Console", Font.PLAIN, 150));
        boite_score1.setForeground(Color.orange);
        boite_score2.setFont(new Font("Console", Font.PLAIN, 150));
        boite_score2.setForeground(Color.blue.brighter());

        //Focus
        addKeyListener(this);
        boite_centrale.addKeyListener(this);
        ligne1.addKeyListener(this);
        ligne2.addKeyListener(this);
        ligne3.addKeyListener(this);
        ligne4.addKeyListener(this);
        ligne5.addKeyListener(this);
        ligne6.addKeyListener(this);
        ligne7.addKeyListener(this);

        //Disposition
        boite_centrale.setLayout(layboite_centrale);
        boite_centrale.add(ligne1);
        boite_centrale.add(ligne2);
        boite_centrale.add(ligne3);
        boite_centrale.add(ligne4);
        boite_centrale.add(ligne5);
        boite_centrale.add(ligne6);
        boite_centrale.add(ligne7);


        winlay.setConstraints(boite_centrale, new GridBagConstraints(1, 0, 1, 0, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
        winlay.setConstraints(boite_score1, new GridBagConstraints(0, 0, 1, 0, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
        winlay.setConstraints(boite_score2, new GridBagConstraints(2, 0, 1, 0, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
        winlay.setConstraints(boite_dialogue, new GridBagConstraints(1, 0, 1, 0, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
        add(boite_score1);
        add(boite_centrale);
        add(boite_score2);
        add(boite_dialogue);
        //pack();


        boite_dialogue.setText(intro);

        boite_dialogue.setFont(new Font("Arcade Rounded", Font.PLAIN, 30 - (9 - L) * 4));
        boite_dialogue.setOpaque(true);
        boite_centrale.setOpaque(false);

        boite_dialogue.setVisible(true);
        boite_centrale.setVisible(false);
        boite_score1.setVisible(false);
        boite_score2.setVisible(false);


    }

    public static ColorPane getActive() {

        if (ligneactive == 1) return ligne1;
        else if (ligneactive == 2) return ligne2;
        else if (ligneactive == 3) return ligne3;
        else if (ligneactive == 4) return ligne4;
        else if (ligneactive == 5) return ligne5;
        else if (ligneactive == 6) return ligne6;
        else if (ligneactive == 7) return ligne7;
        else
            return ligne7;
    }

    public static SwingTimer getActiveTimer() {
        if (joueuractif == 1) {
            Launcher.miniSwingTimer2.suspendOn();
            return Launcher.miniSwingTimer1;
        } else {
            Launcher.miniSwingTimer1.suspendOn();
            return Launcher.miniSwingTimer2;
        }
    }

    public static void reset() {
        reponse = null;
        if (Launcher.arg0 > 4 && Launcher.arg0 < 10) L = Launcher.arg0;
        if (Launcher.arg0 == 1) L = random.nextInt(5) + 5;


        boite_score1.setText(String.valueOf(score1));
        boite_score2.setText(String.valueOf(score2));


        while (reponse == null) {
            if (((score1 - score2) > 1 && joueuractif == 1) || ((score2 - score1) > 1 && joueuractif == 2)) reponse =
                    Dictionaries.fullDictionary.get(L).get(random.nextInt(Dictionaries.fullDictionary.get(L).size()));
            else reponse = Dictionaries.solutionsDictionary.get(L).get(random.nextInt(Dictionaries.solutionsDictionary.get(L).size()));
        }
//System.out.println(reponse);

        Dimension dbase;
        if (dim.width > 1440) {
            dimbase = (96 * L) + 6;
            dbase = new Dimension(dimbase, 85);
        } else {
            dimbase = (69 * L) + 6;
            dbase = new Dimension(dimbase, 64);
        }

        getActiveTimer().reset();
        ligneactive = 1;
        offset = 1;
        flagDoubleChange = false;

        trouve = new boolean[L];
        trouve[0] = true;


        ligne1.setPreferredSize(dbase);
        ligne2.setPreferredSize(dbase);
        ligne3.setPreferredSize(dbase);
        ligne4.setPreferredSize(dbase);
        ligne5.setPreferredSize(dbase);
        ligne6.setPreferredSize(dbase);
        ligne7.setPreferredSize(dbase);

        ligne1.setBorder(BorderFactory.createLineBorder(Color.red, 3));
        ligne2.setBorder(BorderFactory.createLineBorder(Color.white, 3));
        ligne3.setBorder(BorderFactory.createLineBorder(Color.white, 3));
        ligne4.setBorder(BorderFactory.createLineBorder(Color.white, 3));
        ligne5.setBorder(BorderFactory.createLineBorder(Color.white, 3));
        ligne6.setBorder(BorderFactory.createLineBorder(Color.white, 3));
        ligne7.setBorder(BorderFactory.createLineBorder(Color.white, 3));


        ligne1.setBackground(Color.black);
        ligne1.setText(null);
        ligne2.setBackground(Color.black);
        ligne2.setText(null);
        ligne3.setBackground(Color.black);
        ligne3.setText(null);
        ligne4.setBackground(Color.black);
        ligne4.setText(null);
        ligne5.setBackground(Color.black);
        ligne5.setText(null);
        ligne6.setBackground(Color.black);
        ligne6.setText(null);
        ligne7.setBackground(Color.black);
        ligne7.setText(null);

        ligne7.setVisible(false);

        ligne1.append(fonte1, fontsize, Color.RED, String.valueOf(reponse.charAt(0)).toUpperCase());
        for (int i = 0; i < L - 1; i++) ligne1.append(fonte1, fontsize, Color.white, " ");
        int trouve2 = random.nextInt(L - 2) + 1;
        if (L > 7) {
            ligne1.replace(fonte1, fontsize, Color.white, String.valueOf(reponse.charAt(trouve2)), trouve2, trouve2 + 1);
            trouve[trouve2] = true;
        }


    }

    public static void testmot(String s)

    {
        nombre_lettres = 0;
        if (Ortho(s))

        {
            tempreponse[0] = 2;
            int nbtest = 0;
            for (int i = 1; i < s.length(); i++) {
                if (s.charAt(i) == reponse.charAt(i)) {
                    tempreponse[i] = 2;
                    temps[i] = true;
                    trouve[i] = true;
                } else {
                    tempreponse[i] = 0;
                    temps[i] = false;
                }
            }
            for (int i = 1; i < s.length(); i++) {
                for (int j = 1; j < s.length(); j++) {
                    if (s.charAt(i) == reponse.charAt(j) && i != j && tempreponse[i] == 0 && !temps[j]) {
                        tempreponse[i] = 1;
                        temps[j] = true;
                    }
                }
            }


            for (int i = 0; i < s.length(); i++) {
                if (tempreponse[i] == 2) {
                    getActive().replace(fonte1, fontsize, Color.RED, String.valueOf(s.charAt(i)).toUpperCase(), i, i + 1);
                    bip_bp.play();
                    nombre_lettres++;
                    nbtest++;
                }
                if (tempreponse[i] == 1) {
                    getActive().replace(fonte1, fontsize, Color.ORANGE, String.valueOf(s.charAt(i)), i, i + 1);
                    bip_mp.play();
                }
                if (tempreponse[i] == 0) {
                    getActive().replace(fonte1, fontsize, Color.WHITE, String.valueOf(s.charAt(i)), i, i + 1);
                    bip_np.play();
                }


                if (nbtest == L) return;
            }
        } else {
            try {
                getActive().replace(fonte1, fontsize, Color.DARK_GRAY, getActive().getText(1, L - 1), 1, L);
            } catch (BadLocationException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }

            changeJoueur();
            return;
        }

        if (ligneactive > 5) {
            changeJoueur();

            return;
        }


        offset = 1;

        getActive().setBorder(BorderFactory.createLineBorder(Color.white, 3));
        ligneactive++;
        getActive().setBorder(BorderFactory.createLineBorder(Color.red, 3));
        getActive().append(fonte1, fontsize, Color.RED, String.valueOf(reponse.charAt(0)).toUpperCase());
        for (int i = 1; i < L; i++) {
            if (trouve[i])
                getActive().append(fonte1, fontsize, Color.white, String.valueOf(reponse.charAt(i)).toUpperCase());
            else getActive().append(fonte1, fontsize, Color.white, " ");
        }
        getActiveTimer().reset();
    }

    public static void gagne() {
        Launcher.miniSwingTimer1.suspendOn();
        Launcher.miniSwingTimer2.suspendOn();
        flagAnim = true;
        applause.play();
        flagAnim = false;
        if (joueuractif == 1) score1++;
        else score2++;
        boite_score1.setText(String.valueOf(score1));
        boite_score2.setText(String.valueOf(score2));
        if (L < 9) L++;
//ct.begin(); while (ct.get()<300) Thread.yield();

        if ((score1 > 5 && (score1 - score2) > 1) || (score2 > 5 && (score2 - score1) > 1) || score1 > 9 || score2 > 9)
            return;


        boite_dialogue.setText("<ESPACE> pour mot suivant");
        boite_dialogue.setFont(new Font("Arcade Rounded", Font.PLAIN, 20 - (9 - L) * 4));
        boite_dialogue.setOpaque(true);
        boite_centrale.setOpaque(false);

        boite_dialogue.setVisible(true);
        flagAnim3 = true;

    }

    public static void perdu() {
        Launcher.miniSwingTimer1.suspendOn();
        Launcher.miniSwingTimer2.suspendOn();
        perdu.play();
        ligne7.setText(null);
        ligne6.setBorder(BorderFactory.createLineBorder(Color.white, 3));
        ligne7.setBorder(BorderFactory.createLineBorder(Color.red, 3));
        ligne7.setVisible(true);
        for (int i = 0; i < reponse.length(); i++) {
            ligne7.append(fonte1, fontsize, Color.RED, String.valueOf(reponse.charAt(i)).toUpperCase());
            bip_bp.play();
        }
        if (L > 5) L--;
// ct.begin(); while (ct.get()<1500) Thread.yield(); reset();   si pas de pause
        boite_dialogue.setText("<ESPACE> pour mot suivant");
        boite_dialogue.setFont(new Font("Arcade Rounded", Font.PLAIN, 30 - (9 - L) * 4));
        boite_dialogue.setOpaque(true);
        boite_centrale.setOpaque(false);

        boite_dialogue.setVisible(true);
        flagAnim3 = true;
    }

    public static void changeJoueur() {
        flagDoubleChange = !flagDoubleChange;

        if (ligneactive < 7) {
            offset = 0;
            perdu.play();
            boite_centrale.setVisible(false);
            boite_score1.setVisible(false);
            boite_score2.setVisible(false);
            boite_dialogue.setVisible(true);
            if (joueuractif == 1) {
                boite_dialogue.setText("Joueur 2");
                boite_dialogue.setForeground(Color.blue.brighter());
            } else {
                boite_dialogue.setText("Joueur 1");
                boite_dialogue.setForeground(Color.orange);
            }
            for (int i = 0; i < 78; i++) {
                boite_dialogue.setFont(new Font("Arcade Rounded", Font.PLAIN, 10 + i));
                ct.begin();
                while (ct.get() < 50) Thread.yield();
            }
            boite_centrale.setVisible(true);
            boite_score1.setVisible(true);
            boite_score2.setVisible(true);
            boite_dialogue.setVisible(false);
            joueuractif++;
            if (joueuractif > 2) joueuractif = 1;
            if (joueuractif == 1) {
                boite_score1.setBorder(BorderFactory.createLineBorder(Color.red, 5));
                boite_score2.setBorder(BorderFactory.createLineBorder(Color.red, 0));
            } else {
                boite_score2.setBorder(BorderFactory.createLineBorder(Color.red, 5));
                boite_score1.setBorder(BorderFactory.createLineBorder(Color.red, 0));
            }
            if (ligneactive == 6 || !flagDoubleChange) {
                getActive().setBorder(BorderFactory.createLineBorder(Color.white, 3));
                ligneactive++;
                getActive().setBorder(BorderFactory.createLineBorder(Color.red, 3));
                if (ligneactive == 7) ligne7.setVisible(true);
            }
            int j = 0;
            for (int i = 0; i < L; i++) {
                if (trouve[i]) j++;
            }
            if (j < L - 1) flagAnim2 = true;
            else {
                getActive().setText("");
                getActive().append(fonte1, fontsize, Color.RED, String.valueOf(reponse.charAt(0)).toUpperCase());
                for (int i = 1; i < reponse.length(); i++) {
                    if (trouve[i])
                        getActive().append(fonte1, fontsize, Color.white, String.valueOf(reponse.charAt(i)).toUpperCase());
                    else getActive().append(fonte1, fontsize, Color.white, " ");
                }
            }
            int i = 0;
            while (flagAnim2) {
                if (i == 0) change.play();
                i++;
            }
            getActiveTimer().reset();
            offset = 1;
        } else perdu();

    }

    public static void jeuGagne() {
        getActiveTimer().suspendOn();

        if ((score1 > 5 && (score1 - score2) > 1) || score1 > 9) boite_score1.setText("œ");
        else boite_score2.setText("œ");
        while (true) {
            jeugagne.play();
            ct.begin();
            while (ct.get() < 5000) Thread.yield();
        }
    }

    public static boolean Ortho(String x) {
        for (String word : Dictionaries.fullDictionary.get(L))
            if (x.equals(word)) return true;

        return false;
    }

    public void keyPressed(java.awt.event.KeyEvent k) {
        if (k.getKeyCode() > 64 && k.getKeyCode() < 91 && offset > 0 && offset < L && ligneactive > 0 && ligneactive < 8 && getActiveTimer().getRTime() > 1000) {
            if (offset == 1)
                for (byte i = 1; i < L; i++) getActive().replace(fonte1, fontsize, Color.white, " ", i, i + 1);
            getActiveTimer().major(500);
            tempchar = Character.toUpperCase(k.getKeyChar());
            if (tempchar != reponse.charAt(0) || offset > 1) {
                getActive().replace(fonte1, fontsize, Color.white, String.valueOf(tempchar), offset, offset + 1);
                offset++;
            }

        }//lettres

   /* if (k.getKeyCode() == 10 && offset==L) {
                                if (Launcher.miniTimer.getRTime()>1000)
                                {if (ligneactive<7) {
                                   Launcher.miniTimer.reset();
                                   testmot(getActive().getText().toUpperCase());
                                           }
                                }

                                                                }*/   //entrée

        if (k.getKeyCode() == 32 && flagAnim3)

        {
            boite_dialogue.setOpaque(false);
            boite_dialogue.setVisible(false);
            boite_centrale.setOpaque(true);
            flagAnim3 = false;
            boite_centrale.setVisible(true);
            boite_score1.setVisible(true);
            boite_score2.setVisible(true);
            reset();
        }//espace

        // if (k.getKeyCode() == 27) {if (!Launcher.mainTimer.keepCounting) System.exit(0);}//esc

        if (k.getKeyCode() == 8 && offset > 1) {
            getActiveTimer().minus(500);
            getActive().select(offset - 1, offset);
            getActive().replaceSelection(" ");
            offset--;
        }//backspace
    }

    public void keyReleased(java.awt.event.KeyEvent keyEvent) {
    }

    public void keyTyped(java.awt.event.KeyEvent keyEvent) {
    }
}

