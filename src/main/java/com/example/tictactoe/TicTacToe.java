package com.example.tictactoe;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TicTacToe extends Application {
    private ChangeListener<? super Number> widthChangeListener;
    private ChangeListener<? super Number> heightChangeListener;

    @Override
    public void start(Stage stage) throws IOException {
        widthChangeListener = (observable, oldValue, newValue) -> {
            stage.heightProperty().removeListener(heightChangeListener);
            stage.setHeight(newValue.doubleValue());
            stage.heightProperty().addListener(heightChangeListener);
        };
        heightChangeListener = (observable, oldValue, newValue) -> {
            stage.widthProperty().removeListener(widthChangeListener);
            stage.setWidth(newValue.doubleValue());
            stage.widthProperty().addListener(widthChangeListener);
        };

        stage.widthProperty().addListener(widthChangeListener);
        stage.heightProperty().addListener(heightChangeListener);
        
        FXMLLoader fxmlLoader = new FXMLLoader(TicTacToe.class.getResource("tictactoe.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Tic-tac-toe");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}