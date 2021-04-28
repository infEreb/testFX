package sample;

import Constructor.*;
import Engine.Constants;
import Engine.Graph;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Exchanger;

import static java.lang.Thread.sleep;

public class GameLoop extends AnimationTimer {

    Graph<Point2D> graphMap = LevelData.createGraphMap(1);
    Pacman pacman;
    Map<Integer, Ghost> ghosts;
    Map<Integer, MoveActions> moveActions;
    StackPane stackPane;
    Stage primaryStage;
    long lastUpdate = System.nanoTime();
    boolean fruitMoved;
    boolean fruitCanMove;
    int stepFruit;
    ArrayList<Integer> fruitSteps;
    Fruit fruit;
    InfoBar infoBar;
    long elapsedNanoSeconds;
    int secondsForFruitStart;
    boolean isReady;
    boolean isReadyStarted;
    ImageView gameReadyLabel;

    public GameLoop(Pacman pacman, Map<Integer, Ghost> ghosts, Map<Integer, MoveActions> moveActions,
                    InfoBar infoBar){
        this.pacman = pacman;
        this.ghosts = ghosts;
        this.moveActions = moveActions;
        this.stackPane = infoBar.getStackPane();
        this.primaryStage = infoBar.getPrimaryStage();
        fruitMoved = false;
        fruitCanMove = false;
        stepFruit = 0;
        fruit = new Fruit(24, 11, new Image("/res/Fruit/cherry.png"), 0);
        fruitSteps = new ArrayList<>();
        this.infoBar = infoBar;
        isReady = false;
        isReadyStarted = false;
        createFruitSteps(fruitSteps);
    }


    @Override
    public void handle(long presentNanoTime) {
        useMouseMenuButton(stackPane, primaryStage);

        if(infoBar.getCurrentCountLives() > 0) {
            getReadyToPlay();
        }


        if(isReady || infoBar.getCurrentCountLives() == 0) {
            if (infoBar.getCurrentCountLives() > 0) {
                if (!pacman.isDead) {
                    if (pacman.isKilled(ghosts.values())) {
                        pacman.pacmanDeadAnimation(moveActions.get(Constants.PACMAN).getActiveMove());
                        return;
                    }

                    pacman.activeMoving(moveActions.get(Constants.PACMAN).move(pacman, Game.todoMove));

                    //System.out.println((int)elapsedSeconds);

                    elapsedNanoSeconds = presentNanoTime - lastUpdate;

                    // 1 second = 1,000,000,000 (1 billion) nanoseconds
                    secondsForFruitStart = (int) (elapsedNanoSeconds / 1_000_000_000);

                    if (secondsForFruitStart % 5 == 0 && secondsForFruitStart > 0 && !fruitCanMove && !fruitMoved) {
                        fruitCanMove = true;
                        Game.root.getChildren().add(fruit);
                    }

                    if (fruitCanMove) {
                        functionForFruit();
                    }
                    Point2D redPos = ghosts.get(Constants.RED).getBody().getLogicalPosFromPixelPos();
                    Point2D pacPos = pacman.getBody().getLogicalPosFromPixelPos();

                    //System.out.println(redPos);
                    //System.out.println(pacPos);

                    ArrayList<Point2D> points = null;
                    points = graphMap.nodeListToValueList(
                            graphMap.breadthFirstSearching(
                                    graphMap.getNode(redPos),
                                    graphMap.getNode(pacPos)
                            )
                    );
                    Point2D vecPoint = points.get(0).subtract(redPos);
                    int direction = MoveActions.vectorToDirection(vecPoint) != null ? MoveActions.vectorToDirection(vecPoint) : Constants.NONE;

                    //System.out.println("Direct: " + Constants.stringDirection(direction));


                    ghosts.get(Constants.RED).activeMoving(moveActions.get(Constants.RED)
                            .aiMove(ghosts.get(Constants.RED), direction));


                    ghosts.get(Constants.PINK).activeMoving(moveActions.get(Constants.PINK).randomMove(ghosts.get(Constants.PINK), presentNanoTime));
                    ghosts.get(Constants.YELLOW).activeMoving(moveActions.get(Constants.YELLOW).randomMove(ghosts.get(Constants.YELLOW), presentNanoTime));
                    ghosts.get(Constants.BLUE).activeMoving(moveActions.get(Constants.BLUE).randomMove(ghosts.get(Constants.BLUE), presentNanoTime));


                } else {
                    pacman.createPacmanDeathAnimation();

                    moveActions.forEach((number, moveAction) -> {
                        moveAction.startedMovementCondition();
                    });
                    pacman.setAnimationDeathStarted(false);
                    pacman.setStartedPositionAfterPacmanDeath(12 * 28, 14 * 28);
                    ghosts.forEach((number, ghost) -> {
                        ghost.setStartedPositionAfterPacmanDeath(12 * 28, 11 * 28);
                    });


                    infoBar.removeLiveInLabel();
                    infoBar.countLiveReduce();
                    pacman.isDead = false;
                    Game.todoMove = Constants.NONE;
                    isReady = false;
                    isReadyStarted = false;
                }
            } else {
                gameOver(pacman, ghosts);
            }
        }
    }

