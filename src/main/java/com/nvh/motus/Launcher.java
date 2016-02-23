package com.nvh.motus;

import com.nvh.motus.service.Dictionaries;
import com.nvh.motus.view.MainFrame;

import java.io.IOException;

public class Launcher {
    public static MainFrame mainFrame;

    public static void main(String[] args) throws IOException {

        new Dictionaries();
        mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }
}
