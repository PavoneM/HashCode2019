package com.sec;

import com.sec.models.Photo;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class InputParser {

  public static List<Photo> parse(String fileName) throws FileNotFoundException {
    InputStreamReader is = new InputStreamReader(new FileInputStream(fileName));
    BufferedReader bf = new BufferedReader(is);
    List<Photo> photos = new ArrayList<Photo>();
    try {
      String line = bf.readLine();
      int nbPhotos = Integer.parseInt(line);
      for (int i = 0; i < nbPhotos; i++) {
        line = bf.readLine();
        String[] constants = line.split(" ");
        Photo photo = new Photo();
        photo.vertical = "V".equals(constants[0]);
        for (int j = 2; j < constants.length; j++) {
          photo.tags.add(constants[j]);
        }
        photos.add(photo);
      }
    } catch (IOException e) {
      System.out.println(e);
    }
    return photos;
  }
}
