package com.nvh.motus.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Dictionaries {

    public static List<List<String>> fullDictionary = resetDictionary();
    public static final List<List<String>> solutionsDictionary = resetDictionary();
    private static URL fullDictionaryPath = Dictionaries.class.getResource("/Dico56789.txt");
    private static URL solutionsDictionaryPath = Dictionaries.class.getResource("/2000mots.txt");

    public Dictionaries() {

        String line;

        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(fullDictionaryPath.getPath()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            if (in != null) {
                while (!(line = in.readLine()).endsWith("*")) {
                    fullDictionary.get(line.length()).add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            in = new BufferedReader(new FileReader(solutionsDictionaryPath.getPath()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            assert in != null;
            while (!(line = in.readLine()).endsWith("*")) {
                    solutionsDictionary.get(line.length()).add(line);
                }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private static List<List<String>> resetDictionary() {
        List<List<String>> emptyDictionary = new ArrayList<>();
        for (int i=0; i<16; i++)
            emptyDictionary.add(new ArrayList<>());
        return emptyDictionary;
    }
}
