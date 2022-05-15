package com.example.tictactoe;

import java.util.Arrays;
import java.util.Collections;

public class Game {
    protected final char[][] board;

    Game(int columns, int rows) {
        this.board = new char[rows][columns];
        for (int row = 0; row < this.getRows(); row++) {
            for (int column = 0; column < this.getColumns(); column++) {
                board[row][column] = ' ';
            }
        }
    }

    Game(char[][] board) {
        this.board = new char[board.length][];
        for (int i = 0; i < board.length; i++) {
            this.board[i] = board[i].clone();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                sb.append(board[i][j]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public int getColumns() {
        return this.board[0].length;
    }

    public int getRows() {
        return this.board.length;
    }

    public char getCell(int row, int column) {
        return this.board[row][column];
    }

    public void setCell(int row, int column, char player) {
        if (column < 0 || column >= this.getColumns() || row < 0 || row >= this.getRows()) {
            throw new IllegalArgumentException("Invalid cell: " + row + "," + column);
        }
        if (this.getCell(row, column) != ' ') {
            throw new IllegalArgumentException("Cell is not empty: " + row + "," + column);
        }
        if (player != 'X' && player != 'O') {
            throw new IllegalArgumentException("Invalid player: " + player);
        }

        this.board[row][column] = player;
    }

    public boolean isFull() {
        for (int row = 0; row < this.getRows(); row++) {
            for (int column = 0; column < this.getColumns(); column++) {
                if (this.getCell(row, column) == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isWinner(char player) {
        return this.isWinnerHorizontal(player) || this.isWinnerVertical(player) || this.isWinnerDiagonal(player);
    }

    public char getWinner() {
        if (this.isWinner('X')) {
            return 'X';
        } else if (this.isWinner('O')) {
            return 'O';
        }

        return ' ';
    }

    public boolean isDraw() {
        if (isFull() && getWinner() == ' ') {
            return true;
        }

        return false;
    }

    private boolean isWinnerHorizontal(char player) {
        for (int row = 0; row < this.getRows(); row++) {
            for (int column = 0; column < this.getColumns(); column++) {
                if (this.getCell(column, row) != player) {
                    break;
                } else if (column == this.getColumns() - 1) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isWinnerVertical(char player) {
        for (int column = 0; column < this.getColumns(); column++) {
            for (int row = 0; row < this.getRows(); row++) {
                if (this.getCell(column, row) != player) {
                    break;
                } else if (row == this.getRows() - 1) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isWinnerDiagonal(char player) {
        if (getColumns() != getRows()) {
            return false;
        }

        if (getCell(0, 0) == player) {
            for (int row = 1; row < getRows(); row++) {
                if (getCell(row, row) != player) {
                    break;
                } else if (row == getRows() - 1) {
                    return true;
                }
            }
        }
        if (getCell(0, getRows() - 1) == player) {
            for (int row = 1; row < getRows(); row++) {
                if (getCell(row, getRows() - row - 1) != player) {
                    break;
                } else if (row == getRows() - 1) {
                    return true;
                }
            }
        }

        return false;
    }
}
