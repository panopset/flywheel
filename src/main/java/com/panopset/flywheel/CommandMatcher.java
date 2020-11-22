package com.panopset.flywheel;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Flywheel command matcher.
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
      throws FlywheelException {
    final List<Command> rtn = new ArrayList<>();
    final Deque<MatchableCommand> stack = new ArrayDeque<>();
    for (Command command : commands) {
      updateStack(stack, rtn, command);
      if (command instanceof MatchableCommand) {
        if (stack.isEmpty() || command instanceof CommandFile) {
          rtn.add(command);
        }
        stack.push((MatchableCommand) command);
      } else if (command instanceof CommandQuit) {
        if (stack.isEmpty()) {
          throw new FlywheelException("Un-matched quit found, stopping.");
        }
        stack.pop();
      }
    }
    return rtn;
  }
  
  private static void updateStack(Deque<MatchableCommand> stack, List<Command> rtn, Command command ) {
    if (stack.isEmpty()) {
      if (!(command instanceof MatchableCommand)) {
        rtn.add(command);
      }
    } else {
      stack.peek().getCommands().add(command);
    }
  }

  private CommandMatcher() {
  }
}
