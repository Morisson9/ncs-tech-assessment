package com.sudoku.command;

import com.sudoku.rules.Violation;

import java.util.Optional;

/** Reports the first rule violation on the board, or that there are none. */
public record CheckCommand() implements Command {

    @Override
    public CommandResult execute(GameContext context) {
        Optional<Violation> violation = context.findViolation();
        return violation.map(value -> CommandResult.messageOnly(value.message())).orElseGet(() ->
                CommandResult.messageOnly("No rule violations detected."));
    }
}
