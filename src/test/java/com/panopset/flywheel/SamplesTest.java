package com.panopset.flywheel;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.panopset.flywheel.samples.FlywheelSample;
import com.panopset.flywheel.samples.FlywheelSamples;

class SamplesTest {

  @Test
  void test() {
    FlywheelSample fs = FlywheelSamples.all().get(0);
    Assertions.assertEquals("List variable", fs.getDesc());
    Assertions.assertEquals("foo\nbar", fs.getListText().replace("\r", ""));
    Assertions.assertEquals("${w0}", fs.getTemplateText());
    Assertions.assertEquals("list", fs.getName());
  }
}
