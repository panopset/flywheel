package com.panopset.flywheel;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import com.panopset.compat.Logop;
import com.panopset.compat.Stringop;

class RawCommandLoader {

  private final Template tmplt;

  private final Stack<String> queue = new Stack<>();

  private final List<Command> commands = new ArrayList<>();

  RawCommandLoader(final Template template) {
    assert template != null;
    tmplt = template;
  }

  List<Command> load() throws FlywheelException {
    tmplt.getTemplateSource().reset();
    while (!tmplt.getTemplateSource().isDone()) {
      if (tmplt.getFlywheel().isStopped()) {
        return new ArrayList<>();
      }
      flushQueue();
      if (tmplt.getFlywheel().isStopped()) {
        return new ArrayList<>();
      }
      String line = tmplt.getTemplateSource().next();
      if (tmplt.isLineBreaks()) {
        line = String.format("%s%s", line, Stringop.getEol());
      }
      process(line);
    }
    flushQueue();
    return commands;
  }

  private void flushQueue() throws FlywheelException {
    while (!queue.isEmpty()) {
      process(queue.pop());
      if (tmplt.getFlywheel().isStopped()) {
        return;
      }
    }
  }

  private void loadCommand(final Command command) {
    if (tmplt.getFirstCommand() == null) {
      tmplt.setFirstCommand(command);
    }
    commands.add(command);
    Logop.debug("Loading command: " + command.toString());
  }

  private void process(final String line) throws FlywheelException {
    int openDirectiveLoc = line.indexOf(Syntax.getOpenDirective());
    int closeDirectiveLoc = line.indexOf(Syntax.getCloseDirective());
    if (closeDirectiveLoc == -1 || openDirectiveLoc == -1) {
      loadCommand(new CommandText(tmplt, line));
      return;
    }
    if (closeDirectiveLoc < openDirectiveLoc) {
      skipTo(line, openDirectiveLoc);
      return;
    }
    int endOfDirective = closeDirectiveLoc + Syntax.getCloseDirective().length();
    if (openDirectiveLoc == 0) {
      String remainder = line.substring(endOfDirective);
      if (remainder.length() > 0) {
        this.queue.push(remainder);
      }
      loadCommand(
          new Command.Builder().template(tmplt).source(line, closeDirectiveLoc).construct());
    } else {
      skipTo(line, openDirectiveLoc);
    }
  }

  private void skipTo(final String ln, final int pos) {
    loadCommand(new CommandText(tmplt, ln.substring(0, pos)));
    this.queue.push(ln.substring(pos));
  }

  public static void addStructure(final List<Command> commands) {
    if (commands == null) {
      return;
    }
    Command prev = null;
    for (Command command : commands) {
      command.setPrev(prev);
      if (prev != null) {
        prev.setNext(command);
      }
      prev = command;
    }
  }
}
