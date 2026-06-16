package com.sudoku.testutil;

import com.sudoku.io.Console;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

/**
 * A console for tests: it replays a fixed list of input lines and records every
 * line written so assertions can inspect the output. Once the scripted input is
 * exhausted, {@link #readLine()} returns null to mimic end of input.
 */
public final class RecordingConsole implements Console {

    private final Deque<String> inputs;
    private final StringBuilder output = new StringBuilder();

    public RecordingConsole(List<String> scriptedInputs) {
        this.inputs = new ArrayDeque<>(scriptedInputs);
    }

    @Override
    public String readLine() {
        return inputs.poll();
    }

    @Override
    public void println(String text) {
        output.append(text).append('\n');
    }

    public String output() {
        return output.toString();
    }
}
