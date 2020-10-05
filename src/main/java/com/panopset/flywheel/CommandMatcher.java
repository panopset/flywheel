package com.panopset.flywheel;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Flywheel command matcher.
 *
 * @author Karl Dinwiddie
 */
final class CommandMatcher {

  /**
   * Given a List of commands, tie the quit commands to the MatchableCommands.
   *
   * @param commands
   *          Commands to add structure to.
   * @return New List of commands.
   * @throws Exception
   *           Exception is thrown if there is an unmatched quit.
   */
  protected static List<Command> matchQuitCommands(final List<Command> commands)
      throws Exception {
    final List<Command> rtn = new ArrayList<Command>();
    final Stack<MatchableCommand> stack = new Stack<MatchableCommand>();
    for (Command command : commands) {
      if (stack.isEmpty()) {
        if (!(command instanceof MatchableCommand)) {
          rtn.add(command);
        }
      } else {
        stack.peek().getCommands().add(command);
      }
      if (command instanceof MatchableCommand) {
        if (stack.isEmpty() || command instanceof CommandFile) {
          rtn.add((MatchableCommand) command);
        }
        stack.push((MatchableCommand) command);
      } else if (command instanceof CommandQuit) {
        if (stack.isEmpty()) {
          throw new Exception("Un-matched quit found, stopping.");
        }
        stack.pop();
      }
    }
    return rtn;
  }

  /**
   * Prevent instantiation.
   */
  private CommandMatcher() {
  }
}
