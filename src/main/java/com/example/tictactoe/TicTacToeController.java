package com.example.tictactoe;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class TicTacToeController {
    @FXML
    private GridPane grid;
    @FXML
    private Text winner;
    private TicTacToeBot game = new TicTacToeBot(3, 3);
    private Boolean debounce = false;
    private final String player = "X";
    private final String bot = "O";

    private Button getCell(int row, int column) {
        for (Node node : grid.getChildren()) {
            if (grid.getRowIndex(node) == row && grid.getColumnIndex(node) == column) {
                return (Button) node;
            }
        }

        return null;
    }

    @FXML
    protected void onClick(ActionEvent event) {
        final Button button = (Button) event.getSource();
        final int row = grid.getRowIndex(button);
        final int column = grid.getColumnIndex(button);

        if (game.getCell(row, column) == ' ' && !debounce) {
            debounce = true;

            {
                button.setText(player);
                button.setStyle("-fx-text-fill: red;");
                game.setCell(row, column, player.charAt(0));
            }

            if (game.getWinner() == ' ' && !game.isFull()) {
                final Vector2 move = game.getBestMove(bot.charAt(0), player.charAt(0));
                final Button button2 = getCell(move.getX(), move.getY());
                button2.setText(bot);
                button2.setStyle("-fx-text-fill: blue;");
                game.setCell(move.getX(), move.getY(), bot.charAt(0));
            }

            if (game.getWinner() != ' ') {
                winner.setText(game.getWinner() + " wins!");
            } else if (game.isFull()) {
                winner.setText("Draw!");
            }

            debounce = false;
        }
    }

    @FXML
    protected void onReset() {
        winner.setText("");

        for (Node node : grid.getChildren()) {
            final Button button = (Button) node;
            button.setText("");
        }

        game = new TicTacToeBot(game.getRows(), game.getColumns());
    }

    public void initialize() {
        for (int x = 0; x < game.getColumns(); x++) {
            for (int y = 0; y < game.getRows(); y++) {
                final Button button = new Button();
                button.setOnAction(this::onClick);
                button.setPrefSize(1000, 1000);
                grid.add(button, x, y);
            }
        }
    }
}