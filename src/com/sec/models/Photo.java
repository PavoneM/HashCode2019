package com.sec.models;

import java.util.ArrayList;
import java.util.List;

public class Photo {
  boolean vertical;
  List<String> tags;
  int id;

  public Photo() {
    this.tags = new ArrayList<>();
  }
}
