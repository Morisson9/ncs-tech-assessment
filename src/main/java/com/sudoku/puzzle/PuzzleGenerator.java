package com.sudoku.puzzle;

import com.sudoku.solver.SudokuSolver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Creates puzzles by solving a blank grid into a random complete solution and
 * then removing cells one at a time, keeping a removal only while the puzzle
 * still has a single solution. Removal stops once the requested number of
 * givens remains.
 */
public final class PuzzleGenerator {

    private static final int SIZE = 9;
    private static final int DEFAULT_GIVENS = 30;
    private static final int MAX_ATTEMPTS = 100;

    private final SudokuSolver solver;
    private final Random random;

    public PuzzleGenerator() {
        this(new SudokuSolver(), new Random());
    }

    public PuzzleGenerator(SudokuSolver solver, Random random) {
        this.solver = solver;
        this.random = random;
    }

    public Puzzle generate() {
        return generate(DEFAULT_GIVENS);
    }

    /**
     * Generates a puzzle with exactly {@code givens} pre-filled cells and a
     * unique solution. Seventeen is the known minimum number of givens for a
     * uniquely solvable Sudoku, so we refuse anything below that.
     */
    public Puzzle generate(int givens) {
        if (givens < 17 || givens > SIZE * SIZE) {
            throw new IllegalArgumentException("Givens must be between 17 and 81");
        }
        for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
            int[][] solution = solver.generateSolved(random);
            int[][] carved = carve(solution, givens);
            if (carved != null) {
                return Puzzle.of(carved, solution);
            }
        }
        throw new IllegalStateException(
                "Could not generate a puzzle with " + givens + " givens after " + MAX_ATTEMPTS + " attempts");
    }

    private int[][] carve(int[][] solution, int givens) {
        int[][] puzzle = deepCopy(solution);
        int removalsNeeded = SIZE * SIZE - givens;
        int removed = 0;
        for (int index : shuffledCells()) {
            if (removed == removalsNeeded) {
                break;
            }
            int row = index / SIZE;
            int col = index % SIZE;
            int backup = puzzle[row][col];
            puzzle[row][col] = 0;
            if (solver.countSolutions(puzzle, 2) == 1) {
                removed++;
            } else {
                puzzle[row][col] = backup;
            }
        }
        return removed == removalsNeeded ? puzzle : null;
    }

    private List<Integer> shuffledCells() {
        List<Integer> cells = new ArrayList<>();
        for (int i = 0; i < SIZE * SIZE; i++) {
            cells.add(i);
        }
        Collections.shuffle(cells, random);
        return cells;
    }

    private int[][] deepCopy(int[][] grid) {
        int[][] copy = new int[grid.length][];
        for (int row = 0; row < grid.length; row++) {
            copy[row] = grid[row].clone();
        }
        return copy;
    }
}
