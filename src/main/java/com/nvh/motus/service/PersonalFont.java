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
            font = Font.createFont(Font.TRUETYPE_FONT, inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return font;
    }
}
