package com.nvh.motus.model;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;

public class ColorPane extends JTextPane {

    public void append(String fonte, int taille, Color c, Object s) {

        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY,
                StyleConstants.FontFamily, fonte);
        AttributeSet aset2 = sc.addAttribute(SimpleAttributeSet.EMPTY,
                StyleConstants.FontSize, taille);
        AttributeSet aset3 = sc.addAttribute(SimpleAttributeSet.EMPTY,
                StyleConstants.Foreground, c);
        AttributeSet aset4 = sc.addAttribute(SimpleAttributeSet.EMPTY,
                StyleConstants.StrikeThrough, false);

        int len = getDocument().getLength();

        setCaretPosition(len);
        setCharacterAttributes(aset, false);
        setCharacterAttributes(aset2, false);
        setCharacterAttributes(aset3, false);
        setCharacterAttributes(aset4, false);

        replaceSelection(s.toString());

        this.update(this.getGraphics());
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void replace(String fonte, int taille, Color c, Object s, int pos1, int pos2) {

        setCaretPosition(pos2);
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY,
                StyleConstants.FontFamily, fonte);
        AttributeSet aset2 = sc.addAttribute(SimpleAttributeSet.EMPTY,
                StyleConstants.FontSize, taille);
        AttributeSet aset3 = sc.addAttribute(SimpleAttributeSet.EMPTY,
                StyleConstants.Foreground, c);
        AttributeSet aset4 = sc.addAttribute(SimpleAttributeSet.EMPTY,
                StyleConstants.StrikeThrough, false);

        setCharacterAttributes(aset, false);
        setCharacterAttributes(aset2, false);
        setCharacterAttributes(aset3, false);
        setCharacterAttributes(aset4, false);
        moveCaretPosition(pos1);
        replaceSelection(s.toString());
        this.update(this.getGraphics());
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getLettersCount() {
        int lettersCount = 0;
        for (Character c : this.getText().toCharArray()) {
            if (Character.isLetter(c)) lettersCount++;
        }
        return lettersCount;
    }
}
