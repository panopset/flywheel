package com.panopset.flywheel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import com.panopset.compat.Logop;

/**
 * Tests the Flywheel template variable resolution.
 *
 * @author Karl Dinwiddie
 */
public final class BracketTest {

  /**
   * <b>foo</b>.
   */
  static final String FOO = "foo";

  /**
   * <b>bar</b>.
   */
  static final String BAR = "bar";

  /**
   * <b>$&#123;1} ${foo}</b>.
   */
  static final String INPUT = "$&#123;1} ${" + FOO + "}.";

  /**
   * <b>$&#123;1} bar</b>.
   */
  static final String EXPECTED = "$&#123;1} " + BAR + ".";

  @Test
  void testBracket() throws IOException {
    Logop.turnOnDebugging();
    final String s = new FlywheelBuilder().map(FOO, BAR)
        .input(new String[] { EXPECTED }).construct().exec();
    assertEquals(EXPECTED, s);
    Logop.turnOffDebugging();
  }

}
