package com.sudoku.rules;

import com.sudoku.model.Board;

import java.util.Optional;

/**
 * Checks a board for duplicate numbers. Rows are scanned first, then columns,
 * then 3x3 subgrids, and the first duplicate found is reported.
 */
public final class RuleValidator {

    private static final int SIZE = 9;

    public Optional<Violation> findViolation(Board board) {
        Optional<Violation> rowViolation = checkRows(board);
        if (rowViolation.isPresent()) {
            return rowViolation;
        }
        Optional<Violation> columnViolation = checkColumns(board);
        if (columnViolation.isPresent()) {
            return columnViolation;
        }
        return checkSubgrids(board);
    }

    private Optional<Violation> checkRows(Board board) {
        for (int row = 0; row < SIZE; row++) {
            boolean[] seen = new boolean[SIZE + 1];
            for (int col = 0; col < SIZE; col++) {
                int value = board.valueAt(row, col);
                if (value == 0) {
                    continue;
                }
                if (seen[value]) {
                    return Optional.of(Violation.row(value, (char) ('A' + row)));
                }
                seen[value] = true;
            }
        }
        return Optional.empty();
    }

    private Optional<Violation> checkColumns(Board board) {
        for (int col = 0; col < SIZE; col++) {
            boolean[] seen = new boolean[SIZE + 1];
            for (int row = 0; row < SIZE; row++) {
                int value = board.valueAt(row, col);
                if (value == 0) {
                    continue;
                }
                if (seen[value]) {
                    return Optional.of(Violation.column(value, col + 1));
                }
                seen[value] = true;
            }
        }
        return Optional.empty();
    }

    private Optional<Violation> checkSubgrids(Board board) {
        for (int boxRow = 0; boxRow < SIZE; boxRow += 3) {
            for (int boxCol = 0; boxCol < SIZE; boxCol += 3) {
                boolean[] seen = new boolean[SIZE + 1];
                for (int r = boxRow; r < boxRow + 3; r++) {
                    for (int c = boxCol; c < boxCol + 3; c++) {
                        int value = board.valueAt(r, c);
                        if (value == 0) {
                            continue;
                        }
                        if (seen[value]) {
                            return Optional.of(Violation.subgrid(value));
                        }
                        seen[value] = true;
                    }
                }
            }
        }
        return Optional.empty();
    }
}
