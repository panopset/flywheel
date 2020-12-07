package com.panopset.flywheel.samples;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import com.panopset.compat.Logop;
import com.panopset.compat.Rezop;
import com.panopset.compat.Stringop;

public enum FlywheelSamples {
  
  INSTANCE;
  
  public static List<FlywheelSample> all() {
    return INSTANCE.getSamples();
  }
  
  private List<FlywheelSample> samples;

  public List<FlywheelSample> getSamples() {
    if (samples == null) {
      samples = new ArrayList<>();
      for (String sfx : Rezop.loadListFromTextResource(String.format("%s/%s", BASE_PATH, "index.txt"))) {
        populateSample(sfx, samples);
      }
    }
    return samples;
  }
  
  private void populateSample(String sfx, List<FlywheelSample> list) {
    if (sfx.length()>0 && "#".equals(sfx.substring(0, 1))) {
      return;
    }
    Properties props = new Properties();
    try {
      props.load(Rezop.getResourceStream(String.format("%s/%s/%s", BASE_PATH, sfx, "props.txt")));
    } catch (IOException e) {
      Logop.error(e);
      return;
    }
    FlywheelSample fs = new FlywheelSample();
    fs.setName(sfx);
    fs.setListText(Rezop.loadFromRez(this.getClass(), String.format("%s/%s/%s", BASE_PATH, sfx, "list.txt")));
    fs.setTemplateText(Rezop.loadFromRez(this.getClass(), String.format("%s/%s/%s", BASE_PATH, sfx, "template.txt")));
    fs.setDesc(props.getProperty("desc"));
    fs.setLineBreaks(Stringop.parseBoolean(props.getProperty("lineBreaks"), true));
    fs.setListBreaks(Stringop.parseBoolean(props.getProperty("listBreaks"), true));
    list.add(fs);
  }
  
  private static final String BASE_PATH = "/com/panopset/flywheel/samples/";

}
