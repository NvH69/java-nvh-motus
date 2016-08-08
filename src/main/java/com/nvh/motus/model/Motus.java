package com.nvh.motus.model;

import java.util.ArrayList;
import java.util.List;

public class Motus {

    private int activePlayer;
    private int wordLenght;
    private List<Integer> scores = new ArrayList<>();
    private Turn currentTurn;
    boolean random;

    public Motus(int wordLenght, boolean random /* , int nbPlayers, long timePerRow1, long timePerRow2*/) {
        this.wordLenght = wordLenght;
        this.scores.add(0, 0);
        this.scores.add(1, 0);
        this.activePlayer = 0;
        this.currentTurn = new Turn(this);
        this.random = random;
        /* A venir
        int nbPlayers1 = nbPlayers;
        boolean alive = true;
        long timePerRound = timePerRow1;
        long timePerGame = timePerRow2;
        */
    }

    public int getWordLenght() {
        return wordLenght;
    }

    void setWordLenght(int wordLenght) {
        this.wordLenght = wordLenght;
    }

    public int getActivePlayer() {
        return activePlayer;
    }

    private void setActivePlayer(int activePlayer) {
        this.activePlayer = activePlayer;
    }

    public void scoreUp(int player) {
        this.scores.set(player, this.scores.get(player) + 1);
    }

    public Turn getCurrentTurn() {
        return currentTurn;
    }

    public List<Integer> getScores() {
        return scores;
    }

    private void changeActivePlayer() {
        if (this.getActivePlayer() == 0) this.setActivePlayer(1);
        else this.setActivePlayer(0);
    }

    /* Implémentations à venir

    public int getNbPlayers() {
        return nbPlayers;
    }

    public void setNbPlayers(int nbPlayers) {
        this.nbPlayers = nbPlayers;
    }

    public long getTimePerRound() {
        return timePerRound;
    }

    public void setTimePerRound(long timePerRound) {
        this.timePerRound = timePerRound;
    }

    public long getTimePerGame() {
        return timePerGame;
    }

    public void setTimePerGame(long timePerGame) {
        this.timePerGame = timePerGame;
    }

    public int getScoreDifference() {
        return this.scores.get(0) - this.scores.get(1);
    }

    public boolean isAlive() {
        return alive;
    }
    */

    public void newLine() {
        this.getCurrentTurn().newLine();
    }

    public void newTurn() {
        this.currentTurn = new Turn(this);
        changeActivePlayer();
    }
}
