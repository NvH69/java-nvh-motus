package com.nvh.motus;

import com.nvh.motus.service.Dictionaries;
import com.nvh.motus.view.MainFrame;
import com.nvh.motus.view.SwingTimer;

import javax.swing.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Launcher {


    public static SwingTimer miniSwingTimer1, miniSwingTimer2;
    public static int arg0;

    public static void main(String[] args) throws IOException {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            new Dictionaries();
            arg0 = 0;
            miniSwingTimer1 = new SwingTimer(9000);
            miniSwingTimer2 = new SwingTimer(9000);
            new MainFrame().setVisible(true);

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Launcher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
