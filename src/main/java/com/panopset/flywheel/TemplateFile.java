package com.panopset.flywheel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import com.panopset.compat.Fileop;
import com.panopset.compat.Logop;
import com.panopset.compat.Stringop;

public final class TemplateFile extends TemplateReader {

  private String onDeck;

  private boolean fileDone = false;

  public TemplateFile(final File file) {
    tsf = file;
    fn = Fileop.getCanonicalPath(tsf);
  }

  @Override
  public String getName() {
    return fn;
  }

  @Override
  public boolean isDone() {
    if (fileDone) {
      return true;
    }
    if (tsf == null || !tsf.exists()) {
      fileDone = true;
      return true;
    }
    try {
      String str = br.readLine(); 
      if (str == null) {
        fileDone = true;
        br.close();
      } else {
        onDeck = str + Stringop.getEol();
      }
    } catch (IOException e) {
      Logop.error(e);
    }
    return fileDone;
  }

  public void resetSource() {
    fileDone = false;
    if (tsf != null && tsf.exists()) {
      try {
        br = new BufferedReader(new FileReader(tsf));
      } catch (FileNotFoundException ex) {
        Logop.error(ex);
      }
    } else {
      Logop.warn(String.format("%s %s", Fileop.getCanonicalPath(tsf), "does not exist"));
    }
  }

  private BufferedReader br;

  private final File tsf;

  private final String fn;

  @Override
  public String getNextLine() {
    return onDeck;
  }
}
