package com.panopset.flywheel;

import java.io.StringWriter;

/**
 * Command that is created from a directive.
 *
 * @author Karl Dinwiddie
 */
public class TemplateDirectiveCommand extends TemplateCommand {

  /**
   * Template directive command.
   *
   * @param source
   *          Source.
   * @param innerPiece
   *          Inner piece.
   * @param template
   *          Template.
   */
  public TemplateDirectiveCommand(final String source, final String innerPiece,
      final Template template) {
    super(source, innerPiece, template);
    String params = "";
    if (innerPiece.length() > 2) {
      params = innerPiece.substring(Syntax.getDirective().length() + 2);
    }
    setParams(params);
  }

  @Override
  public void resolve(final StringWriter sw) throws FlywheelException {
    // does nothing here.
  }

}
