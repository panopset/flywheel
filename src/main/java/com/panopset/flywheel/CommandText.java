package com.panopset.flywheel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import com.panopset.compat.Logop;

/**
 * Command to handle normal text entry.
 *
 * @author Karl Dinwiddie
 */
public class CommandText extends Command {

  /**
   * Command text.
   */
  private String text;

  /**
   * Command Text constructor.
   *
   * @param template
   *          Template this command was declared in.
   * @param params
   *          Params.
   */
  public CommandText(final Template template, final String params) {
    super(template);
    assert params != null;
    this.text = params;
  }

  @Override
  public final String getDescription() {
    StringReader sr = new StringReader(text);
    try (BufferedReader br = new BufferedReader(sr)) {
      String str = br.readLine();
      if (str == null) {
        return str = "";
      }
      return "CommandText:" + str;
    } catch (IOException ex) {
      Logop.error(ex);
      return ex.getMessage();
    }
  }

  @Override
  public final void resolve(final StringWriter sw) {
    String rtn = text;
    if (!getTemplate().getFlywheel().isReplacementsSuppressed()) {
      for (String[] s : getTemplate().getFlywheel().getReplacements()) {
        rtn = rtn.replace(s[0], s[1]);
      }
    }
    sw.append(rtn);
  }
}
