package com.panopset.flywheel;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.panopset.flywheel.samples.FlywheelSample;
import com.panopset.flywheel.samples.FlywheelSamples;

class FlywheelSamplesTest {

  @Test
  void testGetSamples() {
    List<FlywheelSample> flywheelSamples = FlywheelSamples.all();
    Assertions.assertTrue(flywheelSamples.size() > 1);
    FlywheelSample fs0 = flywheelSamples.get(0);
    Assertions.assertEquals("foo\nbar", fs0.getListText());
    Assertions.assertEquals("${w0}", fs0.getTemplateText());
    Assertions.assertTrue(fs0.getLineBreaks());
    Assertions.assertTrue(fs0.getListBreaks());
    FlywheelSample fs1 = flywheelSamples.get(1);
    Assertions.assertTrue(fs1.getLineBreaks());
    Assertions.assertFalse(fs1.getListBreaks());
  }

}
