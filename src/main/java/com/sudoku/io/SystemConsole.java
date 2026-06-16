package com.sudoku.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;

/**
 * Console backed by standard input and output. UTF-8 is used explicitly so the
 * multiplication sign in the subgrid message renders correctly regardless of
 * the platform default.
 */
public final class SystemConsole implements Console {

    private final BufferedReader reader;
    private final PrintStream out;

    public SystemConsole() {
        this.reader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
        this.out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
    }

    @Override
    public String readLine() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public void println(String text) {
        out.println(text);
    }
}
