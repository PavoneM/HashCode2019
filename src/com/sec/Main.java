package com.sec;

import com.sec.models.Photo;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static String FILENAME = "src/resources/a_example.txt";
    private static String OUTPUTNAME = "example.out";

    public static void main(String[] args) {
        try {
            List<Photo> photos = InputParser.parse(FILENAME);
            System.out.println(photos.size());
            Main.saveOutput();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void saveOutput() {
        StringBuilder sb = new StringBuilder("");

        sb.append(3);
        sb.append(" ");
        sb.append("4");
        sb.append("\n");
        sb.append("2");

        try {
            PrintWriter out = new PrintWriter(Main.OUTPUTNAME);
            out.write(sb.toString());
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
