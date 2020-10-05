package com.panopset.gp;

import java.io.File;
import java.io.IOException;
import com.panopset.compat.Logop;

public class FileCompare {
  
  private FileCompare() {}

  public static boolean filesAreSame(final File one, final File two) {
    try (TextFileProcessor tfp1 = TextFileProcessor.textFileIterator(one);
        TextFileProcessor tfp2 = TextFileProcessor.textFileIterator(two)) {
      while (tfp1.hasNext() && tfp2.hasNext()) {
        String str1 = tfp1.next();
        String str2 = tfp2.next();
        if (!str1.equals(str2)) {
          return false;
        }
      }
      if (tfp1.hasNext() || tfp2.hasNext()) {
        return false;
      }
    } catch (IOException ex) {
      Logop.error(ex);
    }
    return true;
  }
}
