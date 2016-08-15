package com.nvh.motus.view.swing;

/**
 * Vue pour plueiseurs joueurs à implémenter

public class MultiplayerView {
public static Timer miniSwingTimer1, miniSwingTimer2;

    public void mpWin() {
        miniSwingTimer1.suspendOn();
        miniSwingTimer2.suspendOn();
        wonTurn.play();

        if ((game.getScores().get(0) > 5 && (game.getScores().get(0) - game.getScores().get(1)) > 1) ||
                (game.getScores().get(1) > 5 && (game.getScores().get(1) - game.getScores().get(0)) > 1) ||
                game.getScores().get(0) > 9 || game.getScores().get(1) > 9) return;

        boite_score1.setText(String.valueOf(game.getScores().get(0)));
        boite_score2.setText(String.valueOf(game.getScores().get(1)));
        if (game.getWordLenght() < 9) game.setWordLenght(game.getWordLenght() + 1);
        boite_dialogue.setText("<ESPACE> pour mot suivant");
        boite_dialogue.setFont(new Font("Arcade Rounded", Font.PLAIN, 20 - (9 - game.getWordLenght()) * 4));
        boite_dialogue.setOpaque(true);
        boite_centrale.setOpaque(false);
        boite_dialogue.setVisible(true);
    }

    public void mpLose() {
        miniSwingTimer1.suspendOn();
        miniSwingTimer2.suspendOn();
        lostTurn.play();
        lines.get(6).setText(null);
        lines.get(5).setBorder(BorderFactory.createLineBorder(Color.white, 3));
        lines.get(6).setBorder(BorderFactory.createLineBorder(Color.red, 3));
        lines.get(6).setVisible(true);
        for (int i = 0; i < game.getWordLenght(); i++) {
            lines.get(6).append(letterFont, fontsize, Color.RED, String.valueOf(game.getCurrentTurn().getWord().charAt(i)).toUpperCase());
            bip_bp.play();
        }
        if (game.getWordLenght() > 5) game.setWordLenght(game.getWordLenght() - 1);
        boite_dialogue.setText("<ESPACE> pour mot suivant");
        boite_dialogue.setFont(new Font("Arcade Rounded", Font.PLAIN, 30 - (9 - game.getWordLenght()) * 4));
        boite_dialogue.setOpaque(true);
        boite_centrale.setOpaque(false);

        boite_dialogue.setVisible(true);
        turnVictory();
    }

    public void swapPlayer() {
        flagDoubleChange = !flagDoubleChange;
        int charIndexToAdd = 0;
        if (game.getCurrentTurn().getActiveRow() < 7) {
            offset = 1;
            lostTurn.play();
            boite_centrale.setVisible(false);
            boite_score1.setVisible(false);
            boite_score2.setVisible(false);
            boite_dialogue.setVisible(true);
            if (game.getActivePlayer() == 0) {
                boite_dialogue.setText("Joueur 2");
                boite_dialogue.setForeground(Color.blue.brighter());
            } else {
                boite_dialogue.setText("Joueur 1");
                boite_dialogue.setForeground(Color.orange);
            }
            for (int i = 0; i < 78; i++) {
                boite_dialogue.setFont(new Font(screenFont, Font.PLAIN, 10 + i));
                ct.begin();
                while (ct.get() < 50) Thread.yield();
            }
            boite_centrale.setVisible(true);
            boite_score1.setVisible(true);
            boite_score2.setVisible(true);
            boite_dialogue.setVisible(false);

            if (game.getActivePlayer() == 0) {
                boite_score1.setBorder(BorderFactory.createLineBorder(Color.red, 5));
                boite_score2.setBorder(BorderFactory.createLineBorder(Color.red, 0));
            } else {
                boite_score2.setBorder(BorderFactory.createLineBorder(Color.red, 5));
                boite_score1.setBorder(BorderFactory.createLineBorder(Color.red, 0));
            }
            if (game.getCurrentTurn().getActiveRow() == 6 || !flagDoubleChange) {
                getActiveColorPane().setBorder(BorderFactory.createLineBorder(Color.white, 3));
                game.getCurrentTurn().setActiveRow(game.getCurrentTurn().getActiveRow() + 1);
                getActiveColorPane().setBorder(BorderFactory.createLineBorder(Color.red, 3));
                if (game.getCurrentTurn().getActiveRow() == 7) lines.get(7).setVisible(true);
            }
            int j = 0;
            for (int i = 0; i < game.getWordLenght(); i++) {
                if (game.getCurrentTurn().getFoundLetters().get(i)) j++;
            }
            if (j < game.getWordLenght() - 1) swapPlayer();
            else {
                getActiveColorPane().setText("");
                getActiveColorPane()
                        .append(letterFont, fontsize, Color.RED, String.valueOf
                                (game.getCurrentTurn().getWord().charAt(0)).toUpperCase());
                for (int i = 1; i < game.getCurrentTurn().getWord().length(); i++) {
                    if (game.getCurrentTurn().getFoundLetters().get(i))
                        getActiveColorPane()
                                .append(letterFont, fontsize, Color.white, String.valueOf
                                        (game.getCurrentTurn().getWord().charAt(i)).toUpperCase());
                    else getActiveColorPane().append(letterFont, fontsize, Color.white, " ");
                }
            }
            change.play();
            getActiveTimer().reset();
            offset = 1;
        } else {
            turnDefeat();
        }
        //changement joueur
        getActiveColorPane().setText("");
        getActiveColorPane()
                .append(letterFont, fontsize, Color.RED,
                        game.getCurrentTurn().getWord().charAt(0));
        for (int charIndex = 1; charIndex < game.getWordLenght(); charIndex++) {
            if (game.getCurrentTurn().getFoundLetters().get(charIndex)) {
                getActiveColorPane()
                        .append(letterFont, fontsize, Color.white,
                                game.getCurrentTurn().getWord().charAt(charIndex));
            } else {
                getActiveColorPane()
                        .append(letterFont, fontsize, Color.white, " ");
            }
        }

        for (int charIndex = 0; charIndex < game.getWordLenght(); charIndex++) {
            if (!game.getCurrentTurn().getFoundLetters().get(charIndex)) {
                game.getCurrentTurn().getFoundLetters().set(charIndex, true);
                charIndexToAdd = charIndex;
                break;
            }
        }

        for (int j = 0; j < 255; j++) {
            getActiveColorPane()
                    .replace(letterFont, fontsize, new Color(j, j, j),
                            game.getCurrentTurn().getWord().charAt(charIndexToAdd), charIndexToAdd, charIndexToAdd + 1);

            ct.begin();
            while (ct.get() < 4) Thread.yield();
        }
        getActiveColorPane()
                .replace(letterFont, fontsize, Color.white,
                        game.getCurrentTurn().getWord().charAt(charIndexToAdd), charIndexToAdd, charIndexToAdd + 1);
    }

    public void gameMPVictory() {
        getActiveTimer().suspendOn();

        if ((game.getScores().get(0) > 5 && (game.getScores().get(0) - game.getScores().get(1)) > 1) || game.getScores().get(0) > 9)
            boite_score1.setText("œ");
        else boite_score2.setText("œ");
        while (true) {
            jeugagne.play();
            ct.begin();
            while (ct.get() < 5000) Thread.yield();
        }
    }

        public SwingTimer getActiveTimer() {
                if (game.getActivePlayer() == 0) {
                miniSwingTimer2.suspendOn();
                return miniSwingTimer1;
                } else{
                miniSwingTimer1.suspendOn();
                return miniSwingTimer2;
                }
                }
}
 */