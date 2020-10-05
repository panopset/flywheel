package com.panopset.flywheel;

/**
 * Flywheel control flags.
 *
 * @author Karl Dinwiddie
 *
 */
public final class Control {

  /**
   * Auto-step wait time.
   */
  public static final int AUTO_STEP_WAIT_TIME = 500;

  /**
   * @return true if replacements are suppressed.
   */
  public boolean isReplacementsSuppressed() {
    return replacementsSuppressed;
  }

  /**
   * Used to temporarily suppress replacements.
   *
   * @param value
   *          Replacements suppressed.
   */
  public void setReplacementsSuppressed(final boolean value) {
    this.replacementsSuppressed = value;
  }

  /**
   * @return Stop execution flag.
   */
  public boolean isStopped() {
    return stop;
  }

  /**
   * Some error conditions require that Flywheel execution must stop.
   */
  public void stop() {
    stop = true;
  }

  /**
   * Stop flag.
   */
  private boolean stop = false;

  /**
   * Replacement suppression flag.
   */
  private boolean replacementsSuppressed = false;
}
