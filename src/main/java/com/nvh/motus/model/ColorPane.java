package com.nvh.motus.model;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;

public class ColorPane extends JTextPane {

    public void append(String fonte, int taille, Color c, String s) {

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

        replaceSelection(s);
    }

    public void replace(String fonte, int taille, Color c, String s, int pos1, int pos2) {
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
        replaceSelection(s);
    }
}
