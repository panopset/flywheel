package com.panopset.flywheel;

public interface TemplateSource {
  boolean isDone();
  String next();
  void reset();
  int getLine();
  String getName();
}
