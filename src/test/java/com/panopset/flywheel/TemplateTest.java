package com.panopset.flywheel;

import java.io.IOException;

final class TemplateTest {

  /**
   * Test template command. See <b>templateTest01.txt</b>.
   */
  void testTemplateCommand() throws IOException {
    SimpleTest.comparisonTest("templateTest01.txt", "templateTest.txt",
        "templateTestExpected.txt");
  }

}
