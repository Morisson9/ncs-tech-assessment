package com.sudoku.command;

import com.sudoku.model.Position;

/** Places a number in a cell. */
public record PlaceCommand(Position position, int value) implements Command {

    @Override
    public CommandResult execute(GameContext context) {
        if (context.board().isFixed(position)) {
            return CommandResult.withBoard("Invalid move. " + position + " is pre-filled.");
        }
        context.board().place(position, value);
        if (context.isSolved()) {
            return CommandResult.solved("Move accepted.");
        }
        return CommandResult.withBoard("Move accepted.");
    }
}
