package sample;

import Constructor.Body2D;
import Constructor.Sprite;
import Constructor.SpriteAnimation;
import Engine.Constants;
import Engine.RigidBody2D;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.*;
import javafx.stage.Stage;

import java.util.ArrayList;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Canvas canvas = new Canvas(1280, 720);
        Group group = new Group(canvas);
        Pane root = new Pane();

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

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
        // right boundary line
        //gc.fillRect(1280, 0, 1, 720);
        // bottom boundary line
        //gc.fillRect(0,720,1280,1);
        Body2D top_line = new Body2D(new Sprite(), new RigidBody2D(0,-53,1280,1));
        Body2D left_line = new Body2D(new Sprite(), new RigidBody2D(0,0,1,720));
        Body2D bottom_line = new Body2D(new Sprite(), new RigidBody2D(0,720,1280,1));
        Body2D right_line = new Body2D(new Sprite(), new RigidBody2D(1280,0,1,720));




        Sprite png_white_s = new Sprite(new ImageView("/sample/png-white.png"),
                new Point2D(400,30), new Point2D(0,0));
        Sprite png_black = new Sprite(new ImageView("/sample/png-black.png"),
                new Point2D(700,-100), new Point2D(0,0));
        Body2D png_white = new Body2D(png_white_s,
                new RigidBody2D(png_white_s.getPosition().getX(),
                        png_white_s.getPosition().getY(),png_white_s.getWidth(),png_white_s.getHeight()));

        //gc.drawImage(png_white_s.getTexture(), png_white_s.getPosition().getX(), png_white_s.getPosition().getY());


        final long tempNanoTime = System.nanoTime();
        Pacman pacman = new Pacman(png_white, new SpriteAnimation(new ArrayList<Sprite>(),2,1));
        root.getChildren().addAll(pacman);
        pacman.getAnimation().play();
        new AnimationTimer()
        {

            double time;
            @Override
            public void handle(long presentNanoTime) {
                time = (presentNanoTime - tempNanoTime) / 1e9;
                double posX = png_white.getPosition().getX();
                double posY = png_white.getRigidBody().getMinY();
                double maxX = canvas.getBoundsInLocal().getMaxX();
                double maxY = canvas.getBoundsInLocal().getMaxY();

                //Pacman pacman = new Pacman(png_white, new SpriteAnimation(new ArrayList<Sprite>(),2,200));
                //gc.drawImage(png_white.getTexture(), posX, posY);

                //if();
                //debug info
                System.out.println(time);
                System.out.println("PosX: " + posX + " -- ScrnPosX: " + maxX);
                System.out.println("PosY: " + posY + " -- ScrnPosY: " + maxY);
                System.out.println("Height: " + png_white.getRigidBody().getHeight() + " -- Width: " + png_white.getRigidBody().getWidth());
                System.out.println("Key: " + code.toString());

                if (code.toString().equals("W") && !pacman.getBody().intersects(top_line)) {
                    /*png_white.setVelocity(new Point2D(0, -1));
                    gc.clearRect(posX, posY, png_white.getSprite().getWidth(), png_white.getSprite().getHeight());
                    png_white.update(3);
                    png_white.render(gc, png_white.getSprite().getTexture().getImage());*/
                    pacman.move(new Point2D(0, -1), 3);

                } else if (code.toString().equals("S") && !png_white.intersects(bottom_line)) {
                    png_white.setVelocity(new Point2D(0, 1));
                    gc.clearRect(posX, posY, png_white.getSprite().getWidth(), png_white.getSprite().getHeight());
                    png_white.update(3);
                    png_white.render(gc, png_white.getSprite().getTexture().getImage());

                } else if (code.toString().equals("A") && !png_white.intersects(left_line)) {
                    png_white.setVelocity(new Point2D(-1, 0));
                    gc.clearRect(posX, posY, png_white.getSprite().getWidth(), png_white.getSprite().getHeight());
                    png_white.update(3);
                    png_white.render(gc, png_white.getSprite().getTexture().getImage());

                } else if (code.toString().equals("D") && !png_white.intersects(right_line)) {
                    png_white.setVelocity(new Point2D(1, 0));
                    gc.clearRect(posX, posY, png_white.getSprite().getWidth(), png_white.getSprite().getHeight());
                    png_white.update(3);
                    png_white.render(gc, png_white.getSprite().getTexture().getImage());
                }


            }
        }.start();

        primaryStage.show();
    }
    public void update()
    {

    }


    public static void main(String[] args) {
        launch(args);
    }
}
