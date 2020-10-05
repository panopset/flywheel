package com.panopset.flywheel;

import java.io.StringWriter;
import com.panopset.compat.Logop;
import com.panopset.gp.ReflectionInvoker;

/**
 * <h1>e - Execute</h1>
 * <p>
 * Excecute any public static method, which returns a String and takes 0 or more
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
 * ${&#064;e com.panopset.compat.Strings.capitalize(name)}
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
 *
 * @author Karl Dinwiddie
 */
public class CommandExecute extends TemplateDirectiveCommand {

  /**
   * Short HTML text for publishing command format in an HTML document.
   *
   * @return <b>${&#064;e com.panopset.compat.Strings.capitalize(name)}</b>.
   */
  public static String getShortHtmlText() {
    return "${&#064;e com.panopset.compat.Strings.capitalize(name)}";
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
