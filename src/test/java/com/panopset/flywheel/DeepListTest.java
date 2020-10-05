package com.panopset.flywheel;

import java.io.IOException;
import org.junit.jupiter.api.Test;

/**
 * Deep list test.
 *
 * @author Karl Dinwiddie
 *
 */
public final class DeepListTest {

  /**
   * Test using a list within a list.
   */
  @Test
  public void testListCommand() throws IOException {
    SimpleTest.comparisonTest("deepListTest01.txt", "deepListTest.txt",
        "deepListTestExpected.txt");
  }

}
