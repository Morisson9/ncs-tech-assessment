package com.sudoku.io;

/**
 * Thin abstraction over reading and writing lines so the game loop can be
 * driven by the real terminal in production and by scripted input in tests.
 */
public interface Console {

    /** Reads a line of input, or returns null at end of input. */
    String readLine();

    void println(String text);
}
