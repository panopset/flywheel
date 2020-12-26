package com.panopset.flywheel;

import java.util.List;

public class TemplateArray implements TemplateSource {

  private final String[] ia;
  private int index = 0;

  public TemplateArray(final List<String> data) {
    ia = data.toArray(new String[0]);
  }

  public TemplateArray(final String[] data) {
    ia = data;
  }

  @Override
  public boolean isDone() {
    return index >= ia.length;
  }

  @Override
  public String next() {
    return ia[index++];
  }

  @Override
  public void reset() {
    index = 0;
  }

  @Override
  public int getLine() {
    return index;
  }

  @Override
  public String getName() {
    return "";
  }
}
