package com.panopset.flywheel;

/**
 * <h1>q - Quit</h1>
 *
 * <pre>
 * ${&#064;q}
 * </pre>
 * <ul>
 * <li>If the q command follows a p command, the String buffer is defined as the
 * variable name that was provided in the p command.</li>
 * <li>If the q command follows an l command, the String buffer is read for the
 * each item in the list.</li>
 * </ul>
 *
 * @author Karl Dinwiddie
 *
 */
public class CommandQuit extends TemplateDirectiveCommand {

  /**
   * Matchable command that this Quit command matches and closes out.
   */
  private MatchableCommand mc;

  /**
   * Short HTML text for publishing command format in an HTML document.
   *
   * @return <b>${&#064;q}</b>.
   */
  public static String getShortHtmlText() {
    return "${&#064;q}";
  }

  /**
   * Get matchable command.
   *
   * @return Matchable command that this Command Quit resolves.
   */
  public final MatchableCommand getMatchableCommand() {
    return mc;
  }

  /**
   * Command Quit constructor.
   *
   * @param source
   *          Command source.
   * @param template
   *          Template this command was declared in.
   */
  public CommandQuit(final String source, final Template template) {
    super(source, "", template);
  }

  /**
   * Match command.
   *
   * @param matchableCommand
   *          Command to match this Command Quit with.
   */
  protected final void match(final MatchableCommand matchableCommand) {
    matchableCommand.setCommandQuit(this);
    mc = matchableCommand;
  }
}
