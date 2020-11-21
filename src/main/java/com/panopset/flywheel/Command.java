package com.panopset.flywheel;

import java.io.StringWriter;
import com.panopset.compat.Nls;

/**
 * Abstract Flywheel command class.
 */
public abstract class Command {

  /**
   * Template that this command belongs to.
   */
  private final Template template;

  /**
   * Current line number.
   */
  private final int lineNumber;

  /**
   * Next command.
   */
  private Command nextCmd;

  /**
   * Previous command.
   */
  private Command prevCmd;

  /**
   * @return next Command.
   */
  public final Command getNext() {
    return nextCmd;
  }

  /**
   * Set next command.
   *
   * @param next
   *          Next command.
   */
  public final void setNext(final Command next) {
    nextCmd = next;
  }

  /**
   * @return Previous command.
   */
  public final Command getPrev() {
    return prevCmd;
  }

  /**
   * Set previous command.
   *
   * @param prev
   *          Previous command.
   */
  public final void setPrev(final Command prev) {
    prevCmd = prev;
  }

  /**
   * Constructor.
   *
   * @param commandTemplate
   *          Template this command was parsed from.
   */
  public Command(final Template commandTemplate) {
    template = commandTemplate;
    this.lineNumber = template.getTemplateSource().getLine();
  }

  /**
   * Get template.
   *
   * @return Template that this command was found on.
   */
  public final Template getTemplate() {
    return template;
  }

  /**
   * @return Line number that this command was defined on.
   */
  public final int getLineNumber() {
    return lineNumber;
  }

  /**
   * Resolve the command.
   *
   * @param sw
   *          StringWriter to output command results to.
   * @throws Exception
   *           Exception.
   */
  public abstract void resolve(StringWriter sw) throws FlywheelException;

  /**
   * Step resolve this command.
   *
   * @param sw
   *          StringWriter to output command results to.
   * @throws Exception
   *           Exception.
   */
  public final void resolveCommand(final StringWriter sw) throws FlywheelException {
    resolve(sw);
  }

  /**
   * @return Command description.
   */
  public abstract String getDescription();

  /**
   * Command description.
   */
  private String description;

  @Override
  public final String toString() {
    if (description == null) {
      description = getDescription() + ", line:" + getLineNumber();
    }
    return description;
  }

  /**
   * Command builder.
   */
  public static final class Builder {

    /**
     * Template.
     */
    private Template template;

    /**
     * Source.
     */
    private String source;

    /**
     * Close directive location.
     */
    private int closeDirectiveLoc;

    /**
     * Command.
     */
    private Command command;

    /**
     * @param commandTemplate
     *          Template this command was resolved from.
     * @return Builder.
     */
    public Builder template(final Template commandTemplate) {
      this.template = commandTemplate;
      return this;
    }

    /**
     * @param newSource
     *          Source.
     * @param newCloseDirectiveLoc
     *          Close directive location.
     * @return Builder.
     */
    public Builder source(final String newSource, final int newCloseDirectiveLoc) {
      source = newSource;
      closeDirectiveLoc = newCloseDirectiveLoc;
      return this;
    }

    /**
     * @param newCommand
     *          New command.
     * @return Builder.
     */
    public Builder command(final Command newCommand) {
      command = newCommand;
      return this;
    }

    /**
     * @return Command.
     */
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
            throw new FlywheelException("Invalid directive: " + cmd);
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
