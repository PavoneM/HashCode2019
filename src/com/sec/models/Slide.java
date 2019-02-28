package com.sec.models;

public class Slide {
  int id;
  boolean vertical;
  Photo photo1;
  Photo photo2;

  public Slide(Photo photo1) {
    this.photo1 = photo1;
  }

  public Slide(Photo photo1, Photo photo2) {
    this.photo1 = photo1;
    this.photo2 = photo2;
  }
}
