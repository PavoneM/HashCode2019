package com.sec;

import com.sec.models.Photo;
import com.sec.models.Slide;
import com.sec.models.Slideshow;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
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
//        inputs.add(FILENAME2);
        inputs.add(FILENAME3);
//        inputs.add(FILENAME4);
//        inputs.add(FILENAME5);

        for(String filename : inputs) {
            resetBetweenIterations();

            // TODO RESET STATICS
            try {
                List<Photo> photos = InputParser.parse(filename);

                Map<String, List<Photo>> tagsMap = createTagsMap(photos);
                List<Slide> slideList = createSlideList(photos);

                // step 1 : take slide with most tags
                Slide mostTagSlide = getMostTagSlide(slideList);

                // create slideShow
                Slideshow slideshow = new Slideshow();
                slideList.add(mostTagSlide);

                Slide currentSlide = mostTagSlide;
                slideList.remove(currentSlide);

                // step : repeat all slides
                while(slideList.size() > 0) {
                    // step 2: compute max point
                    int maxPoint = currentSlide.nbOfPointsMax();

                    // step 3 : compare it to all available slides
                    Slide nextSlide = fintNextSlide(currentSlide, slideList, maxPoint);

                    // Step 4: put in list of slides
                    slideshow.slidesList.add(nextSlide);
                    currentSlide = nextSlide;
                    slideList.remove(currentSlide);
                }

                Main.saveOutput(slideshow, filename);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static Slide fintNextSlide(Slide currentSlide, List<Slide> listSlide, int maxPoints) {
        Slide slideToReturn = listSlide.get(0);
        int maxInterest = 0;
        for(Slide slide : listSlide) {
            if (currentSlide != slide) {
                int interest = currentSlide.getInterestInNextSlide(slide);
                if (interest >= maxPoints) {
                    break;
                }
                if (interest >= maxInterest) {
                    maxInterest = interest;
                    slideToReturn = slide;
                }
            }
        }
        return slideToReturn;
    }

    private static Slide getMostTagSlide(List<Slide> list) {
        Slide mostTagSlide = list.get(0);
        int numberTagMax = 0;
        for(Slide slide : list) {
            if(numberTagMax < slide.tags.size()) {
                numberTagMax = slide.tags.size();
                mostTagSlide = slide;
            }
        }
        return mostTagSlide;
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

    private static List<Slide> createSlideList(List<Photo> photos) {
        List<Slide> listSlides = new ArrayList<>();
        List<Photo> pendingVertical = new LinkedList<Photo>();
        for (Photo photo : photos) {

            if(!photo.vertical) {
                listSlides.add(new Slide(photo));
            } else {
                if (!pendingVertical.isEmpty()) {
                    int indexToClean = -1;
                    for (int i = 0; i < pendingVertical.size(); i++) {
                        Photo pendingPhoto = pendingVertical.get(i);
                        Set<String> tmpSet = new HashSet<String>(pendingPhoto.tags);
                        tmpSet.retainAll(photo.tags);
                        if (tmpSet.isEmpty()) {
                            // ideal match
                            listSlides.add(new Slide(photo, pendingPhoto));
                            indexToClean = i;
                            break;
                        }
                    }
                    if (indexToClean != -1) {
                        pendingVertical.remove(indexToClean);
                    } else {
                        pendingVertical.add(photo);
                    }
                } else {
                    pendingVertical.add(photo);
                }
            }
        }

        // flush pending vertical
        for (int i = 0; i < pendingVertical.size(); i = i + 2) {
            listSlides.add(new Slide(pendingVertical.get(i), pendingVertical.get(i + 1)));
        }

        return listSlides;
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
