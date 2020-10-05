package com.panopset.flywheel;

import java.io.IOException;
import org.junit.jupiter.api.Test;
/**
 * Test Command Execute.
 *
 * @author Karl Dinwiddie
 *
 */
public final class ExecuteTest  {

  @Test
  public void testExecuteCommand() throws IOException {
    SimpleTest.comparisonTest("executeTest.txt", "executeTest.txt",
        "executeTestExpected.txt");
  }
}
