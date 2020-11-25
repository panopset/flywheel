package com.panopset.flywheel;

public final class TemplateArray extends TemplateReader {

  private String[] da;

  public TemplateArray(final String[] data) {
    da = data;
  }

  @Override
  public String getName() {
    return "";
  }

  @Override
  public String getNextLine() {
    return da[getIndex()];
  }

  @Override
  public boolean isDone() {
    return getIndex() >= da.length;
  }

  @Override
  protected void resetSource() {
  }
}
