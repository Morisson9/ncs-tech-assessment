package com.sudoku.solver;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * Backtracking solver used both to produce a finished grid for puzzle
 * generation and to count solutions so the generator can guarantee a unique
 * answer. Grids are plain 9x9 int arrays with 0 for empty cells.
 */
public final class SudokuSolver {

    private static final int SIZE = 9;

    /** Returns a solved copy of the grid, or empty if it has no solution. */
    public Optional<int[][]> solve(int[][] grid) {
        int[][] work = copy(grid);
        if (fill(work)) {
            return Optional.of(work);
        }
        return Optional.empty();
    }

    /**
     * Produces a complete, valid grid built with the given random source so that
     * generation can be made deterministic in tests.
     */
    public int[][] generateSolved(Random random) {
        int[][] grid = new int[SIZE][SIZE];
        fillRandom(grid, random);
        return grid;
    }

    /**
     * Counts solutions up to {@code limit}. The search stops as soon as the limit
     * is reached, which keeps uniqueness checks (limit of 2) cheap.
     */
    public int countSolutions(int[][] grid, int limit) {
        return count(copy(grid), limit);
    }

    private boolean fill(int[][] grid) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (grid[row][col] == 0) {
                    for (int value = 1; value <= SIZE; value++) {
                        if (canPlace(grid, row, col, value)) {
                            grid[row][col] = value;
                            if (fill(grid)) {
                                return true;
                            }
                            grid[row][col] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private boolean fillRandom(int[][] grid, Random random) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (grid[row][col] == 0) {
                    for (int value : shuffledValues(random)) {
                        if (canPlace(grid, row, col, value)) {
                            grid[row][col] = value;
                            if (fillRandom(grid, random)) {
                                return true;
                            }
                            grid[row][col] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private int count(int[][] grid, int limit) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (grid[row][col] == 0) {
                    int found = 0;
                    for (int value = 1; value <= SIZE; value++) {
                        if (canPlace(grid, row, col, value)) {
                            grid[row][col] = value;
                            found += count(grid, limit);
                            grid[row][col] = 0;
                            if (found >= limit) {
                                return found;
                            }
                        }
                    }
                    return found;
                }
            }
        }
        return 1;
    }

    private boolean canPlace(int[][] grid, int row, int col, int value) {
        for (int i = 0; i < SIZE; i++) {
            if (grid[row][i] == value || grid[i][col] == value) {
                return false;
            }
        }
        int boxRow = (row / 3) * 3;
        int boxCol = (col / 3) * 3;
        for (int r = boxRow; r < boxRow + 3; r++) {
            for (int c = boxCol; c < boxCol + 3; c++) {
                if (grid[r][c] == value) {
                    return false;
                }
            }
        }
        return true;
    }

    private List<Integer> shuffledValues(Random random) {
        List<Integer> values = new java.util.ArrayList<>();
        for (int value = 1; value <= SIZE; value++) {
            values.add(value);
        }
        Collections.shuffle(values, random);
        return values;
    }

    private int[][] copy(int[][] grid) {
        int[][] copy = new int[SIZE][SIZE];
        for (int row = 0; row < SIZE; row++) {
            System.arraycopy(grid[row], 0, copy[row], 0, SIZE);
        }
        return copy;
    }
}
