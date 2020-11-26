package com.panopset.flywheel;

import com.panopset.compat.Stringop;

public class LineFeedRules {

  private Boolean lineBreaks;
  private Boolean listBreaks;
  private Boolean winCRLF;

  public static final LineFeedRules LINE_ONLY_BREAKS = new LineFeedRules(true, false, false);
  public static final LineFeedRules LINE_ONLY_BREAKS_WINDOWS = new LineFeedRules(true, false, true);
  public static final LineFeedRules LIST_BREAKS = new LineFeedRules(false, true, false);
  public static final LineFeedRules FULL_BREAKS = new LineFeedRules(true, true, false);
  public static final LineFeedRules FLATTEN = new LineFeedRules(false, false, false);
  public static final LineFeedRules FULL_BREAKS_WINDOWS = new LineFeedRules(false, true, true);
  
  public LineFeedRules() {}

  public LineFeedRules(String lineBreaks, String listBreaks) {
    this(lineBreaks, listBreaks, "false");
  }

  public LineFeedRules(String lineBreaks, String listBreaks, String winCRLF) {
    this.lineBreaks = Stringop.parseBoolean(lineBreaks);
    this.listBreaks = Stringop.parseBoolean(listBreaks);
    this.winCRLF = Stringop.parseBoolean(winCRLF);
  }

  public LineFeedRules(Boolean lineBreaks, Boolean listBreaks, Boolean winCRLF) {
    this.lineBreaks = lineBreaks;
    this.listBreaks = listBreaks;
    this.winCRLF = winCRLF;
  }

  public Boolean getLineBreaks() {
   if (lineBreaks == null) {
    lineBreaks = true;
   }
   return lineBreaks;
  }

  public void setLineBreaks(Boolean lineBreaks) {
   this.lineBreaks = lineBreaks;
  }

  public Boolean getListBreaks() {
   if (listBreaks == null) {
    listBreaks = true;
   }
   return listBreaks;
  }

  public void setListBreaks(Boolean listBreaks) {
   this.listBreaks = listBreaks;
  }

  public Boolean getWinCRLF() {
   if (winCRLF == null) {
    winCRLF = false;
   }
   return winCRLF;
  }

  public void setWinCRLF(Boolean winCRLF) {
   this.winCRLF = winCRLF;
  }
  
  @Override
  public String toString() {
    return String.format("LineB:%s ListB:%s WinCRLF:%s", getLineBreaks(), getListBreaks(), getWinCRLF());
  }
}
