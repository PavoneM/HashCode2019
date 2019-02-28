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
import java.util.Set;

public class Main {
    private static String FILENAME1 = "src/resources/a_example.txt";
    private static String FILENAME2 = "src/resources/b_lovely_landscapes.txt";
    private static String FILENAME3= "src/resources/c_memorable_moments.txt";
    private static String FILENAME4 = "src/resources/d_pet_pictures.txt";
    private static String FILENAME5 = "src/resources/e_shiny_selfies.txt";

    public static Map<String, Set<Slide>> SLIDES_PER_TAG;

    private static void resetBetweenIterations() {
        SLIDES_PER_TAG = new HashMap<String, Set<Slide>>();
        Slide.CURRENT_ID = 0;
    }

    public static void main(String[] args) {
        List<String> inputs = new ArrayList<>();
        inputs.add(FILENAME1);
        inputs.add(FILENAME2);
        inputs.add(FILENAME3);
        inputs.add(FILENAME4);
        inputs.add(FILENAME5);

        resetBetweenIterations();

        for(String filename : inputs) {

            // TODO RESET STATICS
            try {
                List<Photo> photos = InputParser.parse(filename);

                Map<String, List<Photo>> tagsMap = createTagsMap(photos);
                Slideshow slideshow = createSlideshow(photos);
                Main.saveOutput(slideshow, filename);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private static Map<String, List<Photo>> createTagsMap(List<Photo> photos) {
        Map<String, List<Photo>> map = new HashMap<>();
        for (Photo photo : photos) {
            for(String tag : photo.tags) {
                if (!map.containsKey(tag)) {
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

            if(!photo.vertical) {
                Slide slide = new Slide(photo);
                slideshow.slidesList.add(slide);
            }
        }
        return slideshow;
    }

    private static void saveOutput(Slideshow slideshow, String inputName) {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(inputName + ".out"));
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
