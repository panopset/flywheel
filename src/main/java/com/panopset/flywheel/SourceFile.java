package com.panopset.flywheel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.panopset.compat.Fileop;
import com.panopset.compat.Logop;

/**
 * Flywheel source file, all paths relative to the primary script file.
 */
public final class SourceFile {

  /**
   * Relative path.
   */
  private final String relativePath;

  /**
   * @return Relative path.
   */
  String getRelativePath() {
    return relativePath;
  }

  /**
   * Source file.
   */
  private final File file;

  /**
   * @return File.
   */
  File getFile() {
    return file;
  }

  /**
   * Canonical path of source file.
   */
  private final String canonicalPath;

  /**
   * Source file broken down in to a String List.
   */
  private List<String> sourceLines;

  /**
   * Directory depth relative to driver template.
   */
  private Integer depth;

  /**
   * Get depth relative to driver template.
   *
   * @return Directory depth relative to driver template
   */
  public int getDepth() {
    if (depth == null) {
      depth = 0;
      String temp = relativePath;
      int idx = temp.indexOf("/");
      while (idx > -1) {
        depth++;
        temp = temp.substring(idx + 1);
        idx = temp.indexOf("/");
      }
    }
    return depth;
  }

  /**
   * Reset source lines, for list processing.
   */
  public void reset() {
    sourceLines = null;
  }

  /**
   * Get source lines.
   *
   * @return Source lines.
   */
  public List<String> getSourceLines() {
    if (sourceLines == null) {
      sourceLines = Fileop.readLines(file);
    }
    return sourceLines;
  }

  /**
   * SourceFile constructor.
   *
   * @param sourceFile Source file.
   * @param basePath Base directory path.
   */
  public SourceFile(final File sourceFile, final String basePath) {
    file = sourceFile;
    canonicalPath = Fileop.getCanonicalPath(this.file);
    relativePath = canonicalPath.substring(basePath.length() + 1);
  }

  /**
   * SourceFile constructor.
   *
   * @param sourceFileRelativePath Source file relative path.
   * @param flywheel Flywheel.
   */
  public SourceFile(final Flywheel flywheel, final String sourceFileRelativePath) {
    relativePath = sourceFileRelativePath;
    file = new File(flywheel.getBaseDirectoryPath() + "/" + relativePath);
    canonicalPath = Fileop.getCanonicalPath(file);
  }

  /**
   * Check to see if source file exists, and can be read.
   *
   * @return true if source file exists.
   */
  public boolean isValid() {
    return file.exists() && file.canRead();
  }

  /**
   * Override equals method. From
   * <a href="http://www.geocities.com/technofundo/tech/java/equalhash.html">
   * http://www.geocities.com/technofundo/tech/java/equalhash.html </a>
   *
   * @param obj Object.
   * @return true if obj is equal to this.
   */
  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if ((obj == null) || (obj.getClass() != this.getClass())) {
      return false;
    }
    SourceFile test = (SourceFile) obj;
    return canonicalPath.equals(test.canonicalPath) && relativePath.equals(test.relativePath);
  }

  /**
   * @return hash code for this object.
   */
  @Override
  public int hashCode() {
    return file.hashCode();
  }
}
