package com.sudoku.rules;

/**
 * Describes the broken Sudoku rule to player
 */
public final class Violation {

    public enum Type {
        ROW,
        COLUMN,
        SUBGRID
    }

    private final Type type;
    private final int number;
    private final String location;

    private Violation(Type type, int number, String location) {
        this.type = type;
        this.number = number;
        this.location = location;
    }

    public static Violation row(int number, char rowLabel) {
        return new Violation(Type.ROW, number, String.valueOf(rowLabel));
    }

    public static Violation column(int number, int columnLabel) {
        return new Violation(Type.COLUMN, number, String.valueOf(columnLabel));
    }

    public static Violation subgrid(int number) {
        return new Violation(Type.SUBGRID, number, null);
    }

    public Type type() {
        return type;
    }

    public String message() {
        return switch (type) {
            case ROW -> "Number " + number + " already exists in Row " + location + ".";
            case COLUMN -> "Number " + number + " already exists in Column " + location + ".";
            case SUBGRID -> "Number " + number + " already exists in the same 3x3 subgrid.";
            default -> throw new IllegalStateException("Unknown violation type: " + type);
        };
    }
}
