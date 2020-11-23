package com.panopset.flywheel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.panopset.compat.Stringop;

class LineFeedTest {

  @Test
  void test() throws IOException {
    List<String> input = new ArrayList<>();
    input.add("a");
    input.add("b");
    Stringop.setEol("\n");
    Flywheel fw = new FlywheelBuilder().input(input).construct();
    String result = fw.exec();
    Assertions.assertEquals("a\nb\n", result);
  }

}
