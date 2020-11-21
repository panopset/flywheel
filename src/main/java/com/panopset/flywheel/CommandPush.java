package com.panopset.flywheel;

import java.io.StringWriter;

/**
 * <h1>p - Push</h1>
 *
 * <pre>
 *
 * ${&#064;p name}Joe${&#064;q}
 * </pre>
 *<p>
 * Everything following this command is pushed into a String buffer, until a q
 * command is reached.
 *</p>
 */
public class CommandPush extends MatchableCommand implements
    UserMatchableCommand {

  /**
   * Short HTML text for publishing command format in an HTML document.
   *
   * @return <b>${&#064;p name}</b>.
   */
  public static String getShortHtmlText() {
    return "${&#064;p name}";
  }

  /**
   * Command Push constructor.
   *
   * @param source
   *          Command source.
   * @param innerPiece
   *          Inner piece of the command.
   * @param template
   *          Template this command was declared in.
   */
  public CommandPush(final String source, final String innerPiece,
      final Template template) {
    super(source, innerPiece, template);
  }

  @Override
  public final void resolve(final StringWriter notUsed) {
    StringWriter sw = new StringWriter();
    resolveMatchedCommands(sw);
    getTemplate().getFlywheel().put(getParams(), sw.toString());
  }
}
