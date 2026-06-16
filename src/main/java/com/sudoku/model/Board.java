package com.sudoku.model;

/**
 * The 9x9 playing grid. The board owns its cells and enforces the basic rules
 * about which cells may be changed; it does not know anything about Sudoku
 * validity (that lives in the rules package) beyond whether a cell is filled.
 */
public final class Board {

    public static final int SIZE = 9;

    private final Cell[][] cells;

    private Board(Cell[][] cells) {
        this.cells = cells;
    }

    public static Board fromGivens(int[][] givens) {
        if (givens.length != SIZE) {
            throw new IllegalArgumentException("Grid must have " + SIZE + " rows");
        }
        Cell[][] cells = new Cell[SIZE][SIZE];
        for (int row = 0; row < SIZE; row++) {
            if (givens[row].length != SIZE) {
                throw new IllegalArgumentException("Grid must have " + SIZE + " columns");
            }
            for (int col = 0; col < SIZE; col++) {
                int value = givens[row][col];
                cells[row][col] = value == 0 ? Cell.empty() : Cell.given(value);
            }
        }
        return new Board(cells);
    }

    public int valueAt(int row, int col) {
        return cells[row][col].value();
    }

    public int valueAt(Position position) {
        return valueAt(position.row(), position.col());
    }

    public boolean isFixed(Position position) {
        return cells[position.row()][position.col()].isFixed();
    }

    public boolean isEmpty(Position position) {
        return cells[position.row()][position.col()].isEmpty();
    }

    public void place(Position position, int value) {
        cells[position.row()][position.col()].setValue(value);
    }

    public void clear(Position position) {
        cells[position.row()][position.col()].clear();
    }

    public boolean isComplete() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (cells[row][col].isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    public int[][] snapshot() {
        int[][] copy = new int[SIZE][SIZE];
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                copy[row][col] = cells[row][col].value();
            }
        }
        return copy;
    }
}
