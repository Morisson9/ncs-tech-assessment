package com.sudoku.command;

import com.sudoku.model.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class CommandParserTest {

    private final CommandParser parser = new CommandParser();

    @Test
    void parsesPlacement() {
        PlaceCommand command = assertInstanceOf(PlaceCommand.class, parser.parse("A3 4"));
        assertEquals(Position.of(0, 2), command.position());
        assertEquals(4, command.value());
    }

    @Test
    void parsesLowerCasePlacement() {
        PlaceCommand command = assertInstanceOf(PlaceCommand.class, parser.parse("b3 7"));
        assertEquals(Position.of(1, 2), command.position());
        assertEquals(7, command.value());
    }

    @Test
    void parsesClear() {
        ClearCommand command = assertInstanceOf(ClearCommand.class, parser.parse("C5 clear"));
        assertEquals(Position.of(2, 4), command.position());
    }

    @Test
    void parsesKeywordsCaseInsensitively() {
        assertInstanceOf(HintCommand.class, parser.parse("hint"));
        assertInstanceOf(CheckCommand.class, parser.parse("CHECK"));
        assertInstanceOf(QuitCommand.class, parser.parse("Quit"));
    }

    @Test
    void rejectsNumbersOutOfRange() {
        assertInstanceOf(MessageCommand.class, parser.parse("A3 0"));
        assertInstanceOf(MessageCommand.class, parser.parse("A3 10"));
    }

    @Test
    void rejectsBadCellReference() {
        assertInstanceOf(MessageCommand.class, parser.parse("Z9 4"));
    }

    @Test
    void emptyInputProducesSilentMessage() {
        MessageCommand command = assertInstanceOf(MessageCommand.class, parser.parse("   "));
        assertEquals("", command.text());
    }
}
