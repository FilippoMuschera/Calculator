package com.calc.calculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("calc.fxml"));
        Scene scene = new Scene(fxmlLoader.load()); //carica il file .fxml della UI
        stage.setTitle("Calculator");
        stage.getIcons().add(new Image(String.valueOf(getClass().getResource("icon.png")))); //carica icona della UI
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

//SonarCloud Analysis: https://sonarcloud.io/summary/overall?id=FilippoMuschera_Calculator