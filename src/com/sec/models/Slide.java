package com.sec.models;

import java.util.HashSet;
import java.util.Set;

public class Slide {
  public int id;
  public boolean vertical;
  public Photo photo1;
  public Photo photo2;
  public Set<String> tags;

  public Slide(Photo photo1) {
    this.photo1 = photo1;
    this.tags = new HashSet<>();
  }

  public Slide(Photo photo1, Photo photo2) {
    this.photo1 = photo1;
    this.photo2 = photo2;
  }

  public double nbOfPointsMax() {
    return Math.floor(tags.size()/ 2.);
  }

}
