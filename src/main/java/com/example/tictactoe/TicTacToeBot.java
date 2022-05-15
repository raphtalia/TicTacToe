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
        final int numRows = this.getRows();
        final int numColumns = this.getColumns();

        // Claim the middle cell if available.
        if (numRows % 2 != 0 && numColumns % 2 != 0) {
            final Vector2 mid = new Vector2(numRows / 2, numColumns / 2);
            if (this.getCell(mid.getX(), mid.getY()) == ' ') {
                return mid;
            }
        }

        final int victoryScore = Math.min(numRows, numColumns);
        final Vector2 blockingMove = getBestMove(playerB);
        final Vector2 nonBlockingMove = getBestMove(playerA);

        if (getScoreAfterMove(playerB, blockingMove) == victoryScore && getScoreAfterMove(playerA, nonBlockingMove) < victoryScore) {
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
                if (this.getCell(row, column) == ' ') {
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
                final char owner = this.getCell(row, column);

                if (owner == player) {
                    scores[row]++;
                } else if (owner != ' ') {
                    scores[row] = 0;
                    break;
                }
            }
        }

        return max(scores);
    }

    private int getVerticalScore(char player) {
        int[] scores = new int[this.getColumns()];

        for (int column = 0; column < this.getColumns(); column++) {
            for (int row = 0; row < this.getRows(); row++) {
                final char owner = this.getCell(row, column);

                if (owner == player) {
                    scores[column]++;
                } else if (owner != ' ') {
                    scores[column] = 0;
                    break;
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
                final char owner = this.getCell(row, row);

                if (owner == player) {
                    scores[0] = row + 1;
                } else if (owner != ' ') {
                    scores[0] = 0;
                    break;
                }
            }
        }
        if (getCell(0, getRows() - 1) == player) {
            for (int row = 1; row < getRows(); row++) {
                final char owner = this.getCell(row, getRows() - row - 1);

                if (owner == player) {
                    scores[1] = row + 1;
                } else if (owner != ' ') {
                    scores[1] = 0;
                    break;
                }
            }
        }

        return max(scores);
    }
}
