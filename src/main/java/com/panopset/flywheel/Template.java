package com.panopset.flywheel;

import java.io.StringWriter;
import java.util.List;
import com.panopset.compat.Nls;
import com.panopset.compat.Logop;

/**
 * Once a Flywheel Script file is read, it becomes a template to be processed. Templates may also be
 * processed using the template directive within any template.
 */
public final class Template {
  private final Flywheel fw;
  private final TemplateSource ts;
  private SourceFile sf;
  private final LineFeedRules templateRules;

  public Template(final Flywheel flywheel, final SourceFile sourceFile,
      LineFeedRules templateRules) {
    fw = flywheel;
    sf = sourceFile;
    ts = new TemplateFile(sourceFile.getFile());
    this.templateRules = templateRules;
  }

  public Template(final Flywheel flywheel, final TemplateSource templateSource,
      LineFeedRules templateRules) {
    fw = flywheel;
    sf = null;
    ts = templateSource;
    this.templateRules = templateRules;
  }

  public Template(final Flywheel flywheel, final TemplateSource templateSource) {
    this(flywheel, templateSource, flywheel.getLineFeedRules());
  }

  public LineFeedRules getTemplateRules() {
    return templateRules;
  }

  private Command firstCommand;

  public Command getFirstCommand() {
    return firstCommand;
  }

  public void setFirstCommand(final Command command) {
    firstCommand = command;
  }

  private CommandFile commandFil;

  public CommandFile getCommandFile() {
    return commandFil;
  }

  public void setCommandFile(final CommandFile commandFile) {
    commandFil = commandFile;
  }

  private List<Command> rawCommands;

  void exec(final StringWriter stringWriter) {
    for (Command topCommand : getTopCommands()) {
      if (fw.isStopped()) {
        return;
      }
      try {
        topCommand.resolve(stringWriter);
      } catch (FlywheelException e) {
        Logop.error(e);
        Logop.warn("Relative path: " + this.getRelativePath());
        fw.getTemplate().getFlywheel().stop(e.getMessage());
        return;
      }
    }
  }

  private List<Command> getRawCommands() throws FlywheelException {
    if (rawCommands == null) {
      rawCommands = new RawCommandLoader(this).load();
      RawCommandLoader.addStructure(rawCommands);
    }
    return rawCommands;
  }

  private List<Command> topCommands;

  public List<Command> getTopCommands() {
    if (topCommands == null) {
      try {
        List<Command> commands = new ImpliedQuitFilter().addImpliedQuits(getRawCommands());
        topCommands = CommandMatcher.matchQuitCommands(commands);
      } catch (FlywheelException ex) {
        stop(ex.getMessage());
      }
    }
    return topCommands;
  }

  public void output() {
    List<Command> topCommands = getTopCommands();
    if (topCommands == null) {
      return;
    }
    try {
      for (Command topCommand : topCommands) {
        if (topCommand == null || fw == null || fw.getWriter() == null) {
          return;
        }
        topCommand.resolveCommand(fw.getWriter());
      }
    } catch (FlywheelException t) {
      Logop.error(t);
      stop(t.getMessage());
    }
  }

  private void stop(final String msg) {
    Logop.error(String.format("Stopped while processing line %d: %s.", ts.getLine(), ts.getName()));
    fw.stop(msg);
  }

  public Flywheel getFlywheel() {
    return fw;
  }

  public String getRelativePath() {
    if (sf == null) {
      return "";
    }
    return sf.getRelativePath();
  }

  public TemplateSource getTemplateSource() {
    return ts;
  }
}
