package com.sec.models;

import java.util.ArrayList;
import java.util.List;

public class Photo {
  public boolean vertical;
  public List<String> tags;
  public int id;

  public Photo() {
    this.tags = new ArrayList<>();
  }
}
