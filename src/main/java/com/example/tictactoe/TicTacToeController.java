package com.example.tictactoe;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class TicTacToeController {
    @FXML
    private GridPane grid;
    private TicTacToeBot game = new TicTacToeBot(3, 3);
    private Boolean debounce = false;
    private  int turn = 0;

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

        if (button.getText().isEmpty() && !debounce) {
            debounce = true;

//            final String player = "X";
//            final String bot = "O";

            {
                final String player = (turn % 2 == 0) ? "X" : "O";
                button.setText(player);
                if (player.equals("X")) {
                    button.setStyle("-fx-text-fill: red;");
                } else {
                    button.setStyle("-fx-text-fill: blue;");
                }
                System.out.println(grid.getColumnIndex(button) + " " + grid.getRowIndex(button));
                game.setCell(grid.getColumnIndex(button), grid.getRowIndex(button), player.charAt(0));
                turn++;
            }

//            if (game.getWinner() == ' ') {
//                // final String text = (turn % 2 == 0) ? "X" : "O";
//                final Vector2 move = game.getBestMove(player.charAt(0), bot.charAt(0));
//                System.out.println(move);
//                final Button button2 = getCell(move.getX(), move.getY());
//                button2.setText(bot);
//                button2.setStyle("-fx-text-fill: blue;");
//                game.setCell(move.getX(), move.getY(), bot.charAt(0));
//                // turn++;
//            }

            if (game.getWinner() != ' ') {
                System.out.println("Winner: " + game.getWinner());
                System.out.println("Game over!");
            }

            debounce = false;
        }
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