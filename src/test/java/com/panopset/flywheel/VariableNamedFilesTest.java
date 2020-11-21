package com.panopset.flywheel;

import java.io.IOException;
import org.junit.jupiter.api.Test;

public final class VariableNamedFilesTest {

  /**
   * Test variable named template. See <b>variableNamedTemplateTest.txt</b>.
   */
  @Test
  public void testVariableNamedTemplate() throws IOException {
    SimpleTest.comparisonTest("variableNamedTemplateTest.txt", "variableNamedTemplateResult.txt",
        "variableNamedTemplateExpected.txt");
  }

  /**
   * Test variable named list. See <b>variableNamedListTest.txt</b>
   */
  @Test
  public void testVariableNamedList() throws IOException {
    SimpleTest.comparisonTest("variableNamedListTest.txt", "variableNamedListResult.txt",
        "variableNamedListExpected.txt");
  }

}
