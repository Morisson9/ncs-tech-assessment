package com.sudoku.puzzle;

import com.sudoku.model.Board;
import com.sudoku.model.Position;
import com.sudoku.rules.RuleValidator;
import com.sudoku.solver.SudokuSolver;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PuzzleGeneratorTest {

    private final SudokuSolver solver = new SudokuSolver();

    @Test
    void generatesExactlyThirtyGivens() {
        PuzzleGenerator generator = new PuzzleGenerator(solver, new Random(7));
        Puzzle puzzle = generator.generate();
        assertEquals(30, puzzle.givenCount());
    }

    @Test
    void generatedPuzzleHasUniqueSolution() {
        PuzzleGenerator generator = new PuzzleGenerator(solver, new Random(7));
        Puzzle puzzle = generator.generate();
        Board board = puzzle.newBoard();
        assertEquals(1, solver.countSolutions(board.snapshot(), 2));
    }

    @Test
    void givensMatchTheStoredSolution() {
        PuzzleGenerator generator = new PuzzleGenerator(solver, new Random(99));
        Puzzle puzzle = generator.generate();
        Board board = puzzle.newBoard();
        for (int row = 0; row < Board.SIZE; row++) {
            for (int col = 0; col < Board.SIZE; col++) {
                Position position = Position.of(row, col);
                if (!board.isEmpty(position)) {
                    assertEquals(puzzle.solutionAt(position), board.valueAt(position));
                }
            }
        }
    }

    @Test
    void solutionGridIsValid() {
        PuzzleGenerator generator = new PuzzleGenerator(solver, new Random(99));
        Puzzle puzzle = generator.generate();
        int[][] solved = solver.solve(puzzle.newBoard().snapshot()).orElseThrow();
        assertTrue(new RuleValidator().findViolation(Board.fromGivens(solved)).isEmpty());
    }

    @Test
    void rejectsTooFewGivens() {
        PuzzleGenerator generator = new PuzzleGenerator(solver, new Random(1));
        assertThrows(IllegalArgumentException.class, () -> generator.generate(16));
    }
}
