package com.panopset.flywheel.samples;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FlywheelSample implements Serializable {
  private static final long serialVersionUID = 1L;
  private String name;
  private String desc;
  private String listText;
  private String templateText;
  private String tokens;
  private List<String> lineSplitWidths;
  private Boolean lineBreaks;
  private Boolean listBreaks;

  public String getName() {
   if (name == null) {
    name = "";
   }
   return name;
  }

  public void setName(String name) {
   this.name = name;
  }

  public String getDesc() {
   if (desc == null) {
    desc = "";
   }
   return desc;
  }

  public void setDesc(String desc) {
   this.desc = desc;
  }

  public String getListText() {
   if (listText == null) {
    listText = "";
   }
   return listText;
  }

  public void setListText(String listText) {
   this.listText = listText;
  }

  public String getTemplateText() {
   if (templateText == null) {
    templateText = "";
   }
   return templateText;
  }

  public void setTemplateText(String templateText) {
   this.templateText = templateText;
  }

  public String getTokens() {
   if (tokens == null) {
    tokens = "";
   }
   return tokens;
  }

  public void setTokens(String tokens) {
   this.tokens = tokens;
  }

  public List<String> getLineSplitWidths() {
   if (lineSplitWidths == null) {
    lineSplitWidths = new ArrayList<>();
   }
   return lineSplitWidths;
  }

  public void setLineSplitWidths(List<String> lineSplitWidths) {
   this.lineSplitWidths = lineSplitWidths;
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
}
