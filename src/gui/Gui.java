package gui;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

public class Gui extends Application {

    private Controller theController;
    private BorderPane root;
    private Popup descriptionPane;
    private Stage primaryStage;
    private Scene primaryScene;
    private TextArea primaryText;
    private ComboBox primaryDoors;

    @Override
    public void start(Stage givenStage) throws Exception {
        theController = new Controller(this);
        primaryStage = givenStage;
        root = setUpRoot();
        primaryScene = new Scene(root, 800, 675);
        root = adjustablePane(root);
        primaryScene.getStylesheets().add((new File("res/dungeon.css")).toURI().toString());
        primaryStage.setTitle("Dungeon Generator 4.0");
        primaryStage.setScene(primaryScene);
        primaryStage.show();
    }

    private BorderPane setUpRoot() {
        BorderPane temp = new BorderPane();
        Insets spacing = new Insets(10);
        HBox chamberView = new HBox(10);
        chamberView.setAlignment(Pos.CENTER);
        Node topMenu = setUpTopMenu();
        Node editButton = setUpEditButt();
        Node leftMenu = setUpLeftMenu();
        primaryText = setUpMainText();
        Node rightMenu = setUpAdditionalText();
        temp.setTop(topMenu);
        temp.setBottom(editButton);
        chamberView.getChildren().addAll(leftMenu, primaryText, rightMenu);
        temp.setCenter(chamberView);
        return temp;
    }

    private BorderPane adjustablePane(BorderPane givenPane) {
        givenPane.prefHeightProperty().bind(primaryScene.heightProperty());
        givenPane.prefWidthProperty().bind(primaryScene.widthProperty());
        return givenPane;
    }

    private Node setUpTopMenu() {
        Menu temp = new Menu("File");
        MenuItem saveItem = new MenuItem("Save File");
        MenuItem loadItem = new MenuItem("Load File");
        MenuBar tempBar = new MenuBar();

        saveItem.setOnAction((ActionEvent event) -> {
            theController.reactToFileSave();
        });
        loadItem.setOnAction((ActionEvent event) -> {
            theController.reactToFileLoad();
        });

        temp.getItems().add(saveItem);
        temp.getItems().add(loadItem);
        tempBar.getMenus().add(temp);

        return tempBar;
    }

    private Node setUpLeftMenu() {
        MenuBar tempBar = new MenuBar();
        Menu tempMenu = new Menu();
        ObservableList<String> observeList;
        ListView<String> viewList;
        ArrayList<String> tempList = new ArrayList<String>();
        int i;

        for (i = 0; i < theController.getMainLevel().getChambers().size(); i++) {
            String temp;
            temp = "Chamber " + (i + 1);
            tempList.add(temp);
        }
        for (i = 0; i < theController.getMainLevel().getPassages().size(); i++) {
            String temp;
            temp = "Passages " + (i + 1);
            tempList.add(temp);
        }

        observeList = FXCollections.<String>observableArrayList(tempList);
        viewList = new ListView<>(observeList);

        viewList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                primaryText.setText(theController.reactToDescripChange(t1));
                /*Change dropdown*/
                updateComboBox(t1);
            }
        });

        viewList.getStyleClass().addAll("selectMenu");

        return viewList;
    }

    private Node setUpEditButt() {
        Button temp = new Button();
        temp.setText("Edit");
        temp.setOnAction((ActionEvent event) -> {
           theController.reactToEditButton();
        });
        return temp;
    }

    private TextArea setUpMainText() {
        TextArea temp = new TextArea();
        temp.setText("Welcome to Dungeon Generator 4.0!\n" + "A level has already been generated for you\n"
                + "Please select a passage or chamber on the left tab to display it's description");
        temp.setEditable(false);
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

    private void updateComboBox() {

    }

    /**
     * Main method of gui class.
     * @param args arguments provided when program is initiated
     */
    public static void main(String[] args) {
        launch(args);
    }

}
