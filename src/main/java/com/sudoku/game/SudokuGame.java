package com.sudoku.game;

import com.sudoku.command.Command;
import com.sudoku.command.CommandParser;
import com.sudoku.command.CommandResult;
import com.sudoku.io.BoardRenderer;
import com.sudoku.io.Console;

/**
 * Plays a single puzzle from first prompt to win or quit. The application layer
 * is responsible for the welcome banner and for offering another game.
 */
public final class SudokuGame {

    private static final String PROMPT = "Enter command (e.g., A3 4, C5 clear, hint, check, quit):";
    private static final String SUCCESS = "You have successfully completed the Sudoku puzzle!";

    private final GameState state;
    private final Console console;
    private final CommandParser parser;
    private final BoardRenderer renderer;

    public SudokuGame(GameState state, Console console) {
        this(state, console, new CommandParser(), new BoardRenderer());
    }

    public SudokuGame(GameState state, Console console, CommandParser parser, BoardRenderer renderer) {
        this.state = state;
        this.console = console;
        this.parser = parser;
        this.renderer = renderer;
    }

    public GameResult play() {
        console.println("Here is your puzzle:");
        console.println(renderer.render(state.board()));

        while (true) {
            console.println("");
            console.println(PROMPT);
            String input = console.readLine();
            if (input == null) {
                return GameResult.QUIT;
            }

            Command command = parser.parse(input);
            CommandResult result = command.execute(state);

            console.println("");
            if (!result.message().isEmpty()) {
                console.println(result.message());
            }
            if (result.showBoard()) {
                console.println("");
                console.println("Current grid:");
                console.println(renderer.render(state.board()));
            }
            if (result.solved()) {
                console.println("");
                console.println(SUCCESS);
                return GameResult.WON;
            }
            if (result.quit()) {
                return GameResult.QUIT;
            }
        }
    }
}
