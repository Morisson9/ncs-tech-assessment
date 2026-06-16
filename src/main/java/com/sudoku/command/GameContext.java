package com.sudoku.command;

import com.sudoku.model.Board;
import com.sudoku.puzzle.Puzzle;
import com.sudoku.rules.Violation;

import java.util.Optional;

/**
 * The slice of game state that commands are allowed to touch. Defining it in the
 * command package (and having the game package implement it) keeps the
 * dependency arrow pointing one way: commands depend on this abstraction, not on
 * the concrete game.
 */
public interface GameContext {

    Board board();

    Puzzle puzzle();

    Optional<Violation> findViolation();

    boolean isSolved();
}
