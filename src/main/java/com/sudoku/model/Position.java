package com.sudoku.model;

import java.util.Optional;

/**
 * A cell coordinate on the board.
 *
 * <p>Rows are labelled A-I and columns 1-9 in the user-facing notation, but
 * internally both are stored as zero-based indices (0-8) so the model can use
 * them directly as array offsets.
 */
public final class Position {

    private final int row;
    private final int col;

    private Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public static Position of(int row, int col) {
        if (row < 0 || row > 8 || col < 0 || col > 8) {
            throw new IllegalArgumentException("Row and column must be between 0 and 8");
        }
        return new Position(row, col);
    }

    /**
     * Parses notation such as "B3" into a position. Input is trimmed and treated
     * case-insensitively. Returns an empty optional when the text is not a valid
     * coordinate so callers can produce a friendly error message.
     */
    public static Optional<Position> tryParse(String text) {
        if (text == null) {
            return Optional.empty();
        }
        String trimmed = text.trim().toUpperCase();
        if (trimmed.length() != 2) {
            return Optional.empty();
        }
        char rowChar = trimmed.charAt(0);
        char colChar = trimmed.charAt(1);
        if (rowChar < 'A' || rowChar > 'I' || colChar < '1' || colChar > '9') {
            return Optional.empty();
        }
        return Optional.of(new Position(rowChar - 'A', colChar - '1'));
    }

    public int row() {
        return row;
    }

    public int col() {
        return col;
    }

    @Override
    public String toString() {
        return "" + (char) ('A' + row) + (char) ('1' + col);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Position position)) {
            return false;
        }
        return row == position.row && col == position.col;
    }

    @Override
    public int hashCode() {
        return row * 9 + col;
    }
}
