package sample;

import Constructor.Point2D;
import Constructor.Sprite;
import Constructor.Vector2D;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.*;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;

import java.util.ArrayList;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Group group = new Group();
        Scene scene = new Scene(group);
        primaryStage.setScene(scene);

        Canvas canvas = new Canvas(1920, 1080);
        group.getChildren().add(canvas);

        StringBuffer code = new StringBuffer();

        scene.setOnKeyPressed(
                new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent keyEvent) {
                        String keyCode = keyEvent.getCode().toString();
                        if(keyCode == "W" || keyCode == "S" || keyCode == "A" || keyCode == "D") {
                            code.delete(0, code.length());
                            code.append(keyCode);
                        }
                    }
                }
        );

        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.clearRect(0,0,512,512);

        gc.setFill(Color.BLUE);

        Sprite png_white = new Sprite(new Image("/sample/png-white.png"),
                new Point2D(400,30), new Vector2D(new Point2D(0,0)), 10, 10);
        Sprite png_black = new Sprite(new Image("/sample/png-black.png"),
                new Point2D(700,-100), new Vector2D(new Point2D(0,0)), 10, 10);


        final long tempNanoTime = System.nanoTime();
        new AnimationTimer()
        {
            double time;
            @Override
            public void handle(long presentNanoTime) {
                time = (presentNanoTime - tempNanoTime) / 1e9;
                System.out.println(time);
                gc.drawImage(png_white.getImage(), png_white.getPosition().getX(), png_white.getPosition().getY());

                if (code.toString().equals("W")) {
                    png_white.setVelocity(new Vector2D(new Point2D(0, -1)));
                    png_white.update(1);
                    gc.clearRect(0,0,1920,1080);
                    gc.drawImage(png_white.getImage(), png_white.getPosition().getX(), png_white.getPosition().getY());
                } else if (code.toString().equals("S")) {
                    png_white.setVelocity(new Vector2D(new Point2D(0, 1)));
                    png_white.update(1);
                    gc.clearRect(0,0,1920,1080);
                    gc.drawImage(png_white.getImage(), png_white.getPosition().getX(), png_white.getPosition().getY());

                } else if (code.toString().equals("A")) {
                    png_white.setVelocity(new Vector2D(new Point2D(-1, 0)));
                    png_white.update(1);
                    gc.clearRect(0,0,1920,1080);
                    gc.drawImage(png_white.getImage(), png_white.getPosition().getX(), png_white.getPosition().getY());

                } else if (code.toString().equals("D")) {
                    png_white.setVelocity(new Vector2D(new Point2D(1, 0)));
                    png_white.update(1);
                    gc.clearRect(0,0,1920,1080);
                    gc.drawImage(png_white.getImage(), png_white.getPosition().getX(), png_white.getPosition().getY());
                }


            }
        }.start();

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
