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
import java.util.HashMap;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Group group = new Group();
        Pane root = new Pane();
        root.setPrefSize(1280, 720);

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

        Body2D top_line = new Body2D(new Sprite(), new RigidBody2D(0,0,1280,1));
        Body2D left_line = new Body2D(new Sprite(), new RigidBody2D(0,0,1,720));
        Body2D bottom_line = new Body2D(new Sprite(), new RigidBody2D(0,720,1280,1));
        Body2D right_line = new Body2D(new Sprite(), new RigidBody2D(1280,0,1,720));

        // creating sprites and add them to the sprite list
        Sprite p_stay = new Sprite(new ImageView("/sample/2.png"),
                new Point2D(0,0), new Point2D(0,0));
        Sprite p_up1 = new Sprite(new ImageView("/sample/1u.png"),
                new Point2D(0,0), new Point2D(0,0));
        Sprite p_up2 = new Sprite(new ImageView("/sample/0u.png"),
                new Point2D(0,0), new Point2D(0,0));
        ArrayList<Sprite> up = new ArrayList<Sprite>();
        up.add(p_stay);
        up.add(p_up1);
        up.add(p_up2);
        Sprite p_down1 = new Sprite(new ImageView("/sample/1d.png"),
                new Point2D(0,0), new Point2D(0,0));
        Sprite p_down2 = new Sprite(new ImageView("/sample/0d.png"),
                new Point2D(0,0), new Point2D(0,0));
        ArrayList<Sprite> down = new ArrayList<Sprite>();
        down.add(p_stay);
        down.add(p_down1);
        down.add(p_down2);
        Sprite p_left1 = new Sprite(new ImageView("/sample/1l.png"),
                new Point2D(0,0), new Point2D(0,0));
        Sprite p_left2 = new Sprite(new ImageView("/sample/0l.png"),
                new Point2D(0,0), new Point2D(0,0));
        ArrayList<Sprite> left = new ArrayList<Sprite>();
        left.add(p_stay);
        left.add(p_left1);
        left.add(p_left2);
        Sprite p_right1 = new Sprite(new ImageView("/sample/1r.png"),
                new Point2D(0,0), new Point2D(0,0));
        Sprite p_right2 = new Sprite(new ImageView("/sample/0r.png"),
                new Point2D(0,0), new Point2D(0,0));
        ArrayList<Sprite> right = new ArrayList<Sprite>();
        right.add(p_stay);
        right.add(p_right1);
        right.add(p_right2);

        // puts list into hashmap for animation
        HashMap<Integer, ArrayList<Sprite>> pacmanSprites = new HashMap<Integer, ArrayList<Sprite>>();
        pacmanSprites.put(Constants.UP, up);
        pacmanSprites.put(Constants.DOWN, down);
        pacmanSprites.put(Constants.LEFT, left);
        pacmanSprites.put(Constants.RIGHT, right);

        Body2D png_white = new Body2D(p_stay,
                new RigidBody2D(p_stay.getPosition().getX(),
                        p_stay.getPosition().getY(), p_stay.getWidth(), p_stay.getHeight()));

        final long tempNanoTime = System.nanoTime();

        Pacman pacman = new Pacman(png_white, new SpriteAnimation(pacmanSprites,2,1));
        root.getChildren().addAll(pacman);
        //pacman.getAnimation().play();

        new AnimationTimer()
        {

            double time;
            @Override
            public void handle(long presentNanoTime) {

                if (code.toString().equals("W") && !pacman.getBody().intersects(top_line)) {
                    pacman.getAnimation().setDiraction(Constants.UP);
                    pacman.getAnimation().play();
                    pacman.move(new Point2D(0, -1), 3);

                } else if (code.toString().equals("S") && !pacman.getBody().intersects(bottom_line)) {
                    pacman.getAnimation().setDiraction(Constants.DOWN);
                    pacman.getAnimation().play();
                    pacman.move(new Point2D(0, 1), 3);

                } else if (code.toString().equals("A") && !pacman.getBody().intersects(left_line)) {
                    pacman.getAnimation().setDiraction(Constants.LEFT);
                    pacman.getAnimation().play();
                    pacman.move(new Point2D(-1, 0), 3);

                } else if (code.toString().equals("D") && !pacman.getBody().intersects(right_line)) {
                    pacman.getAnimation().setDiraction(Constants.RIGHT);
                    pacman.getAnimation().play();
                    pacman.move(new Point2D(1, 0), 3);
                }


            }
        }.start();

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
