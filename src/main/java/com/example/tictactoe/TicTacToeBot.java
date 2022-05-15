package com.example.tictactoe;

import java.util.*;

public class TicTacToeBot extends Game {
    TicTacToeBot(int columns, int rows) {
        super(columns, rows);
    }

    TicTacToeBot(char[][] board) {
        super(board);
    }

    public int getScore(char player) {
        return Collections.max(Arrays.asList(this.getHorizontalScore(player), this.getVerticalScore(player), this.getDiagonalScore(player)));
    }

    public Vector2 getBestMove(char player) {
        Map<Vector2, Integer> moves = new HashMap<>();

        for (Vector2 cell : this.getEmptyCells()) {
            moves.put(cell, this.getScoreAfterMove(player, cell));
        }

        List<Integer> scores = new ArrayList<>(moves.values());
        int maxScore = Collections.max(scores);

        for (Vector2 cell : moves.keySet()) {
            if (moves.get(cell) == maxScore) {
                return cell;
            }
        }

        return null;
    }

    public Vector2 getBestMove(char playerA, char playerB) {
        // playerA = us, playerB = enemy
        final Vector2 blockingMove = getBestMove(playerB);
        if (getScoreAfterMove(playerB, blockingMove) == Math.min(getColumns(), getRows()) - 1) {
            return blockingMove;
        }

        return getBestMove(playerA);
    }

    public int getScoreAfterMove(char player, Vector2 move) {
        TicTacToeBot clone = new TicTacToeBot(board);
        clone.setCell(move.getX(), move.getY(), player);
        return clone.getScore(player);
    }

    public ArrayList<Vector2> getEmptyCells() {
        ArrayList<Vector2> emptyCells = new ArrayList<>();

        for (int row = 0; row < this.getRows(); row++) {
            for (int column = 0; column < this.getColumns(); column++) {
                if (this.getCell(column, row) == ' ') {
                    emptyCells.add(new Vector2(row, column));
                }
            }
        }

        return emptyCells;
    }

    private int max(int[] scores) {
        int max = 0;
        for (int score : scores) {
            if (score > max) {
                max = score;
            }
        }

        return max;
    }

    private int getHorizontalScore(char player) {
        int[] scores = new int[this.getRows()];

        for (int row = 0; row < this.getRows(); row++) {
            for (int column = 0; column < this.getColumns(); column++) {
                if (this.getCell(column, row) == player) {
                    scores[row]++;
                }
            }
        }

        return max(scores);
    }

    private int getVerticalScore(char player) {
        int[] scores = new int[this.getColumns()];

        for (int column = 0; column < this.getColumns(); column++) {
            for (int row = 0; row < this.getRows(); row++) {
                if (this.getCell(column, row) == player) {
                    scores[column]++;
                }
            }
        }

        return max(scores);
    }

    private int getDiagonalScore(char player) {
        int[] scores = new int[2];

        if (getColumns() != getRows()) {
            return 0;
        }

        if (getCell(0, 0) == player) {
            for (int row = 1; row < getRows(); row++) {
                if (getCell(row, row) == player) {
                    scores[0] = row + 1;
                }
            }
        }
        if (getCell(0, getRows() - 1) == player) {
            for (int row = 1; row < getRows(); row++) {
                if (getCell(row, getRows() - row - 1) == player) {
                    scores[1] = row + 1;
                }
            }
        }

        return max(scores);
    }
}
