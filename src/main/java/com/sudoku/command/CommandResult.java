package com.sudoku.command;

/**
 * The outcome of running a command: a message to show, whether the grid should
 * be reprinted afterwards, and whether the game has been won or should quit.
 */
public final class CommandResult {

    private final String message;
    private final boolean showBoard;
    private final boolean solved;
    private final boolean quit;

    private CommandResult(String message, boolean showBoard, boolean solved, boolean quit) {
        this.message = message;
        this.showBoard = showBoard;
        this.solved = solved;
        this.quit = quit;
    }

    /** A message followed by the updated grid. */
    public static CommandResult withBoard(String message) {
        return new CommandResult(message, true, false, false);
    }

    /** A message on its own, with no grid. */
    public static CommandResult messageOnly(String message) {
        return new CommandResult(message, false, false, false);
    }

    /** The winning move: show the grid and the success message. */
    public static CommandResult solved(String message) {
        return new CommandResult(message, true, true, false);
    }

    public static CommandResult quit(String message) {
        return new CommandResult(message, false, false, true);
    }

    public String message() {
        return message;
    }

    public boolean showBoard() {
        return showBoard;
    }

    public boolean solved() {
        return solved;
    }

    public boolean quit() {
        return quit;
    }
}
