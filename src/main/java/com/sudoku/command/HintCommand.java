package com.sudoku.command;

import com.sudoku.model.Board;
import com.sudoku.model.Position;

/**
 * Reveals one correct number. The first empty cell in reading order is filled
 * from the stored solution, which keeps the behaviour predictable and easy to
 * test.
 */
public record HintCommand() implements Command {

    @Override
    public CommandResult execute(GameContext context) {
        Position target = firstEmpty(context.board());
        if (target == null) {
            return CommandResult.messageOnly("The grid is already full.");
        }
        int value = context.puzzle().solutionAt(target);
        context.board().place(target, value);
        String message = "Hint: Cell " + target + " = " + value;
        if (context.isSolved()) {
            return CommandResult.solved(message);
        }
        return CommandResult.withBoard(message);
    }

    private Position firstEmpty(Board board) {
        for (int row = 0; row < Board.SIZE; row++) {
            for (int col = 0; col < Board.SIZE; col++) {
                Position position = Position.of(row, col);
                if (board.isEmpty(position)) {
                    return position;
                }
            }
        }
        return null;
    }
}