    void getReadyToPlay() {
        Runnable r = ()->{
            try {
                isReadyStarted = true;
                Sound soundReady = new Sound("/src/res/audio/start-music.mp3");
                soundReady.playSound();
                //stop();
                sleep(4000);
                //start();
                isReady = true;
                //in javaFX only the FX thread can modify the ui elements
                Platform.runLater(new Runnable(){
                    @Override
                    public void run() {
                        removeReadyLabel();
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        if(!isReady && !isReadyStarted) {
            addReadyLabel();
            Thread readyThread = new Thread(r, "ReadyThread");
            readyThread.start();
        }
    }

    void addReadyLabel(){
        gameReadyLabel = new ImageView("/res/ready.png");
        gameReadyLabel.setTranslateX(9*28+53);
        gameReadyLabel.setTranslateY(14*28);
        Game.root.getChildren().add(gameReadyLabel);
    }
    void removeReadyLabel(){
        Game.root.getChildren().remove(gameReadyLabel);
    }
    void functionForFruit(){
        System.out.println("FruitCanMove");
        DoStepsFruit(fruit, fruitSteps);
        if(stepFruit == 16){
            fruitMoved = true;
            fruitCanMove = false;
        }
    }
    void createFruitSteps(ArrayList<Integer> fruitSteps){
        int stepsLeftFirst = 9, stepsDown = 3, stepsLeftSecond = 4;
        for(int step = 0; step < 16; step++){
            if(stepsLeftFirst > 0) {
                fruitSteps.add(Constants.LEFT);
                stepsLeftFirst--;
            }else if(stepsDown > 0){
                fruitSteps.add(Constants.DOWN);
                stepsDown--;
            }else if(stepsLeftSecond > 0){
                fruitSteps.add(Constants.LEFT);
                stepsLeftSecond--;
            }
        }
        /*r = () -> {
            functionForFruit();
        };
        fruitThread = new Thread(r, "Fruit");*/
    }
    void DoStepsFruit(Fruit fruit, ArrayList<Integer> fruitSteps){
        if(stepFruit < 16) {
            System.out.println("Step fruit " + stepFruit + " MOVE " + fruitSteps.get(stepFruit));
            fruit.chooseMoveFruit(fruitSteps.get(stepFruit));
            if(fruit.getIsStuck()) {
                stepFruit++;
            }
        }
    }

    void gameOver(Pacman pacman, Map<Integer, Ghost> ghosts){
        Game.root.getChildren().removeAll(pacman);
        Game.root.getChildren().removeAll(ghosts.values());
        ImageView gameOverText = new ImageView("/res/gameover.png");
        gameOverText.setTranslateX(9*28+18);
        gameOverText.setTranslateY(14*28+6);
        Game.root.getChildren().add(gameOverText);
    }
    void useMouseMenuButton(StackPane stackPane, Stage primaryStage){
        infoBar.getMenuButton().setOnMouseClicked(event ->{
            Button resume = new Button("Resume");
            Button quit = new Button("Quit");
            StackPane backgroundMenu = new StackPane();
            Menu menu = new Menu(stackPane, backgroundMenu);
            menu.showMenu(resume, quit);
            resume.setOnMouseClicked(mouseEvent -> {
                stackPane.getChildren().remove(backgroundMenu);
                start();
            });
            quit.setOnMouseClicked(mouseEvent -> {
                primaryStage.close();
            });
            stop();
        });
    }

    }

