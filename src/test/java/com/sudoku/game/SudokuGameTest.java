package com.sudoku.game;

import com.sudoku.model.Position;
import com.sudoku.puzzle.Puzzle;
import com.sudoku.rules.RuleValidator;
import com.sudoku.testutil.Puzzles;
import com.sudoku.testutil.RecordingConsole;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SudokuGameTest {

    private final RuleValidator validator = new RuleValidator();

    @Test
    void completingTheGridWinsTheGame() {
        Puzzle puzzle = Puzzle.of(
                Puzzles.solutionWithBlanks(Position.of(0, 0), Position.of(0, 2)),
                Puzzles.canonicalSolution());
        RecordingConsole console = new RecordingConsole(List.of("A1 5", "A3 4"));

        GameResult result = playGame(puzzle, console);

        assertEquals(GameResult.WON, result);
        assertTrue(console.output().contains("Move accepted."));
        assertTrue(console.output().contains("You have successfully completed the Sudoku puzzle!"));
    }

    @Test
    void preFilledCellsCannotBeChanged() {
        Puzzle puzzle = Puzzle.of(Puzzles.canonicalGivens(), Puzzles.canonicalSolution());
        RecordingConsole console = new RecordingConsole(List.of("A1 6"));

        GameResult result = playGame(puzzle, console);

        assertEquals(GameResult.QUIT, result);
        assertTrue(console.output().contains("Invalid move. A1 is pre-filled."));
    }

    @Test
    void checkReportsARowViolation() {
        Puzzle puzzle = Puzzle.of(
                Puzzles.solutionWithBlanks(Position.of(0, 2)),
                Puzzles.canonicalSolution());
        RecordingConsole console = new RecordingConsole(List.of("A3 5", "check"));

        playGame(puzzle, console);

        assertTrue(console.output().contains("Number 5 already exists in Row A."));
    }

    @Test
    void hintFillsTheLastCellAndWins() {
        Puzzle puzzle = Puzzle.of(
                Puzzles.solutionWithBlanks(Position.of(0, 2)),
                Puzzles.canonicalSolution());
        RecordingConsole console = new RecordingConsole(List.of("hint"));

        GameResult result = playGame(puzzle, console);

        assertEquals(GameResult.WON, result);
        assertTrue(console.output().contains("Hint: Cell A3 = 4"));
    }

    private GameResult playGame(Puzzle puzzle, RecordingConsole console) {
        GameState state = new GameState(puzzle, validator);
        return new SudokuGame(state, console).play();
    }
}
