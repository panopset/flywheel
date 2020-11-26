package com.panopset.flywheel;

import java.io.IOException;
import org.junit.jupiter.api.Test;

final class DepthTest {

  @Test
  void testDepth() throws IOException {
    SimpleTest.comparisonTest("depthTest.txt", "depthTest01.txt",
        "depthTest01Expected.txt");
    SimpleTest.comparisonTest("depthTest.txt", "depthTest02.txt",
        "depthTest02Expected.txt");
  }

  @Test
  void testDeeper() throws IOException {
    SimpleTest.comparisonTest("deepTest.txt", "deepTest.txt",
        "deepTestExpected.txt");
  }
}
