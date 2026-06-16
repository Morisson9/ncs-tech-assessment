package com.sudoku.io;

import com.sudoku.model.Board;

/**
 * Turns a board into the text grid shown to the player. The layout matches the
 * problem statement: a header row of column numbers, then one line per row
 * labelled A-I with empty cells shown as underscores.
 *
 * <p>Newlines are hard-coded to "\n" rather than the platform separator so the
 * output is identical everywhere and stays easy to assert on in tests.
 */
public final class BoardRenderer {

    private static final int SIZE = 9;
    private static final String NEWLINE = "\n";

    public String render(Board board) {
        StringBuilder builder = new StringBuilder();
        builder.append("    1 2 3 4 5 6 7 8 9");
        for (int row = 0; row < SIZE; row++) {
            builder.append(NEWLINE);
            builder.append("  ").append((char) ('A' + row));
            for (int col = 0; col < SIZE; col++) {
                int value = board.valueAt(row, col);
                builder.append(' ').append(value == 0 ? "_" : Integer.toString(value));
            }
        }
        return builder.toString();
    }
}
