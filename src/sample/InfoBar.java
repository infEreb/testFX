package sample;

import Constructor.GridMap;
import Engine.Constants;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;

public class InfoBar {

    private Integer maxScore;
    private int maxLives;
    private int currentCountLives;
    private Integer countFruit;
    private HBox pacmanLives;
    private Button menuButton;
    private static Label currentScoreLabel;
    private Label maxScoreLabel;
    private Label countFruitLabel;
    StackPane stackPane;
    private ArrayList<ImageView> listPacmanLives;
    Scene scene;
    BorderPane mainPane;
    Stage primaryStage;

    public InfoBar(Stage primaryStage){
        countMaxScore();
        maxLives = 4;
        countFruit = 1;
        currentCountLives = maxLives;
        stackPane = new StackPane();
        listPacmanLives = new ArrayList<>();
        this.primaryStage = primaryStage;
    }
    void doSettingForInfoBars(){
        mainPane = this.createInfoBars();
        stackPane.setLayoutX(-28*2);
        stackPane.getChildren().add(mainPane);
        //28
        scene = new Scene(stackPane, 21*28, 29*28);


        String stylesheet = getClass().getResource("/CSS/GameLabelCSS.css").toExternalForm();
        scene.getStylesheets().add(stylesheet);
        scene.setFill(Color.BLACK);
    }
    /*
    * Vulnerable Ghosts:
        #1 in succession - 200 points.
        #2 in succession - 400 points.
        #3 in succession - 800 points.
        #4 in succession - 1600 points.
    */

    void countMaxScore(){
        maxScore = GridMap.listOfPillows.size() * Constants.SCORE_FOR_PILLOW;
        maxScore += GridMap.listOfBigPillows.size() * Constants.SCORE_FOR_POWER_PELLET;
        maxScore += Constants.SCORE_FOR_CHERRY;
        maxScore += Constants.SCORE_FOR_GHOST * 30;
    }

    public int getCurrentCountLives() {
        return currentCountLives;
    }

    public static Label getCurrentScoreLabel() {
        return currentScoreLabel;
    }

    public Button getMenuButton() {
        return menuButton;
    }

    public void countLiveReduce(){
        currentCountLives--;
    }
    public void removeLiveInLabel(){
        pacmanLives.getChildren().remove(listPacmanLives.get(currentCountLives - 1));
        listPacmanLives.remove(currentCountLives - 1);
    }

    public Stage getPrimaryStage(){ return primaryStage; }
    public Scene getScene(){
        return scene;
    }
    public StackPane getStackPane(){
        return stackPane;
    }
    public BorderPane createInfoBars(){

        //Score and level-----
        Label oneUPLabel = new Label("1 UP");

        currentScoreLabel = new Label("0");

        VBox currentScoreAndLevel = new VBox(oneUPLabel, currentScoreLabel);
        currentScoreAndLevel.setAlignment(Pos.BASELINE_RIGHT);
        ///-------------

        //High score--------
        Label maxScoreText = new Label("HIGH SCORE");

        maxScoreLabel = new Label(maxScore.toString());

        VBox maxScoreTextAndDigit = new VBox(maxScoreText, maxScoreLabel);
        maxScoreTextAndDigit.setAlignment(Pos.BASELINE_CENTER);

        //-----------------

        //Menu button-----
        menuButton = new Button("MENU");

        //----------------

        //Upper main bar---------------
        HBox scoreBar = new HBox(currentScoreAndLevel, maxScoreTextAndDigit, menuButton);
        scoreBar.setMaxWidth(19*28);

        HBox.setMargin(currentScoreAndLevel, new Insets(20, 0, 20, 0));
        HBox.setMargin(maxScoreTextAndDigit, new Insets(20, 0, 20, 20));
        HBox.setMargin(menuButton, new Insets(20, 0, 20, 20));

        scoreBar.setAlignment(Pos.TOP_CENTER);
        //------------------------------

        //Part bottom bar
        //Pacman lives ------------

        pacmanLives = new HBox();
        pacmanLives.setAlignment(Pos.BASELINE_LEFT);

        for(int i = 0; i < maxLives; i++){
            listPacmanLives.add(new ImageView("/res/life.png"));
        }
        pacmanLives.getChildren().addAll(listPacmanLives);
        //--------------------------
        //Fruit count----------
        countFruitLabel = new Label(countFruit.toString());

        HBox fruitsCount = new HBox(new ImageView("/res/Fruit/cherry.png"), countFruitLabel);
        fruitsCount.setAlignment(Pos.BASELINE_RIGHT);
        //---------------------

        //Bottom bar---------

        HBox bottomBox = new HBox();
        Region r = new Region();
        HBox.setHgrow(r, Priority.ALWAYS);
        bottomBox.getChildren().addAll(pacmanLives, r, fruitsCount);
        bottomBox.setMaxWidth(17*28);
        bottomBox.setAlignment(Pos.BOTTOM_CENTER);
        //--------------------
        BorderPane mainPane = new BorderPane();
        mainPane.setTop(scoreBar);
        mainPane.setCenter(Game.root);
        mainPane.setBottom(bottomBox);
        BorderPane.setAlignment(scoreBar, Pos.CENTER);
        BorderPane.setAlignment(bottomBox, Pos.CENTER);
        BorderPane.setMargin(scoreBar, new Insets(0, 0, 0, 50));
        BorderPane.setMargin(bottomBox, new Insets(0, 10, 5, 60));
//        mainPane.setLayoutX(-28*2);
        mainPane.setBackground(new Background(new BackgroundFill(Color.BLACK,
                CornerRadii.EMPTY,
                Insets.EMPTY)));
        return mainPane;
    }
}
