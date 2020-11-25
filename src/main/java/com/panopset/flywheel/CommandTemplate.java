package com.panopset.flywheel;

import java.io.StringWriter;

/**
 * <h1>t - Template</h1>
 *
 * <p>
 * Example 1
 * </p>
 *
 * <pre>
 * ${&#064;t someTemplateFile.txt}
 * </pre>
 *
 * <p>
 * Example 2
 * </p>
 *
 * <pre>
 * ${&#064;p templateName}someTemplateFile.txt${&#064;q}
 * ${&#064;t &#064;templateName}
 * </pre>
 *
 * <p>
 * Continue execution using the supplied template file.
 * </p>
 */
public class CommandTemplate extends TemplateDirectiveCommand {

  /**
   * Last command.
   */
  private Command lastCommand;

  /**
   * Short HTML text for publishing command format in an HTML document.
   *
   * @return <b>${&#064;t someTemplateFile.txt}</b>.
   */
  public static String getShortHtmlText() {
    return "${&#064;t someTemplateFile.txt}";
  }

  /**
   * Command Template constructor.
   *
   * @param source
   *          Command source.
   * @param innerPiece
   *          Inner piece of the command.
   * @param template
   *          Template this command was declared in.
   */
  public CommandTemplate(final String source, final String innerPiece,
      final Template template) {
    super(source, innerPiece, template);
  }

  @Override
  public final void resolve(final StringWriter sw) {
    new Template(getTemplate().getFlywheel(), new SourceFile(getTemplate()
        .getFlywheel(), getParams()), getTemplate().getTemplateRules()).exec(sw);
  }

  /**
   * Get last command.
   *
   * @return Last command.
   */
  public final Command getLastCommand() {
    return lastCommand;
  }

  /**
   * Set last command.
   *
   * @param command
   *          Last command.
   */
  public final void setLastCommand(final Command command) {
    lastCommand = command;
  }
}
