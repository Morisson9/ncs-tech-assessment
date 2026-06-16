# Sudoku (command line)

A small CLI Sudoku game in Java. It generates a puzzle with 30 given
numbers, let you fill in the rest, checks your moves, and tells you when you've
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

## Assumptions

- Input is line based. For "press any key to play again" that means pressing
  Enter; typing `quit` (or `q`/`exit`) there ends the session instead.
- A hint counts as a move and reprints the grid; `check` only prints its result.

## Tests

Tests live under `src/test/java` and cover the model, solver, generator,
validator, parser, renderer, and an end-to-end game played through a scripted
console. Run them with `mvn test`.
