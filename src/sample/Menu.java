package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class Menu {

    private StackPane stackPane;
    private StackPane backgroundMenu;
    public Menu(StackPane stackPane){
        this.stackPane = stackPane;
        this.backgroundMenu = new StackPane();;
    }
    void hideMenu(){
        stackPane.getChildren().remove(backgroundMenu);
    }

    void showMenu(Button resume, Button quit){
        backgroundMenu.setPrefSize(28*28, 29*28);
        VBox menu = new VBox();
        Label pauseLabel = new Label("PAUSED");
        menu.getChildren().addAll(pauseLabel, resume, quit);
        resume.getStyleClass().add("menu-buttons");
        quit.getStyleClass().add("menu-buttons");
        VBox.setMargin(resume, new Insets(10, 0, 15, 0));
        backgroundMenu.setPadding(new Insets(0, 0, 0, 28*2));
        menu.setAlignment(Pos.CENTER);
        backgroundMenu.setAlignment(Pos.CENTER);
        backgroundMenu.setStyle("-fx-background-color: rgba(0, 0, 0, 0.8);");
        backgroundMenu.getChildren().add(menu);
        stackPane.getChildren().add(backgroundMenu);
    }
}

