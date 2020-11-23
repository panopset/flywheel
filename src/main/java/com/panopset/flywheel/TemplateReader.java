package com.panopset.flywheel;

public abstract class TemplateReader implements TemplateSource {

  protected abstract void resetSource();

  protected abstract String getNextLine();

  @Override
  public final String next() {
    String rtn = getNextLine();
    index++;
    return rtn;
  }

  @Override
  public final void reset() {
    index = 0;
    resetSource();
  }

  @Override
  public final int getLine() {
    return index + 1;
  }
  
  protected final int getIndex() {
    return index;
  }

  private int index = 0;
}
