# Sudoku (command line)

A small command-line Sudoku game in Java. It generates a puzzle with 30 given
numbers, lets you fill in the rest, checks your moves, and tells you when you've
solved it.

## Requirements

- JDK 17 or newer
- Maven 3.6 or newer

It's plain Java with no platform-specific code, so it runs the same on Windows,
macOS, and Linux.

## Build and run

Build and run the tests:

```
mvn test
```

Package a runnable jar and start the game:

```
mvn package
java -jar target/sudoku-cli.jar
```

If you'd rather not build a jar, you can compile and run straight from the
sources:

```
mvn compile
java -cp target/classes com.sudoku.Main
```

## How to play

The board uses letters A-I for rows and 1-9 for columns. Empty cells show as
`_`. At the prompt you can:

- `A3 4` — put 4 in row A, column 3
- `C5 clear` — empty a cell you filled in earlier
- `hint` — reveal one correct number
- `check` — look for duplicates in any row, column, or 3x3 box
- `quit` — leave the game

You can't overwrite a pre-filled cell, and numbers have to be between 1 and 9.
The game is won when every cell is filled and there are no rule violations; after
that it offers you a new puzzle.

## Design notes

The code is split into small packages by responsibility:

- `model` — `Board`, `Cell`, and `Position`, the core grid types.
- `solver` — a backtracking `SudokuSolver` used both to produce a finished grid
  and to count solutions.
- `puzzle` — `PuzzleGenerator` builds a solved grid and removes numbers while a
  unique solution survives; `Puzzle` keeps the givens and the full solution.
- `rules` — `RuleValidator` finds the first duplicate; `Violation` phrases it.
- `command` — each user action is a `Command` that runs against a `GameContext`
  and returns a `CommandResult`. `CommandParser` turns text into commands.
- `io` — a `Console` abstraction (`SystemConsole` for real input, plus a test
  double) and the `BoardRenderer`.
- `game` — `GameState`, the per-game loop in `SudokuGame`, and the session loop
  in `SudokuApplication`.

A few decisions worth calling out:

- **Generation guarantees a unique solution.** Cells are removed one at a time
  and a removal is kept only while the puzzle still has exactly one solution, so
  hints and the win check are always well defined.
- **Win detection** is "every cell filled and no violations" rather than
  matching the stored solution. A completely filled grid with no duplicates is by
  definition correct, so the solution is only needed for hints.
- **Hints** fill the first empty cell in reading order. That keeps the behaviour
  predictable and easy to test.
- **Validation order** is rows, then columns, then subgrids, and the first
  duplicate found is reported. The examples in the brief aren't internally
  consistent about which rule "wins" when a single move breaks more than one at
  once, so this is a deliberate, documented choice.

## Assumptions

- Input is line based. For "press any key to play again" that means pressing
  Enter; typing `quit` (or `q`/`exit`) there ends the session instead.
- A hint counts as a move and reprints the grid; `check` only prints its result.

## Tests

Tests live under `src/test/java` and cover the model, solver, generator,
validator, parser, renderer, and an end-to-end game played through a scripted
console. Run them with `mvn test`.
