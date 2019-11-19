package gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.File;

public class Gui extends Application {

    private Controller theController;
    private BorderPane root;
    private Popup descriptionPane;
    private Stage primaryStage;
    private Scene primaryScene;

    @Override
    public void start(Stage givenStage) throws Exception {
        theController = new Controller(this);
        primaryStage = givenStage;
        root = setUpRoot();
        primaryScene = new Scene(root, 500, 475);
        primaryScene.getStylesheets().add((new File("res/dungeon.css")).toURI().toString());
        primaryStage.setTitle("Dungeon Generator 4.0");
        primaryStage.setScene(primaryScene);
        primaryStage.show();
    }

    private BorderPane setUpRoot() {
        BorderPane temp = new BorderPane();
        Node topMenu = setUpTopMenu();
        Node editButton = setUpEditButt();
        Node leftMenu = setUpLeftMenu();
        Node centerDescrip = setUpMainText();
        Node rightMenu = setUpAdditionalText();
        temp.setTop(topMenu);
        temp.setBottom(editButton);
        temp.setLeft(leftMenu);
        temp.setRight(rightMenu);
        temp.setCenter(centerDescrip);
        return temp;
    }

    private Node setUpTopMenu() {
        VBox tempBox = new VBox();
        Menu temp = new Menu("File");
        MenuItem saveItem = new MenuItem("Save File");
        MenuItem loadItem = new MenuItem("Load File");
        MenuBar tempBar = new MenuBar();

        saveItem.setOnAction((ActionEvent event) -> {
            theController.reactToButton();
        });
        loadItem.setOnAction((ActionEvent event) -> {
            theController.reactToButton();
        });

        temp.getItems().add(saveItem);
        temp.getItems().add(loadItem);
        tempBar.getMenus().add(temp);

        return tempBar;
    }

    private Node setUpLeftMenu() {
        VBox tempBox = new VBox();
        MenuBar tempBar = new MenuBar();
        Menu tempMenu = new Menu();
        ListView<MenuItem> tempList = new ListView<MenuItem>();
        int i;

        for (i = 0; i < theController.getMainLevel().getChambers().size(); i++) {
            MenuItem temp = new MenuItem();
            temp.setText("Chamber " + (i + 1));
            temp.setOnAction((ActionEvent event) -> {
               theController.reactToButton();
            });
            tempList.getItems().add(temp);
        }
        for (i = 0; i < theController.getMainLevel().getPassages().size(); i++) {
            MenuItem temp = new MenuItem();
            temp.setText("Passage " + (i + 1));
            temp.setOnAction((ActionEvent event) -> {
               theController.reactToButton();
            });
            tempList.getItems().add(temp);
        }

        return tempList;
    }

    private Node setUpEditButt() {
        Button temp = new Button();
        temp.setText("Edit");
        temp.setOnAction((ActionEvent event) -> {
           theController.reactToButton();
        });
        return temp;
    }

    private Node setUpMainText() {
        TextArea temp = new TextArea();
        temp.setText("Blank");
        temp.setEditable(true);
        return temp;
    }

    private Node setUpAdditionalText() {
        TilePane tempPane = new TilePane();
        Label boxName = new Label("List of doors");
        String allDoors;
        int i;

        ComboBox temp = new ComboBox();
        tempPane = new TilePane(temp);

        return tempPane;
    }

    /**
     * Main method of gui class.
     * @param args arguments provided when program is initiated
     */
    public static void main(String[] args) {
        launch(args);
    }

}
