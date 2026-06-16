package com.sudoku.command;

import com.sudoku.model.Position;

import java.util.Optional;

/**
 * Turns a line of raw input into a {@link Command}. Keywords are matched
 * case-insensitively; anything that doesn't fit a known shape comes back as a
 * {@link MessageCommand} with a short explanation.
 */
public final class CommandParser {

    private static final String HELP =
            "Unrecognised command. Try something like A3 4, C5 clear, hint, check, or quit.";

    public Command parse(String input) {
        if (input == null) {
            return new QuitCommand();
        }
        String trimmed = input.trim();
        if (trimmed.isEmpty()) {
            return new MessageCommand("");
        }
        String[] tokens = trimmed.split("\\s+");
        if (tokens.length == 1) {
            return parseKeyword(tokens[0]);
        }
        if (tokens.length == 2) {
            return parseCellCommand(tokens[0], tokens[1]);
        }
        return new MessageCommand(HELP);
    }

    private Command parseKeyword(String word) {
        return switch (word.toLowerCase()) {
            case "hint" -> new HintCommand();
            case "check" -> new CheckCommand();
            case "quit" -> new QuitCommand();
            default -> new MessageCommand(HELP);
        };
    }

    private Command parseCellCommand(String cell, String argument) {
        Optional<Position> position = Position.tryParse(cell);
        if (position.isEmpty()) {
            return new MessageCommand("Invalid cell reference: " + cell + ".");
        }
        if (argument.equalsIgnoreCase("clear")) {
            return new ClearCommand(position.get());
        }
        return parsePlacement(position.get(), argument);
    }

    private Command parsePlacement(Position position, String argument) {
        int value;
        try {
            value = Integer.parseInt(argument);
        } catch (NumberFormatException e) {
            return new MessageCommand("Invalid command. Expected a number 1-9 or 'clear'.");
        }
        if (value < 1 || value > 9) {
            return new MessageCommand("Invalid move. The number must be between 1 and 9.");
        }
        return new PlaceCommand(position, value);
    }
}
