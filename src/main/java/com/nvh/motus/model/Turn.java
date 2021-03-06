package com.nvh.motus.model;

import com.nvh.motus.service.Dictionaries;

import java.util.ArrayList;
import java.util.List;

public class Turn {

    private static java.util.Random random = new java.util.Random(System.currentTimeMillis());
    private int activeRow;
    private String wordToFind = null;
    private List<Boolean> foundLetters = new ArrayList<>();
    private Motus game;

    Turn(Motus game) {
        this.activeRow = 0;
        this.foundLetters.add(true);
        if (game.random) {
            int randomLenght = random.nextInt(5) + 5;
            game.setWordLenght(randomLenght);
        }
        wordToFind = Dictionaries.solutionsDictionary.get(game.getWordLenght()).
                get(random.nextInt(Dictionaries.solutionsDictionary.get(game.getWordLenght()).size()));
        this.game = game;
        for (int i = 1; i < game.getWordLenght(); i++) {
            this.foundLetters.add(false);
        }
    }

    public int getActiveRow() {
        return activeRow;
    }

    public List<Boolean> getFoundLetters() {
        return foundLetters;
    }

    public CharSequence getWordToFind() {
        return this.wordToFind;
    }

    public void setWordToFind(String wordToFind) {
        this.wordToFind = wordToFind;
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
        String wordToFind = this.game.getCurrentTurn().getWordToFind().toString();

        //initialisation du tableau réponse
        results.add(2);
        for (int i = 1; i < wordToTest.length(); i++) {
            results.add(0);
        }

        // teste les lettres bien placées
        for (int i = 1; i < wordToTest.length(); i++) {
            if (wordToTest.charAt(i) == wordToFind.charAt(i)) {
                results.set(i, 2);
                this.getFoundLetters().set(i, true);
            } else {
                results.set(i, 0);
            }
        }
        //teste les lettres mal placées
        for (int i = 1; i < wordToTest.length(); i++) {
            for (int j = 1; j < wordToFind.length(); j++) {
                if (i != j &&
                            results.get(i) == 0 &&
                                results.get(j) != 2 &&
                                    wordToTest.charAt(i) == wordToFind.charAt(j)
                    ) {
                    results.set(i, 1);
                    StringBuilder s = new StringBuilder(wordToFind);
                    s.replace(j,j+1," ");
                    wordToFind = s.toString();
                }
            }
        }
        return results;
    }

    private boolean isCorrectlySpelled(String wordToTest) {
        for (String word : Dictionaries.fullDictionary.get(this.game.getWordLenght()))
            if (wordToTest.equals(word)) return true;

        return false;
    }

    void newLine() {
        this.activeRow++;
    }

    public int getFoundSecondLetter() {
        return random.nextInt(game.getWordLenght() - 2) + 1;
    }

    /* Implémentation future (plusieurs joueurs)
    public void mpWinningTurn() {
        if (this.game.getActivePlayer() == 0) this.game.getScores().set(0, this.game.getScores().get(0) + 1);
        else this.game.getScores().set(1, this.game.getScores().get(1) + 1);
    }
    */
}
