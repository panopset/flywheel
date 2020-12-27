package com.panopset.flywheel;

import java.io.StringWriter;
import com.panopset.compat.Nls;

public abstract class Command {

  private final Template template;
  private final int lineNumber;
  private Command nextCmd;
  private Command prevCmd;

  public final Command getNext() {
    return nextCmd;
  }

  public final void setNext(final Command next) {
    nextCmd = next;
  }

  public final Command getPrev() {
    return prevCmd;
  }

  public final void setPrev(final Command prev) {
    prevCmd = prev;
  }

  public Command(final Template commandTemplate) {
    template = commandTemplate;
    this.lineNumber = template.getTemplateSource().getLine();
  }

  public final Template getTemplate() {
    return template;
  }

  public final int getLineNumber() {
    return lineNumber;
  }

  public abstract void resolve(StringWriter sw) throws FlywheelException;

  public final void resolveCommand(final StringWriter sw) throws FlywheelException {
    resolve(sw);
  }

  public abstract String getDescription();

  private String description;

  @Override
  public final String toString() {
    if (description == null) {
      description = getDescription() + ", line:" + getLineNumber();
    }
    return description;
  }

  public static final class Builder {
    private Template template;
    private String source;
    private int closeDirectiveLoc;
    private Command command;

    public Builder template(final Template commandTemplate) {
      this.template = commandTemplate;
      return this;
    }

    public Builder source(final String newSource, final int newCloseDirectiveLoc) {
      source = newSource;
      closeDirectiveLoc = newCloseDirectiveLoc;
      return this;
    }

    public Builder command(final Command newCommand) {
      command = newCommand;
      return this;
    }

    public Command construct() throws FlywheelException {
      Command rtn = null;
      if (command == null) {
        if (source == null) {
          throw new FlywheelException(
              Nls.xlate("Not enough information to create a command"));
        }
        final String innerPiece = source.substring(Syntax.getOpenDirective()
            .length(), closeDirectiveLoc);
        if (innerPiece.indexOf(Syntax.getDirective()) == 0
            && innerPiece.length() > 1) {
          char cmd = innerPiece.substring(1, 2).charAt(0);
          if (cmd == Commands.FILE.getCharCode()) {
            rtn = new CommandFile(source, innerPiece, template);
          } else if (cmd == Commands.PUSH.getCharCode()) {
            rtn = new CommandPush(source, innerPiece, template);
          } else if (cmd == Commands.REPLACE.getCharCode()) {
            rtn = new CommandReplace(source, innerPiece, template);
          } else if (cmd == Commands.LIST.getCharCode()) {
            rtn = new CommandList(source, innerPiece, template);
          } else if (cmd == Commands.QUIT.getCharCode()) {
            rtn = new CommandQuit(source, template);
          } else if (cmd == Commands.TEXT.getCharCode()) {
            rtn = new CommandTemplate(source, innerPiece, template);
          } else if (cmd == Commands.EXECUTE.getCharCode()) {
            rtn = new CommandExecute(source, innerPiece, template);
          } else {
            throw new FlywheelException("Undefined command, please see : " + cmd);
          }
        } else {
          rtn = new CommandVariable(source, innerPiece, template);
        }
      } else {
        rtn = command;
      }
      return rtn;
    }
  }
}
