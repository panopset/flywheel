package com.panopset.flywheel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import org.junit.jupiter.api.Test;

public final class SplitColumnListTest {

  /**
   * Test split columns in a list command. See <b>splitList.txt</b>.
   */
  @Test
  void testSplitColumns() throws IOException {
    String expected = "6#Zonk7#Bonk";
    String[] inp = new String[] { "${@l splitList.txt}${2}#${3}${@q}" };
    String results = new FlywheelBuilder()
        .baseDirectoryPath("src/test/resources/com/panopset/flywheel")
        .map(ReservedWords.SPLITS, "13,14").input(inp).construct().exec();
    assertEquals(expected, results);
  }

}
