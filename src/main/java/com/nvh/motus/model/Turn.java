package com.nvh.motus.model;

import com.nvh.motus.service.Dictionaries;

import java.util.ArrayList;
import java.util.List;

public class Turn {

    public static java.util.Random random = new java.util.Random(System.currentTimeMillis());
    public int nbRows = 7;
    private int activeRow;
    private String wordToFind = null;
    private List<Boolean> foundLetters = new ArrayList<>();
    private Motus game;

    public Turn(Motus game) {
        this.activeRow = 0;
        this.foundLetters.add(true);
        for (int i = 1; i < game.getWordLenght(); i++) {
            this.foundLetters.add(false);
        }

        while (wordToFind == null) {

                wordToFind = Dictionaries.solutionsDictionary.get(nbRows).
                        get(random.nextInt(Dictionaries.solutionsDictionary.get(nbRows).size()));
        }
        System.out.println(wordToFind);
        this.game = game;
    }

    public int getActiveRow() {
        return activeRow;
    }

    public List<Boolean> getFoundLetters() {
        return foundLetters;
    }

    public CharSequence getWord() {
        return this.wordToFind;
    }

    public boolean isAllFound() {
        for (boolean check : this.getFoundLetters())
            if (!check) return false;
        return true;
    }

    public List<Integer> lineResult(String wordToTest) //renvoie null si ortho mauvaise, 1 sur les mp et 2 sur les bp
    {
        if (!isCorrectlySpelled(wordToTest)) return null;

        List<Integer> results = new ArrayList<>();
        for (int i = 0; i < wordToTest.length(); i++) {
            results.add(0);
        }

        results.set(0, 2);

        for (int i = 1; i < wordToTest.length(); i++) {// teste les lettres bien placées
            if (wordToTest.charAt(i) == game.getCurrentTurn().getWord().charAt(i)) {
                results.set(i, 2);
                this.getFoundLetters().set(i, true);
                game.getCurrentTurn().getFoundLetters().set(i, true);
            } else {
                results.set(i, 0);
            }
        }
        for (int i = 1; i < wordToTest.length(); i++) {//teste les lettres mal placées
            for (int j = 1; j < wordToTest.length(); j++) {
                if (wordToTest.charAt(i) == game.getCurrentTurn().getWord().charAt(j) && i != j && results.get(i) == 0 && !this.getFoundLetters().get(j)) {
                    results.set(i, 1);
                }
            }
        }
        return results;
    }

    public boolean isCorrectlySpelled(String wordToTest) {
        for (String word : Dictionaries.fullDictionary.get(this.game.getWordLenght()))
            if (wordToTest.equals(word)) return true;

        return false;
    }

    public void newLine() {
        this.activeRow++;
        this.foundLetters.set(0, true);
        for (int i = 1; i < game.getWordLenght(); i++) {
            this.foundLetters.set(i, false);
        }
    }

    public void mpWinningTurn() {
        if (this.game.getActivePlayer() == 0) this.game.getScores().set(0, this.game.getScores().get(0) + 1);
        else this.game.getScores().set(1, this.game.getScores().get(1) + 1);
    }
}
