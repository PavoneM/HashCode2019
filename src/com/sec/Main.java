package com.sec;

import com.sec.models.Photo;
import com.sec.models.Slide;
import com.sec.models.Slideshow;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
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

    private static void saveOutput(Slideshow slideshow) {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(OUTPUTNAME));
            pw.println(slideshow.slidesList.size());
            for (Slide slide : slideshow.slidesList) {
                pw.print(slide.photo1.id);
                if (slide.photo2 != null) {
                    pw.print(" ");
                    pw.print(slide.photo2.id);
                }
            }
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
