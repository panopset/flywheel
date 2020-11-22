package com.panopset.flywheel;

import java.io.StringWriter;
import com.panopset.compat.Logop;

/**
 * <h1>e - Execute</h1>
 * <p>
 * Excecute any public static method, defined as a Java System property,
 * where the key starts with "com.panopset.flywheel.cmdkey." and finishes
 * with the name of the command to be used in the e command definition.
 * The function returned by the key must be one that returns a String and takes 0 or more
 * String parameters. If parameters match variable names, then they will be
 * replaced by the variable value, otherwise the parameter will be used as is.
 * </p>
 * <p>
 * You may register objects that will be available using the
 * Flywheel.Builder.registerObject function. The keys must be one word. Instance
 * methods may be called from registered objects.
 * </p>
 *
 * <pre>
 *
 * ${&#064;p name}panopset${&#064;q}
 * 
 * ${&#064;e capitalize(name)}
 *
 * </pre>
 * 
 * <p>
 * The above script will output:
 * </p>
 * 
 * <pre>
 *
 * Panopset
 *
 * </pre>
 */
public class CommandExecute extends TemplateDirectiveCommand {
  
  static final String CAPITALIZE_CMD = "${&#064; capitalize(name)}";

  /**
   * Short HTML text for publishing command format in an HTML document.
   *
   * @return <b>${&#064;e capitalize(name)}</b>.
   */
  public static String getShortHtmlText() {
    return CAPITALIZE_CMD;
  }

  /**
   * Command Execute constructor.
   *
   * @param source
   *          Command source.
   * @param innerPiece
   *          Inner piece of the command.
   * @param template
   *          Template this command was declared in.
   */
  public CommandExecute(final String source, final String innerPiece,
      final Template template) {
    super(source, innerPiece, template);
  }

  @Override
  public final void resolve(final StringWriter sw) throws FlywheelException {
    try {
      String str = getParams();
      int incr = str.lastIndexOf(".");
      if (incr > -1) {
        String key = str.substring(0, incr);
        Object object = getTemplate().getFlywheel().getRegisteredObjects()
            .get(key);
        if (object != null) {
          sw.append(new ReflectionInvoker.Builder().object(object)
              .methodAndParms(str.substring(incr + 1))
              .mapProvider(getTemplate().getFlywheel()).construct().exec());
          return;
        }
      }
      sw.append(new ReflectionInvoker.Builder()
          .classMethodAndParms(getParams())
          .mapProvider(getTemplate().getFlywheel()).construct().exec());
    } catch (Exception e) {
      if (getTemplate() != null) {
        Logop.warn("Line: " + getLineNumber() + " "
            + getTemplate().getTemplateSource().getName());
        Logop.warn(getTemplate().getRelativePath());
        if (getTemplate().getCommandFile() != null) {
          Logop.warn("Output file: " + getTemplate().getCommandFile());
          Logop.warn("source: " + getSource());
        }
      }
      Logop.error(e);
      if (getTemplate() != null && getTemplate().getFlywheel() != null) {
        getTemplate().getFlywheel().stop(e.getMessage());
      }
      Logop.warn("Failure executing " + getSource());
      throw new FlywheelException("Execution terminated, see log.", e);
    }
  }

}
