package com.nvh.motus.model;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

public class ColorPane extends JTextPane {

    public ColorPane() {
        StyledDocument doc = this.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
    }

    public void append(Font font, int size, Color c, Object s) {

        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY,
                StyleConstants.FontFamily, font.getFamily());
        AttributeSet aset2 = sc.addAttribute(SimpleAttributeSet.EMPTY,
                StyleConstants.FontSize, size);
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
    }

    public void replace(Font font, int size, Color c, Object s, int pos1, int pos2) {

        setCaretPosition(pos2);
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY,
                StyleConstants.FontFamily, font.getFamily());
        AttributeSet aset2 = sc.addAttribute(SimpleAttributeSet.EMPTY,
                StyleConstants.FontSize, size);
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
    }

    public int getLettersCount() {
        int lettersCount = 0;
        try {
            for (Character c : this.getText().toCharArray()) {
                if (Character.isLetter(c)) lettersCount++;
            }
            return lettersCount;
        }
        catch (Exception e) {
            return 0;
        }
    }
}
