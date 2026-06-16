package com.sudoku.command;

/** A no-op command that simply reports a message, used for input we can't act on. */
public record MessageCommand(String text) implements Command {

    @Override
    public CommandResult execute(GameContext context) {
        return CommandResult.messageOnly(text);
    }
}
