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
    Assertions.assertEquals("${@p x}foo${@q}${@r x}bar${@q}${l}", fs0.getTemplateText());
    Assertions.assertTrue(fs0.getLineBreaks());
    Assertions.assertFalse(fs0.getListBreaks());
    FlywheelSample fs1 = flywheelSamples.get(1);
    Assertions.assertFalse(fs1.getLineBreaks());
    Assertions.assertFalse(fs1.getListBreaks());
  }

}
