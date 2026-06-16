package com.sudoku.rules;

import com.sudoku.model.Board;
import com.sudoku.model.Position;
import com.sudoku.testutil.Puzzles;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RuleValidatorTest {

    private final RuleValidator validator = new RuleValidator();

    @Test
    void cleanPuzzleHasNoViolation() {
        Board board = Board.fromGivens(Puzzles.canonicalGivens());
        assertTrue(validator.findViolation(board).isEmpty());
    }

    @Test
    void reportsRowDuplicate() {
        Board board = Board.fromGivens(Puzzles.canonicalGivens());
        board.place(Position.of(0, 2), 3); // A3 = 3, duplicates the 3 already in row A
        Violation violation = validator.findViolation(board).orElseThrow();
        assertEquals(Violation.Type.ROW, violation.type());
        assertEquals("Number 3 already exists in Row A.", violation.message());
    }

    @Test
    void reportsColumnDuplicate() {
        Board board = Board.fromGivens(Puzzles.canonicalGivens());
        board.place(Position.of(2, 0), 5); // C1 = 5, duplicates the 5 already in column 1
        Violation violation = validator.findViolation(board).orElseThrow();
        assertEquals(Violation.Type.COLUMN, violation.type());
        assertEquals("Number 5 already exists in Column 1.", violation.message());
    }

    @Test
    void reportsSubgridDuplicate() {
        Board board = Board.fromGivens(Puzzles.canonicalGivens());
        board.place(Position.of(0, 2), 9); // A3 = 9, duplicates the 9 in the top-left box
        Violation violation = validator.findViolation(board).orElseThrow();
        assertEquals(Violation.Type.SUBGRID, violation.type());
        assertEquals("Number 9 already exists in the same 3x3 subgrid.", violation.message());
    }
}
