package com.sudoku.model;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PositionTest {

    @Test
    void parsesTopLeftCorner() {
        Position position = Position.tryParse("A1").orElseThrow();
        assertEquals(0, position.row());
        assertEquals(0, position.col());
    }

    @Test
    void parsesBottomRightCorner() {
        Position position = Position.tryParse("I9").orElseThrow();
        assertEquals(8, position.row());
        assertEquals(8, position.col());
    }

    @Test
    void parsingIsCaseInsensitiveAndTrimmed() {
        assertEquals(Position.of(1, 2), Position.tryParse("  b3 ").orElseThrow());
    }

    @Test
    void rejectsOutOfRangeOrMalformedInput() {
        for (String bad : new String[]{"Z1", "A0", "A10", "AA", "", "3A", "1"}) {
            assertTrue(Position.tryParse(bad).isEmpty(), "expected empty for: " + bad);
        }
    }

    @Test
    void roundTripsThroughToString() {
        assertEquals("C5", Position.of(2, 4).toString());
    }

    @Test
    void ofRejectsOutOfRangeIndices() {
        assertThrows(IllegalArgumentException.class, () -> Position.of(9, 0));
        assertThrows(IllegalArgumentException.class, () -> Position.of(0, -1));
    }

    @Test
    void equalPositionsShareHashCode() {
        Optional<Position> first = Position.tryParse("D4");
        Optional<Position> second = Position.tryParse("d4");
        assertEquals(first, second);
        assertEquals(first.orElseThrow().hashCode(), second.orElseThrow().hashCode());
    }
}
