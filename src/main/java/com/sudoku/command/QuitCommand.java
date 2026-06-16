package com.sudoku.command;

/** Ends the current game. */
public record QuitCommand() implements Command {

    @Override
    public CommandResult execute(GameContext context) {
        return CommandResult.quit("Thanks for playing. Goodbye!");
    }
}
