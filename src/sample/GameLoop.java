package sample;

import Constructor.*;
import Engine.Constants;
import Engine.Graph;
import Engine.Node;
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

    long ghostsTimer = -Constants.GHOST_ESCAPE_TIME;
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
    ImageView victoryLabel;
    int pacmanActiveMove;
    boolean labelIsInstalled;
    double ghostsSpeed;
    double redGhostsSpeed;
    //int directionDiagonallyOposite;
    boolean blinkingInstalled;

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
        pacmanActiveMove = Constants.NONE;
        labelIsInstalled = false;
        blinkingInstalled = false;
        ghostsSpeed = Constants.GHOST_SPEED;
        redGhostsSpeed = Constants.GHOST_SPEED;

        //System.out.println(graphMap.toString());
    }

    @Override
    public void handle(long presentNanoTime) {
        useMouseMenuButton(stackPane, primaryStage);



        if(infoBar.getCurrentCountLives() > 0) {
            getReadyToPlay();
        }

        if(pacman.isPacmanWin() && !labelIsInstalled){
            pacmanWin();
        }
        else if((isReady || infoBar.getCurrentCountLives() == 0) && !labelIsInstalled) {

            if (infoBar.getCurrentCountLives() > 0) {

                if (!pacman.getIsDead()) {

                    if(pacman.getCanKillGhosts()) { // if pacman can kill ghosts
                        ghosts.forEach((ghostColor, ghost) -> {
                                if (pacman.isKilledByOne(ghost)) { // if pacman intersects ghost
                                    if (!ghost.isDead()) { // and if ghost isn't dead
                                        ghost.setIsDead(true); // set up isDead
                                        ghost.setIsMovingHome(true);
                                        pacman.countKilledGhostAndAddLabel(pacmanActiveMove);
                                        ghost.setAnimation(Game.getGhostsDeathAnimation(ghostColor - 1)); // set up death animation
                                    }
                                }
                                if (ghost.isMovingHome()) {
                                    if (ghost.getBody().getLogicalPosFromPixelPos().equals(new Point2D(12, 11))) {
                                        ghost.setAnimation(Game.getGhostsAnimation(Constants.intGhostToString(ghostColor)));
                                        ghost.setIsMovingHome(false);
                                    }
                                }
                        });
                    }
                    else if (pacman.isKilled(ghosts.values())) {
                        pacman.pacmanDeadAnimation(moveActions.get(Constants.PACMAN).getActiveMove());
                        return;
                    }
                    else{
                        ghosts.forEach( (color, ghost) ->{
                            if(ghost.isMovingHome()){
                                if (ghost.getBody().getLogicalPosFromPixelPos().equals(new Point2D(12, 11))) {
                                    ghost.setAnimation(Game.getGhostsAnimation(Constants.intGhostToString(color)));
                                    ghost.setIsMovingHome(false);
                                }
                            }
                        });
                    }

                    pacmanActiveMove = moveActions.get(Constants.PACMAN).move(pacman, Game.todoMove);
                    pacman.activeMoving(pacmanActiveMove, Constants.SPEED);

                    // fruit's handler
                    if (!fruitMoved) {
                        checkFruitCanGo(presentNanoTime);
                    }
                    if (!fruit.getIsEaten() && (fruitCanMove || fruitMoved)) {
                        pacman.checkIsEatenFruit(fruit, pacmanActiveMove);
                    }

                    // change ghost's states by eaten big_pillow

                    //eat big pillow
                    if(pacman.getBigPillowHasEaten()){
                            // animation changing
                        int i = 0;
                        for (Ghost ghost: ghosts.values()) {
                            if(!ghost.isDead())
                                ghost.setAnimation(Game.getGhostsEscapeAnimation(i));
                            i++;
                        }
                        ghostsTimer = presentNanoTime;

                        Sound.playSound("/src/res/audio/ghost-turn-to-blue.mp3");
                        pacman.setBigPillowHasEaten(false);
                        pacman.setCanKillGhosts(true);

                    }

                    // ghosts escaping pacman
                    if (presentNanoTime - ghostsTimer <= Constants.GHOST_ESCAPE_TIME) { // counting 8 seconds
                        //System.out.println((presentNanoTime - ghostsTimer) / 1000000000);
                        if(ghosts.get(Constants.RED).isDead()) {
                            ghosts.get(Constants.RED).setDirectionToMove(ghostDeathPath(Constants.RED));
                        }
                        else {
                            ghosts.get(Constants.RED).setDirectionToMove(ghostEscapePacman(Constants.RED));
                        }

                        //set blinking time
                        if(presentNanoTime - ghostsTimer >= Constants.GHOST_BLINKING_TIME && !blinkingInstalled){
                            int i = 0;
                            for (Ghost ghost: ghosts.values()) {
                                if(!ghost.isDead()) {
                                    ghost.setAnimation(Game.getGhostsBlinkingAnimation(i));
                                }
                                i++;
                            }
                            blinkingInstalled = true;
                        }


                    }
                    else { // ghost's moving with normal states

                        if(ghosts.get(Constants.RED).isMovingHome())
                            ghosts.get(Constants.RED).setDirectionToMove(ghostDeathPath(Constants.RED));
                        else
                            ghosts.get(Constants.RED).setDirectionToMove(ghostPursuitPacman(Constants.RED));

                        if(ghostsTimer != -Constants.GHOST_ESCAPE_TIME) {
                            blinkingInstalled = false;
                            pacman.setCanKillGhosts(false);
                            pacman.setCountEatenGhosts(0);
                            ghostsTimer = -Constants.GHOST_ESCAPE_TIME;

                            //return normal animation
                            ghosts.forEach((ghostColor, ghost) ->{
                                if(!ghost.isMovingHome())
                                    ghost.setAnimation(Game.getGhostsAnimation(Constants.intGhostToString(ghostColor)));
                                ghost.setIsDead(false);

                            });
                        }
                    }

                    moveGhostWithDirection(presentNanoTime);

                } else {
                    losingLife();
                }
            } else {
                gameOver(pacman, ghosts);
            }
        }
    }

    void pacmanWin(){
            victoryLabel = new ImageView("/res/victoryPacman.png");
            victoryLabel.setTranslateX(8*28+1);
            victoryLabel.setTranslateY(10*28);
            System.out.println("WIN");
            Game.root.getChildren().add(victoryLabel);
            labelIsInstalled = true;

    }

    void losingLife(){
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
        pacman.setIsDead(false);
        Game.todoMove = Constants.NONE;
        isReady = false;
        isReadyStarted = false;
    }

    void moveGhostWithDirection(long presentNanoTime){
        ghosts.forEach((ghostColor, ghost) ->{

            if(ghostColor == Constants.RED){
                ghost.activeMoving(moveActions.get(ghostColor).aiMove(ghost, ghost.getDirectionToMove()), Constants.GHOST_SPEED);

            }else{
                if (ghost.isDead() || ghost.isMovingHome()) {
                    if(moveActions.get(ghostColor).getIsStuck())
                        ghost.setDirectionToMove(ghostDeathPath(ghostColor));
                    ghost.activeMoving(moveActions.get(ghostColor).aiMoveFromRndm(ghost, ghost.getDirectionToMove()), Constants.GHOST_SPEED);
                } else {
                    ghost.activeMoving(moveActions.get(ghostColor).randomMove(ghost, presentNanoTime), Constants.GHOST_SPEED);
                }
            }
        });
    }

    void checkFruitCanGo(long presentNanoTime){
        elapsedNanoSeconds = presentNanoTime - lastUpdate;

        // 1 second = 1,000,000,000 (1 billion) nanoseconds
        secondsForFruitStart = (int) (elapsedNanoSeconds / 1_000_000_000);

        if (secondsForFruitStart % 15 == 0 && secondsForFruitStart > 0 && !fruitCanMove) {
            fruitCanMove = true;
            Game.root.getChildren().add(fruit);
        }

        if (fruitCanMove) {
            fruitMovesFromEdgeOfMap();
        }
    }

    void getReadyToPlay() {
        Runnable r = ()->{
            try {
                isReadyStarted = true;
                Sound.playSound("/src/res/audio/start-music.mp3");
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

    int ghostPursuitPacman(int ghost_color){
        Point2D ghostPos = ghosts.get(ghost_color).getBody().getLogicalPosFromPixelPos();
        Point2D pacPos = pacman.getBody().getLogicalPosFromPixelPos();

        //System.out.println(redPos);
        //System.out.println(pacPos);

        ArrayList<Point2D> points = null;
        //System.out.println("=============\n" + graphMap.toString() + "\n=============");
        //System.out.println("from_______ghostPursuitPacman");
        points = graphMap.nodeListToValueList(
                graphMap.breadthFirstSearching(
                        graphMap.getNode(ghostPos),
                        graphMap.getNode(pacPos)
                )
        );
        //System.out.println("GHOST POS - " + ghostPos);
        Point2D vecPoint = points.get(0).subtract(ghostPos);

        //System.out.println("Direct: " + Constants.stringDirection(direction));
        return MoveActions.vectorToDirection(vecPoint) != null ? MoveActions.vectorToDirection(vecPoint) : Constants.NONE;

    }
    int ghostEscapePacman(int ghost_color){
        Point2D ghostPos = ghosts.get(ghost_color).getBody().getLogicalPosFromPixelPos();
        Point2D pacPos = pacman.getBody().getLogicalPosFromPixelPos();

        //System.out.println(redPos);
        //System.out.println(pacPos);

        ArrayList<Point2D> points = null;

        int eatenPillowPos = pacman.getEatenPillowPos();
        //System.out.println("-----DIRECT - " + Constants.stringDirection(eatenPillowPos));
        //System.out.println("from_______ghostEscapePacman");
        //System.out.println("POSITION GHOST: " + ghostPos);
        Point2D destinationPoint;
        switch(eatenPillowPos)
        {
            case Constants.UP_LEFT: {
                //System.out.println("UP_LEFT");
                destinationPoint = new Point2D(20, 22);
                break;
            }
            case Constants.UP_RIGHT: {
                //System.out.println("UP_RIGHT");
                destinationPoint = new Point2D(4, 22);
                break;
            }
            case Constants.DOWN_LEFT: {
                //System.out.println("DOWN_LEFT");
                destinationPoint = new Point2D(20, 1);
                break;
            }
            case Constants.DOWN_RIGHT: {
                //System.out.println("DOWN_RIGHT");
                destinationPoint = new Point2D(4, 1);
                break;
            }

            default:
                destinationPoint = new Point2D(4, 1);
        }

        points = graphMap.nodeListToValueList(
                graphMap.breadthFirstSearching(
                        graphMap.getNode(ghostPos),
                        graphMap.getNode(destinationPoint)
                )
        );
        Point2D vecPoint = points.get(0).subtract(ghostPos);

        //System.out.println("Direct: " + Constants.stringDirection(direction));
        return MoveActions.vectorToDirection(vecPoint) != null ? MoveActions.vectorToDirection(vecPoint) : Constants.NONE;

    }
    int ghostDeathPath(int ghost_color) {
        Point2D ghostPos = ghosts.get(ghost_color).getBody().getLogicalPosFromPixelPos();
        Point2D spawn_point = new Point2D(12, 11);
        ArrayList<Point2D> points = null;
        //System.out.println("=============\n" + graphMap.toString() + "\n=============");
        //System.out.println("from_______ghostPursuitPacman");
        ArrayList<Node<Point2D>> path = graphMap.breadthFirstSearching(graphMap.getNode(ghostPos), graphMap.getNode(spawn_point));
        points = graphMap.nodeListToValueList(path);
        //System.out.println(graphMap.pathToString(path));
        //System.out.println("GHOST POS - " + ghostPos);
        Point2D vecPoint = points.get(0).subtract(ghostPos);

        //System.out.println(Constants.stringDirection(MoveActions.vectorToDirection(vecPoint)));

        //System.out.println("Direct: " + Constants.stringDirection(direction));
        return MoveActions.vectorToDirection(vecPoint) != null ? MoveActions.vectorToDirection(vecPoint) : Constants.NONE;
    }

    void fruitMovesFromEdgeOfMap(){


        Point2D fruitPos = fruit.getBody().getLogicalPosFromPixelPos();
        Point2D endPoint = new Point2D(11, 14);

        if(fruitPos.getX() == endPoint.getX() &&
                fruitPos.getY() == endPoint.getY()){
            fruitMoved = true;
            fruitCanMove = false;
            return;
        }

        //System.out.println(redPos);
        //System.out.println(pacPos);

        ArrayList<Point2D> points = null;
        points = graphMap.nodeListToValueList(
                graphMap.breadthFirstSearching(
                        graphMap.getNode(fruitPos),
                        graphMap.getNode(endPoint)
                )
        );

        Point2D vecPoint = points.get(0).subtract(fruitPos);
        int direction = MoveActions.vectorToDirection(vecPoint) != null ? MoveActions.vectorToDirection(vecPoint) : Constants.NONE;

        fruit.chooseMoveFruit(direction);

    }

    void gameOver(Pacman pacman, Map<Integer, Ghost> ghosts){
        Game.root.getChildren().removeAll(pacman);
        Game.root.getChildren().removeAll(ghosts.values());
        ImageView gameOverText = new ImageView("/res/gameover.png");
        System.out.println("LOSE");
        gameOverText.setTranslateX(9*28+18);
        gameOverText.setTranslateY(14*28+6);
        Game.root.getChildren().add(gameOverText);
        labelIsInstalled = true;
    }
    void useMouseMenuButton(StackPane stackPane, Stage primaryStage){
        infoBar.getMenuButton().setOnMouseClicked(event ->{
            Button resume = new Button("Resume");
            Button quit = new Button("Quit");
            Menu menu = new Menu(stackPane);
            menu.showMenu(resume, quit);
            resume.setOnMouseClicked(mouseEvent -> {
                menu.hideMenu();
                start();
            });
            quit.setOnMouseClicked(mouseEvent -> {
                primaryStage.close();
            });
            stop();
        });
    }

    }

