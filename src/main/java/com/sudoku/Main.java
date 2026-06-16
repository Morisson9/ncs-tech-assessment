package com.sudoku;

import com.sudoku.io.Console;
import com.sudoku.io.SystemConsole;
import com.sudoku.puzzle.PuzzleGenerator;

public final class Main {

    public static void main(String[] args) {
        Console console = new SystemConsole();
        PuzzleGenerator generator = new PuzzleGenerator();
        new SudokuApplication(console, generator).run();
    }
}
