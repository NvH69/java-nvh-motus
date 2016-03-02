package com.nvh.motus.service;

import java.awt.*;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class PersonalFont {


    public static Font loadFont(String fontName, int size) {
        Font font = null;
        try {
            URLConnection con;
            URL urlFont = PersonalFont.class.getResource(fontName);
            con = urlFont.openConnection();
            con.connect();
            InputStream inputStream = con.getInputStream();
            String s = Font.createFont(Font.TRUETYPE_FONT, inputStream).getFamily().split(" ")[0];
            font = new Font(s, Font.BOLD, size);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return font;
    }
}
