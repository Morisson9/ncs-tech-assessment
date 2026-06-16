package com.sudoku.game;

import com.sudoku.command.GameContext;
import com.sudoku.model.Board;
import com.sudoku.puzzle.Puzzle;
import com.sudoku.rules.RuleValidator;
import com.sudoku.rules.Violation;

import java.util.Optional;

/**
 * Holds the live state for one game: the board being edited, the puzzle it came
 * from, and the validator used to judge it. The puzzle is considered solved once
 * every cell is filled and no rule is broken.
 */
public final class GameState implements GameContext {

    private final Board board;
    private final Puzzle puzzle;
    private final RuleValidator validator;

    public GameState(Puzzle puzzle, RuleValidator validator) {
        this.puzzle = puzzle;
        this.board = puzzle.newBoard();
        this.validator = validator;
    }

    @Override
    public Board board() {
        return board;
    }

    @Override
    public Puzzle puzzle() {
        return puzzle;
    }

    @Override
    public Optional<Violation> findViolation() {
        return validator.findViolation(board);
    }

    @Override
    public boolean isSolved() {
        return board.isComplete() && validator.findViolation(board).isEmpty();
    }
}
