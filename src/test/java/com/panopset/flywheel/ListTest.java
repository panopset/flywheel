package com.panopset.flywheel;

import java.io.IOException;
import org.junit.jupiter.api.Test;

/**
 * Test the List command.
 *
 * @author Karl Dinwiddie
 *
 */
public final class ListTest {

  @Test
  public void testListCommand() throws IOException {
    SimpleTest.comparisonTest("listTest01.txt", "listTest.txt",
        "listTestExpected.html");
  }
}
