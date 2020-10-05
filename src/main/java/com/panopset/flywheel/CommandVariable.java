package com.panopset.flywheel;

import java.io.StringWriter;
import com.panopset.compat.Stringop;

/**
 * <h1>Variable</h1> There is no command associated with a variable, so you drop
 * the <b>&#064;</b> directive indicator, and then you specify a variable just
 * as you would in any ant script or unix shell. The variable must have been
 * defined either in a map provided to the script through
 * Flywheel.Builder.mergeMap, or a Push command. Please do not define numbers as
 * variable names, as they are used in list processing.
 *
 * <pre>
 * ${variableName}
 * 
 * Flywheel pre-defined variables:
 * com.panopset.flywheel.template
 *
 * </pre>
 *
 * @author Karl Dinwiddie
 *
 */
public final class CommandVariable extends TemplateCommand {

  /**
   * Short HTML text for publishing command format in an HTML document.
   *
   * @return <b>${variableName}</b>.
   */
  public static String getShortHtmlText() {
    return "${variableName}";
  }

  /**
   * Command Variable constructor.
   *
   * @param source
   *          Command source.
   * @param innerPiece
   *          Inner piece of the command.
   * @param template
   *          Template this command was declared in.
   */
  public CommandVariable(final String source, final String innerPiece,
      final Template template) {
    super(source, innerPiece, template);
    setParams(innerPiece);
  }

  @Override
  public void resolve(final StringWriter sw) {
    String parms = getParams();
    if (Stringop.isPopulated(parms)) {
      if (parms.equals(ReservedWords.TEMPLATE)) {
        sw.append(getTemplate().getRelativePath());
      } else {
        String tmplt = getTemplate().getFlywheel().get(getParams());
        if (tmplt != null
            && !getTemplate().getFlywheel().isReplacementsSuppressed()) {
          for (String[] s : getTemplate().getFlywheel().getReplacements()) {
            tmplt = tmplt.replace(s[0], s[1]);
          }
        }
        if (tmplt != null) {
          sw.append(tmplt);
        }
      }
    }
  }
}
