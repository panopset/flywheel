package com.panopset.flywheel;

import java.io.IOException;
import org.junit.jupiter.api.Test;

/**
 * Double file test.
 *
 * @author Karl Dinwiddie
 *
 */
public final class DoubleFileTest {

  /**
   * Test generating more than one files using Command File.
   */
  @Test
  public void testDoubleFileGeneration() throws IOException {
    SimpleTest
        .comparisonTest("doubleFileTest.txt", new String[] { "dft0.txt",
            "dft1.txt" }, new String[] { 
              "dft_expected0.txt",
              "dft_expected1.txt" });
  }
}
