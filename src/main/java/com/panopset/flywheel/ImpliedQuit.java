package com.panopset.flywheel;

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
