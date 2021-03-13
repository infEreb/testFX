package sample;

import Constructor.*;
import Engine.Constants;
import Engine.Graph;
import Engine.RigidBody2D;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;


public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        Game game = new Game();
        game.startGame(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
