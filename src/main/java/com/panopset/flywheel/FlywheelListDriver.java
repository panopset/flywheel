package com.panopset.flywheel;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import com.panopset.compat.Splitter;
import com.panopset.compat.Stringop;

public class FlywheelListDriver {

  private Flywheel flywheel;
  private List<String> inputList;
  private final String template;
  private Integer splitz;
  private String tokens;
  private Boolean outRtn;

  public Boolean getOutRtn() {
    return outRtn;
  }

  public void setOutRtn(Boolean outRtn) {
    this.outRtn = outRtn;
  }

  public Flywheel getFlywheel() {
    flywheel.setCreateOutputLineBreaksFlag(getOutRtn());
    return flywheel;
  }
 
  public List<String> getInputList() {
    return inputList;
  }

  /**
   * Only do this as part of an interative process.
   * @param value Value
   */
  public void setInputList(List<String> value) {
    inputList = value;
  }
 
  public String getTemplate() {
    return template;
  }
  
  public boolean hasSplitz() {
    return getSplitz() > 0;
  }

  public Integer getSplitz() {
    if (splitz == null) {
      splitz = -1;
    }
    return splitz;
  }

  public void setSplitz(Integer value) {
    splitz = value;
  }

  public String getTokens() {
    if (tokens == null) {
      tokens = "";
    }
    return tokens;
  }
  
  public void setTokens(String value) {
    tokens = value;
  }
  
  public synchronized String getOutput() throws IOException {
    Integer lineSplitWidth = getSplitz();
    if (lineSplitWidth != null && lineSplitWidth > 0) {
      StringWriter sw = new StringWriter();
      for (String s : getInputList()) {
        Iterable<String> chunks = Splitter.fixedLength(lineSplitWidth).split(s);
        List<String> chunky = new ArrayList<>();
        for (String chunk : chunks) {
          chunky.add(chunk);
        }
        setInputList(chunky);
        sw.append(processInput());
        sw.append(Stringop.getEol());
      }
      return sw.toString();
    } else {
     return processInput();
    }
  }

  private String processInput() throws IOException {
    StringWriter sw = new StringWriter();
    for (String s : getInputList()) {
      flywheel = new FlywheelBuilder().map(createInputMapFrom(s))
          .input(Stringop.stringToList(getTemplate())).construct();
      sw.append(flywheel.exec());
      if (flywheel.isStopped()) {
        return "Stopped, see logs.";
      }
      if (flywheel.isCreateOutputLineBreaksFlagSet().booleanValue()) {
        sw.append(Stringop.getEol());
      }
    }
    return sw.toString();
  }

  private Map<String, String> createInputMapFrom(final String inputLine) {
    Map<String, String> rtn = new HashMap<>();
    rtn.put("l", inputLine);

    if (Stringop.isPopulated(inputLine)) {
      int i = 0;
      StringTokenizer st = new StringTokenizer(inputLine, " ");
      while (st.hasMoreTokens()) {
        rtn.put("w" + i++, st.nextToken());
      }
      if (Stringop.isPopulated(getTokens())) {
        i = 0;
        st = new StringTokenizer(inputLine, getTokens());
        while (st.hasMoreTokens()) {
          rtn.put("t" + i++, st.nextToken());
        }
      }
    }
    return rtn;
  }
  
  private FlywheelListDriver(List<String> inputList, String template) {
    this.inputList = inputList;
    this.template = template;
  }

  public static class Builder {
    public Builder(List<String> inputList, String template) {
      fp = new FlywheelListDriver(inputList, template);
    }

    final FlywheelListDriver fp;

    public FlywheelListDriver build() {
      if (fp.inputList.isEmpty()) {
        fp.inputList.add("");
      }
      return fp;
    }

    public Builder withLineBreaks(boolean value) {
      fp.setOutRtn(value);
      return this;
    }
    
    public Builder withSplitz(Integer value) {
      fp.setSplitz(value);
      return this;
    }

    public Builder withTokens(String value) {
      fp.setTokens(value);
      return this;
    }
  }
}
