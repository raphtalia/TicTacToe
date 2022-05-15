package com.example.tictactoe;

public class Vector2 {
    private final int x;
    private final int y;

    public Vector2(int inputX, int inputY) {
        x = inputX;
        y = inputY;
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public float magnitude() {
        return (float) Math.sqrt(x * x + y * y);
    }

    public Vector2 add(Vector2 other) {
        return new Vector2(x + other.x, y + other.y);
    }

    public Vector2 subtract(Vector2 other) {
        return new Vector2(x - other.x, y - other.y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}