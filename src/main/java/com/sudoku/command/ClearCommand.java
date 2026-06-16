package com.sudoku.command;

import com.sudoku.model.Position;

/** Clears a cell the player previously filled. */
public record ClearCommand(Position position) implements Command {

    @Override
    public CommandResult execute(GameContext context) {
        if (context.board().isFixed(position)) {
            return CommandResult.withBoard("Invalid move. " + position + " is pre-filled.");
        }
        context.board().clear(position);
        return CommandResult.withBoard("Move accepted.");
    }
}
