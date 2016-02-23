package com.nvh.motus.view;

import com.nvh.motus.model.ColorPane;
import com.nvh.motus.model.Motus;
import com.nvh.motus.model.SampledSon;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainFrame extends JFrame implements KeyListener {

    final String letterFont = "DropCaps Sans";  //TODO : nom de fonte en fct du OS
    final String screenFont = "Arcade Rounded";
    final SampledSon bip_bp = new SampledSon("/BASSOONh.wav");
    final SampledSon bip_mp = new SampledSon("/BASSOON.wav");
    final SampledSon bip_np = new SampledSon("/BASSOONb.wav");
    final SampledSon perdu = new SampledSon("/fin_perdu.wav");
    //    final SampledSon ting = new SampledSon("/ting.wav");
//    final SampledSon jeugagne = new SampledSon("/rock_002.wav");
    final SampledSon applause = new SampledSon("/GAGNER.wav");
    //    final SampledSon change = new SampledSon("/Body.wav");
    public Motus game;
    java.util.List<ColorPane> lines = new ArrayList<>();
    java.util.Random random = new java.util.Random(System.currentTimeMillis());
    boolean flagDoubleChange = false;
    JLabel boite_score1 = new JLabel();
    JLabel boite_score2 = new JLabel();
    JLabel boite_dialogue = new JLabel();
    JPanel boite_centrale = new JPanel();
    int fontsize;
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    String intro = "<ESPACE> pour commencer";
    int dimbase;
    GridLayout layboite_centrale = new GridLayout(7, 1, 5, 5);
    GridBagLayout winlay = new GridBagLayout();
    AnimationPlay animationPlay = new AnimationPlay();
    boolean isBeginningLine;

    public MainFrame() {

        this.game = new Motus(2, 7, 9000, 300000); //TODO : paramètrage par choix utilisateur

        if (dim.width > 1440) {
            setSize(1440, 768);
            fontsize = 127;
        } else {
            fontsize = 91;
            setSize(1024, 600);
        }
        for (int i = 0; i < 7; i++)
            lines.add(new ColorPane());

        setLocation(dim.width / 2 - getWidth() / 2, dim.height / 2 - getHeight() / 2);
        setTitle("MOTUS");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setFocusableWindowState(true);
        getContentPane().setBackground(Color.black);
        getContentPane().setLayout(winlay);
        setFocusCycleRoot(false);

        //Tailles
        boite_centrale.setBorder(BorderFactory.createLineBorder(Color.blue.brighter(), 5));
        boite_score1.setBorder(BorderFactory.createLineBorder(Color.red, 5));
        //Divers
        boite_centrale.setBackground(Color.black);

        lines.get(6).setOpaque(true);

        boite_dialogue.setFont(new Font("Arcade Rounded", Font.PLAIN, 10));
        boite_dialogue.setBackground(Color.black);

        boite_score1.setFont(new Font("Console", Font.PLAIN, 150));
        boite_score1.setForeground(Color.orange);
        boite_score2.setFont(new Font("Console", Font.PLAIN, 150));
        boite_score2.setForeground(Color.blue.brighter());

        //Focus
        addKeyListener(this);
        boite_centrale.addKeyListener(this);

        //Disposition
        boite_centrale.setLayout(layboite_centrale);

        for (ColorPane line : lines) {
            line.setFocusable(false);
            line.addKeyListener(this);
            boite_centrale.add(line);
            line.setDebugGraphicsOptions(ColorPane.FRAMEBITS);

        }

        winlay.setConstraints(boite_centrale, new GridBagConstraints(1, 0, 1, 0, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
        winlay.setConstraints(boite_score1, new GridBagConstraints(0, 0, 1, 0, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
        winlay.setConstraints(boite_score2, new GridBagConstraints(2, 0, 1, 0, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
        winlay.setConstraints(boite_dialogue, new GridBagConstraints(1, 0, 1, 0, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
        add(boite_score1);
        add(boite_centrale);
        add(boite_score2);
        add(boite_dialogue);

        boite_dialogue.setText(intro);

        boite_dialogue.setFont(new Font(screenFont, Font.PLAIN, 30 - (9 - game.getWordLenght()) * 4));
        boite_dialogue.setOpaque(true);
        boite_centrale.setOpaque(false);

        boite_dialogue.setVisible(true);
        boite_centrale.setVisible(false);
        boite_score1.setVisible(false);
        boite_score2.setVisible(false);

        animationPlay.reset();
    }

    public ColorPane getActiveColorPane() {

        return lines.get(game.getCurrentTurn().getActiveRow());
    }

    public void newTurn() {

        //TODO : implémentation timers (général et par ligne)

        boite_score1.setText(String.valueOf(game.getScores().get(0)));
        boite_score2.setText(String.valueOf(game.getScores().get(1)));

        Dimension dbase;
        if (dim.width > 1440) {
            dimbase = (96 * game.getWordLenght()) + 6;
            dbase = new Dimension(dimbase, 85);
        } else {
            dimbase = (69 * game.getWordLenght()) + 6;
            dbase = new Dimension(dimbase, 64);
        }

        for (ColorPane line : lines) {
            line.setPreferredSize(dbase);
            line.setBorder(BorderFactory.createLineBorder(Color.white, 3));

            line.setBackground(Color.black);
            line.setText(null);
        }
        lines.get(6).setVisible(false);
        flagDoubleChange = false;

        lines.get(0).append(letterFont, fontsize, Color.RED, game.getCurrentTurn().getWord().charAt(0));
        for (int i = 0; i < game.getWordLenght() - 1; i++) lines.get(0).append(letterFont, fontsize, Color.white, " ");
        int trouve2 = random.nextInt(game.getWordLenght() - 2) + 1;
        if (game.getWordLenght() > 7) {
            lines.get(0).replace(letterFont, fontsize, Color.white, game.getCurrentTurn().getWord().charAt(trouve2), trouve2, trouve2 + 1);
            game.getCurrentTurn().getFoundLetters().set(trouve2, true);
        }
        //getActiveTimer().reset();
    }

    public void lineResultDisplay(ArrayList<Integer> results) {
        if (results == null) {//mot incorrect (orthographe)
            try {
                getActiveColorPane().replace(letterFont, fontsize, Color.DARK_GRAY,
                        getActiveColorPane().getText(1, game.getWordLenght() - 1), 1, game.getWordLenght());
            } catch (BadLocationException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            int index = 0;
            for (int test : results) {

                switch (test) {
                    case 2: {
                        charDisplay(getActiveColorPane().getText().charAt(index), Color.RED, index);
                        bip_bp.play();
                        break;
                    }

                    case 1: {
                        charDisplay(getActiveColorPane().getText().charAt(index), Color.ORANGE, index);
                        bip_mp.play();
                        break;
                    }
                    case 0: {
                        charDisplay(getActiveColorPane().getText().charAt(index), Color.WHITE, index);
                        bip_np.play();
                        break;
                    }
                }
                index++;
            }
        }

        if (game.getCurrentTurn().isAllFound()) turnVictory();
        else
            newLine();
//        getActiveTimer().reset();
    }

    public void newLine() {

        getActiveColorPane().setBorder(BorderFactory.createLineBorder(Color.white, 3));

        if (game.getCurrentTurn().getActiveRow() > 4) turnDefeat();
        else {
            game.newLine();

            getActiveColorPane().setBorder(BorderFactory.createLineBorder(Color.red, 3));
            getActiveColorPane().append(letterFont, fontsize, Color.RED, game.getCurrentTurn().getWord().charAt(0));
            for (int i = 1; i < game.getWordLenght(); i++) {
                if (game.getCurrentTurn().getFoundLetters().get(i))
                    getActiveColorPane().append(letterFont, fontsize, Color.white, game.getCurrentTurn().getWord().charAt(i));
                else getActiveColorPane().append(letterFont, fontsize, Color.white, " ");
            }
        }
        isBeginningLine = true;
    }

    public void turnVictory() {

        animationPlay.setWinningTurn(true);
        turnVictoryDisplay();
        game.scoreUp(game.getActivePlayer());
        controlScores();
        game.newTurn();
        newTurn();
    }

    private void controlScores() {
        //TODO : vérifier victoire en solo et en MP
    }

    public void turnVictoryDisplay() {
        //tour gagné : animation
        animationPlay.setWinningTurn(true);
    }

    public void turnDefeat() {
        turnDefeatDisplay();
        game.newTurn();
        newTurn();
    }

    public void turnDefeatDisplay() {
        perdu.play();
        lines.get(6).setText(null);
        lines.get(5).setBorder(BorderFactory.createLineBorder(Color.white, 3));
        lines.get(6).setBorder(BorderFactory.createLineBorder(Color.red, 3));
        lines.get(6).setVisible(true);
        for (int i = 0; i < game.getWordLenght(); i++) {
//            charDisplay(game.getCurrentTurn().getWord().charAt(i), Color.RED, i);
            lines.get(6).append(letterFont, fontsize, Color.RED, game.getCurrentTurn().getWord().charAt(i));
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            bip_bp.play();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getOffset() {
        int index = getActiveColorPane().getText().indexOf(' ');
        if (index == -1) return getActiveColorPane().getLettersCount();
        return index;
    }

    private void charDisplay(Character tempchar, Color color, int offset) {

        getActiveColorPane().replace(letterFont, fontsize, color, tempchar, offset, offset + 1);
    }

    private void controlTurn() {
        String toControl = getActiveColorPane().getText();
        java.util.List<Integer> results = game.getCurrentTurn().lineResult(toControl);
        lineResultDisplay((ArrayList<Integer>) results);
    }

    @Override
    public void keyPressed(java.awt.event.KeyEvent k) {//lettres
        if (k.getKeyCode() > 64 && k.getKeyCode() < 91 && getOffset() > 0 && getOffset() < game.getWordLenght() &&
                game.getCurrentTurn().getActiveRow() >= 0 && game.getCurrentTurn().getActiveRow() < 7
            /*&& getActiveTimer().getRTime() > 1000*/) {

            if (isBeginningLine) {//balaye les lettres pré-remplies
                for (byte i = 1; i < game.getWordLenght(); i++)
                    charDisplay(' ', Color.white, i);
                isBeginningLine = false;
            }
//            getActiveTimer().major(500);
            Character tempchar = Character.toUpperCase(k.getKeyChar());

            if (tempchar != game.getCurrentTurn().getWord().charAt(0) || getOffset() > 1) {
                charDisplay(tempchar, Color.white, getOffset());
            }
            if (getOffset() == game.getWordLenght()) {
                controlTurn();
            }
        }

        if (k.getKeyCode() == 32 && getOffset() == 0) {//espace
            boite_dialogue.setOpaque(false);
            boite_dialogue.setVisible(false);
            boite_centrale.setOpaque(true);
            boite_centrale.setVisible(true);
            boite_score1.setVisible(true);
            boite_score2.setVisible(true);
            newTurn();
        }

        if (k.getKeyCode() == 8 && getOffset() > 1) {//backspace
//            getActiveTimer().minus(500);
            getActiveColorPane().select(getOffset() - 1, getOffset());
            getActiveColorPane().replaceSelection(" ");
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }
}
