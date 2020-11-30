package com.panopset.flywheel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import com.panopset.compat.Stringop;

final class SplitColumnListTest {

  /**
   * Test split columns in a list command. See <b>splitList.txt</b>.
   */
  @Test
  void testSplitColumns() throws IOException {
    Stringop.setEol("\n");
    String expected = "6#Zonk7#Bonk\n";
    String[] inp = new String[] { "${@l splitList.txt}${2}#${3}${@q}" };
    String results = new FlywheelBuilder().withLineFeedRules(LineFeedRules.LINE_BREAKS)
        .baseDirectoryPath("src/test/resources/com/panopset/flywheel")
        .map(ReservedWords.SPLITS, "13,14").input(inp).construct().exec();
    assertEquals(expected, results);
  }

}
