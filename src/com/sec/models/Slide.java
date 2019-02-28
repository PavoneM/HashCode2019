package com.sec.models;

import com.sec.Main;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Slide {
  public int id;
  public boolean vertical;
  public Photo photo1;
  public Photo photo2;
  public Set<String> tags;

  public static int CURRENT_ID;

  public Slide(Photo photo1) {
    this.id = CURRENT_ID++;
    this.photo1 = photo1;
    this.tags = new HashSet<>();
    this.addPhoto(photo1);
  }

  public Slide(Photo photo1, Photo photo2) {
    this(photo1);
    this.photo2 = photo2;
    this.addPhoto(photo2);
    this.vertical = true;
  }

  public double nbOfPointsMax() {
    return Math.floor(tags.size()/ 2.);
  }

  // processing when adding a photo to the slide
  private void addPhoto(Photo photo) {
    for (String tag : photo.tags) {
      tags.add(tag);

      // populate the map of slides per tag
      if (!Main.SLIDES_PER_TAG.containsKey(tag)) {
        Main.SLIDES_PER_TAG.put(tag, new HashSet<Slide>());
      }
      Set<Slide> slides = Main.SLIDES_PER_TAG.get(tag);
      slides.add(this);
    }
  }

  public int hashCode() {
    return id;
  }

}
