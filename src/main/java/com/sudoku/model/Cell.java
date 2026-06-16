package com.sudoku.model;

/**
 * A single square on the board. A cell is either a fixed given (part of the
 * generated puzzle and not editable) or an ordinary cell the player can fill in
 * and clear. A value of zero means the cell is empty.
 */
public final class Cell {

    static final int EMPTY = 0;

    private int value;
    private final boolean fixed;

    private Cell(int value, boolean fixed) {
        this.value = value;
        this.fixed = fixed;
    }

    public static Cell given(int value) {
        if (value < 1 || value > 9) {
            throw new IllegalArgumentException("A given must be between 1 and 9");
        }
        return new Cell(value, true);
    }

    public static Cell empty() {
        return new Cell(EMPTY, false);
    }

    public int value() {
        return value;
    }

    public boolean isFixed() {
        return fixed;
    }

    public boolean isEmpty() {
        return value == EMPTY;
    }

    public void setValue(int newValue) {
        if (fixed) {
            throw new IllegalStateException("Cannot change a pre-filled cell");
        }
        if (newValue < 1 || newValue > 9) {
            throw new IllegalArgumentException("Value must be between 1 and 9");
        }
        this.value = newValue;
    }

    public void clear() {
        if (fixed) {
            throw new IllegalStateException("Cannot clear a pre-filled cell");
        }
        this.value = EMPTY;
    }
}
