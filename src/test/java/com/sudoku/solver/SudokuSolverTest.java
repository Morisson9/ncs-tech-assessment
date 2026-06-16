package com.sudoku.solver;

import com.sudoku.rules.RuleValidator;
import com.sudoku.model.Board;
import com.sudoku.testutil.Puzzles;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SudokuSolverTest {

    private final SudokuSolver solver = new SudokuSolver();

    @Test
    void solvesCanonicalPuzzleToExpectedSolution() {
        int[][] solved = solver.solve(Puzzles.canonicalGivens()).orElseThrow();
        assertGridEquals(Puzzles.canonicalSolution(), solved);
    }

    @Test
    void completedGridHasExactlyOneSolution() {
        assertEquals(1, solver.countSolutions(Puzzles.canonicalSolution(), 2));
    }

    @Test
    void emptyGridHasManySolutions() {
        int[][] empty = new int[9][9];
        assertEquals(2, solver.countSolutions(empty, 2));
    }

    @Test
    void generatedGridIsCompleteAndValid() {
        int[][] grid = solver.generateSolved(new Random(42));
        Board board = Board.fromGivens(grid);
        assertTrue(board.isComplete());
        assertTrue(new RuleValidator().findViolation(board).isEmpty());
    }

    private void assertGridEquals(int[][] expected, int[][] actual) {
        for (int row = 0; row < expected.length; row++) {
            assertArrayEquals(expected[row], actual[row], "row " + row);
        }
    }
}
