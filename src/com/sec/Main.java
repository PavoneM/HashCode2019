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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static String FILENAME = "src/resources/a_example.txt";
    private static String OUTPUTNAME = "example.out";

    public static void main(String[] args) {
        try {
            List<Photo> photos = InputParser.parse(FILENAME);

            Map<String, List<Photo>> tagsMap = createTagsMap(photos);
            Slideshow slideshow = createSlideshow(photos);
            Main.saveOutput(slideshow);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Map<String, List<Photo>> createTagsMap(List<Photo> photos) {
        Map<String, List<Photo>> map = new HashMap<>();
        for (Photo photo : photos) {
            for(String tag : photo.tags) {
                if (map.containsKey(tag)) {
                    List<Photo> list = new ArrayList<>();
                    list.add(photo);
                    map.put(tag, list);
                } else {
                    List<Photo> list = map.get(tag);
                    list.add(photo);
                }
            }
        }
        return map;
    }

    private static Slideshow createSlideshow(List<Photo> photos) {
        Slideshow slideshow = new Slideshow();

        for (Photo photo : photos) {
            Slide slide = new Slide(photo);
            slideshow.slidesList.add(slide);
        }
        return slideshow;
    }

    private static void saveOutput(Slideshow slideshow) {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(OUTPUTNAME));
            pw.println(slideshow.slidesList.size());
            for (Slide slide : slideshow.slidesList) {
                String line = slide.photo1.id + ((slide.photo2 != null) ? (" " + slide.photo2.id) : "");
                pw.println(line);
            }
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
