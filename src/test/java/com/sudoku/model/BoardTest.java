package com.sudoku.model;

import com.sudoku.testutil.Puzzles;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BoardTest {

    @Test
    void marksNonZeroCellsAsFixedAndZeroCellsAsEmpty() {
        Board board = Board.fromGivens(Puzzles.canonicalGivens());
        assertTrue(board.isFixed(Position.of(0, 0)));
        assertEquals(5, board.valueAt(Position.of(0, 0)));
        assertTrue(board.isEmpty(Position.of(0, 2)));
        assertFalse(board.isFixed(Position.of(0, 2)));
    }

    @Test
    void placeAndClearAffectEditableCells() {
        Board board = Board.fromGivens(Puzzles.canonicalGivens());
        Position cell = Position.of(0, 2);
        board.place(cell, 4);
        assertEquals(4, board.valueAt(cell));
        board.clear(cell);
        assertTrue(board.isEmpty(cell));
    }

    @Test
    void placingOnFixedCellThrows() {
        Board board = Board.fromGivens(Puzzles.canonicalGivens());
        assertThrows(IllegalStateException.class, () -> board.place(Position.of(0, 0), 4));
    }

    @Test
    void completeBoardIsReportedComplete() {
        Board board = Board.fromGivens(Puzzles.canonicalSolution());
        assertTrue(board.isComplete());
    }

    @Test
    void boardWithBlanksIsNotComplete() {
        Board board = Board.fromGivens(Puzzles.canonicalGivens());
        assertFalse(board.isComplete());
    }
}
