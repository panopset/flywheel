package com.panopset.flywheel;

import java.io.StringWriter;
import com.panopset.compat.Stringop;

/**
 * <h1>p - Replace</h1>
 *
 * <p>
 * In this example, &quot;Joe&quot; will be replaced with &quot;Mary&quot;.
 * </p>
 * 
 * <pre>
 *
 * ${&#064;p name}Joe${&#064;q}${&#064;r name}Mary${&#064;q}
 *
 * </pre>
 * 
 * <p>
 * In this example, a prior replacement is undone.
 * </p>
 * 
 * <pre>
 *
 * ${&#064;r name}${name}${&#064;q}
 *
 * </pre>
 *
 * @author Karl Dinwiddie
 *
 */
public class CommandReplace extends MatchableCommand implements
    UserMatchableCommand {

  /**
   * Short HTML text for publishing command format in an HTML document.
   *
   * @return <b>${&#064;r name}</b>.
   */
  public static String getShortHtmlText() {
    return "${&#064;r name}";
  }

  /**
   * Command Replace constructor.
   *
   * @param source
   *          Command source.
   * @param innerPiece
   *          Inner piece of the command.
   * @param template
   *          Template this command was declared in.
   */
  public CommandReplace(final String source, final String innerPiece,
      final Template template) {
    super(source, innerPiece, template);
  }

  @Override
  public final void resolve(final StringWriter notUsed) {
    StringWriter sw = new StringWriter();
    resolveMatchedCommands(sw);
    String toReplace = getTemplate().getFlywheel().get(getParams());
    String key = getParams();
    if (Stringop.isPopulated(toReplace)) {
      key = toReplace;
    }
    getTemplate().getFlywheel().getReplacements()
        .add(new String[] { key, sw.toString() });
  }
}
