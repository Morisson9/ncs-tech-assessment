package com.sudoku.puzzle;

import com.sudoku.model.Board;
import com.sudoku.model.Position;

/**
 * A generated puzzle: the starting grid (givens plus blanks) together with its
 * full solution. The solution is kept so the game can offer hints.
 */
public final class Puzzle {

    private static final int SIZE = 9;

    private final int[][] givens;
    private final int[][] solution;

    private Puzzle(int[][] givens, int[][] solution) {
        this.givens = givens;
        this.solution = solution;
    }

    public static Puzzle of(int[][] givens, int[][] solution) {
        return new Puzzle(deepCopy(givens), deepCopy(solution));
    }

    public Board newBoard() {
        return Board.fromGivens(deepCopy(givens));
    }

    public int solutionAt(Position position) {
        return solution[position.row()][position.col()];
    }

    public int givenCount() {
        int count = 0;
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (givens[row][col] != 0) {
                    count++;
                }
            }
        }
        return count;
    }

    private static int[][] deepCopy(int[][] grid) {
        int[][] copy = new int[grid.length][];
        for (int row = 0; row < grid.length; row++) {
            copy[row] = grid[row].clone();
        }
        return copy;
    }
}
