package com.panopset.flywheel;

import java.io.IOException;
import org.junit.jupiter.api.Test;

/**
 * Depth test.
 *
 * @author Karl Dinwiddie
 *
 */
public final class DepthTest {

  @Test
  public void testDepth() throws IOException {
    SimpleTest.comparisonTest("depthTest.txt", "depthTest01.txt",
        "depthTest01Expected.txt");
    SimpleTest.comparisonTest("depthTest.txt", "depthTest02.txt",
        "depthTest02Expected.txt");
  }

  @Test
  public void testDeeper() throws IOException {
    SimpleTest.comparisonTest("deepTest.txt", "deepTest.txt",
        "deepTestExpected.txt");
  }
}
