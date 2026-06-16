package com.sudoku;

import com.sudoku.game.GameResult;
import com.sudoku.game.GameState;
import com.sudoku.game.SudokuGame;
import com.sudoku.io.Console;
import com.sudoku.puzzle.PuzzleGenerator;
import com.sudoku.rules.RuleValidator;

/**
 * SudokuApplication that will drive the session.
 */
public final class SudokuApplication {

    private static final String PLAY_AGAIN = "Press any key to play again...";

    private final Console console;
    private final PuzzleGenerator generator;
    private final RuleValidator validator;

    public SudokuApplication(Console console, PuzzleGenerator generator) {
        this.console = console;
        this.generator = generator;
        this.validator = new RuleValidator();
    }

    public void run() {
        console.println("Welcome to Sudoku!");
        console.println("");

        while (true) {
            GameState state = new GameState(generator.generate(), validator);
            GameResult result = new SudokuGame(state, console).play();
            if (result == GameResult.QUIT) {
                return;
            }
            console.println(PLAY_AGAIN);
            if (!wantsAnotherGame()) {
                return;
            }
            console.println("");
        }
    }

    private boolean wantsAnotherGame() {
        String response = console.readLine();
        if (response == null) {
            return false;
        }
        String trimmed = response.trim().toLowerCase();
        return !trimmed.equals("quit") && !trimmed.equals("exit") && !trimmed.equals("q");
    }
}
