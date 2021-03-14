package com.panopset.flywheel;

import com.panopset.compat.Stringop;

/**
 * A template command is specified in a script, and has a source.
 */
public abstract class TemplateCommand extends Command {

  /**
   * Source.
   */
  private final String src;

  /**
   * Get source.
   *
   * @return Source for this template command.
   */
  final String getSource() {
    return src;
  }

  /**
   * Inner piece of command.
   */
  private final String innerp;

  /**
   * Get inner piece of the command.
   * 
   * @return Inner piece of the command.
   */
  final String getInnerPiece() {
    return innerp;
  }

  /**
   * Parameters.
   */
  private String parms;

  @Override
  public final String getDescription() {
    return getParams();
  }

  /**
   * Set parameters.
   * 
   * @param parameters
   *          Parameters.
   */
  final void setParams(final String parameters) {
    parms = parameters;
  }

  /**
   * Template command constructor.
   *
   * @param source
   *          Source for this template.
   * @param innerPiece
   *          Inner piece.
   * @param template
   *          Template.
   */
  TemplateCommand(final String source, final String innerPiece,
      final Template template) {
    super(template);
    src = source;
    innerp = innerPiece;
  }

  /**
   * Get parameters for this template command.
   *
   * @return Parameters.
   */
  public final String getParams() {
    if (parms == null) {
      return "";
    }
    if (parms.length() > Syntax.getDirective().length()
        && parms.indexOf(Syntax.getDirective()) == 0) {
      return getTemplate().getFlywheel().get(
          parms.substring(Syntax.getDirective().length()));
    }
    return parms;
  }
}
