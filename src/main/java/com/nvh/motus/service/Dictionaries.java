package com.nvh.motus.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Dictionaries {

    public static final List<ArrayList<String>> solutionsDictionary = resetDictionary();
    public static List<ArrayList<String>> fullDictionary = resetDictionary();
    private static ResourceLoader resourceLoader = new ResourceLoader();
    public Dictionaries() {

        String line;

        BufferedReader in = null;
        try {
            String fullDictionaryPath = "/Dico56789.txt";
            in = new BufferedReader(new FileReader(resourceLoader.getFileFromResource(fullDictionaryPath)));
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
            String solutionsDictionaryPath = "/2000mots.txt";
            in = new BufferedReader(new FileReader(resourceLoader.getFileFromResource(solutionsDictionaryPath)));
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

    private static List<ArrayList<String>> resetDictionary() {
        List<ArrayList<String>> emptyDictionary = new ArrayList<>();
        for (int i = 0; i < 16; i++)
            emptyDictionary.add(new ArrayList<>());
        return emptyDictionary;
    }
}
