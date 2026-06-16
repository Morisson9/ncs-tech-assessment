package com.sudoku.command;

/**
 * A single user action. Each command knows how to carry itself out against the
 * game context and report what happened.
 */
public interface Command {

    CommandResult execute(GameContext context);
}
