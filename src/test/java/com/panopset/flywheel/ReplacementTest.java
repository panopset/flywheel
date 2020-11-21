package com.panopset.flywheel;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import com.panopset.compat.Fileop;
import com.panopset.compat.Logop;
import com.panopset.gp.FileCompare;

public final class ReplacementTest {

  private static final String TEST_FILE_NAME = "replacementsTest.txt";

  @Test
  public void testReplacements() throws IOException {
    File generatedFile = new File(SimpleTest.TEST_DIRECTORY + "/"
        + TEST_FILE_NAME);
    if (generatedFile.exists()) {
      Fileop.delete(generatedFile);
    }
    Flywheel script = new FlywheelBuilder()
        .targetDirectory(SimpleTest.TEST_DIRECTORY)
        .file(new File(SimpleTest.TEST_FILE_PATH + TEST_FILE_NAME))
        .replacement("foo", "bar").construct();
    script.exec();
    assertFalse(script.isStopped());
    Logop.dspmsg(Fileop.getCanonicalPath(generatedFile));
    assertTrue(generatedFile.exists());
    assertTrue(generatedFile.length() > 0);
    assertTrue(FileCompare.filesAreSame(generatedFile, new File(
        SimpleTest.TEST_FILE_PATH + "replacementsTestExpected.txt")));
  }
}
