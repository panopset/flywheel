package com.panopset.flywheel;

/**
 * Implied Command Quit.
 *
 * @author Karl Dinwiddie
 */
public class ImpliedQuit extends CommandQuit {

  /**
   * Implied quit.
   * 
   * @param template
   *          Template the implied quit is on.
   */
  public ImpliedQuit(final Template template) {
    super(Syntax.getOpenDirective() + "q" + Syntax.getCloseDirective(),
        template);
  }

}
