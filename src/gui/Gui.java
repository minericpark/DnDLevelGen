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
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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
    private ComboBox<String> primaryDoors;
    private Button editButton;
    private String currentSpace;

    /**
     * Initializes all Gui instances.
     * @param givenStage main stage to initialize
     * @throws Exception exception to throw
     */
    @Override
    public void start(Stage givenStage) throws Exception {
        theController = new Controller(this);
        primaryStage = givenStage;
        root = setUpRoot();
        primaryScene = new Scene(root, 800, 675);
        root = adjustablePane(root);
        descriptionPane = new Popup();
        primaryScene.getStylesheets().add((new File("res/dungeon.css")).toURI().toString());
        primaryStage.setTitle("Dungeon Generator 4.0");
        primaryStage.setScene(primaryScene);
        primaryStage.show();
    }

    /**
     * Sets up main root of gui.
     * @return temp borderpane generated with initial components
     */
    private BorderPane setUpRoot() {
        BorderPane temp = new BorderPane();
        Insets spacing = new Insets(10);
        HBox chamberView = new HBox(10);
        chamberView.setAlignment(Pos.CENTER);
        Node topMenu = setUpTopMenu();
        editButton = setUpEditButt();
        Node leftMenu = setUpLeftMenu();
        primaryText = setUpMainText();
        primaryDoors = setUpAdditionalText();
        temp.setTop(topMenu);
        temp.setBottom(editButton);
        chamberView.getChildren().addAll(leftMenu, primaryText, primaryDoors);
        temp.setCenter(chamberView);
        return temp;
    }

    /**
     * Creates an adjustable pane.
     * @param givenPane pane to adjust with bind.
     * @return adjusted pane that utilizes bind that adjusts to window size
     */
    private BorderPane adjustablePane(BorderPane givenPane) {
        givenPane.prefHeightProperty().bind(primaryScene.heightProperty());
        givenPane.prefWidthProperty().bind(primaryScene.widthProperty());
        return givenPane;
    }

    /**
     * Sets up top part of gui.
     * @return menu composed of top gui elements
     */
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

    /**
     * Sets up left menu of gui.
     * @return menu composed of left gui elements
     */
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

        observeList = FXCollections.observableArrayList(tempList);
        viewList = new ListView<>(observeList);

        viewList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                theController.reactToSpaceChange(t1);
            }
        });

        viewList.getStyleClass().addAll("selectMenu");

        return viewList;
    }

    /**
     * Creates initial edit button.
     * @return temp a plain edit button
     */
    private Button setUpEditButt() {
        Button temp = new Button();
        temp.setText("Edit");
        temp.setOnAction((ActionEvent event) -> {
           theController.reactToEditButton();
        });
        return temp;
    }

    /**
     * Creates initial text area.
     * @return temp a plain introductory text area
     */
    private TextArea setUpMainText() {
        TextArea temp = new TextArea();
        temp.setText("Welcome to Dungeon Generator 4.0!\n" + "A level has already been generated for you\n"
                + "Please select a passage or chamber on the left tab to display it's description");
        temp.setEditable(false);
        return temp;
    }

    /**
     * Creates initial combobox (door list).
     * @return temp a plain combobox
     */
    private ComboBox setUpAdditionalText() {
        int i;
        ArrayList<String> tempList = new ArrayList<>();
        ComboBox<String> temp = new ComboBox<>();
        ObservableList<String> observeList;
        tempList.add("Temporary");
        observeList = FXCollections.observableArrayList(tempList);
        temp.setItems(observeList);

        return temp;
    }

    /**
     * Sets door popup of gui.
     * @param newPane new popup to replace main popup of gui
     * */
    public void setDescriptionPane(Popup newPane) {
        descriptionPane = newPane;
    }

    /**
     * Sets current space's string.
     * @param newString new space to replace current space of gui
     */
    public void setCurrentSpace(String newString) {
        currentSpace = newString;
    }

    /**
     * Returns primary text of gui.
     * @return primaryText global variable that controls main text area of gui
     */
    public TextArea getPrimaryText() {
        return primaryText;
    }

    /**
     * Returns primary stage of gui.
     * @return primaryStage global variable that controls main stage of gui
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Returns primary door popup of gui.
     * @return descriptionPane global variable that controls door popup of gui
     */
    public Popup getDescriptionPane() {
        return descriptionPane;
    }

    /**
     * Returns combobox (doorlist) of gui.
     * @return primaryDoors global variable that controls doorlist of gui
     */
    public ComboBox<String> getDoorCombo() {
        return primaryDoors;
    }

    /**
     * Returns edit button of gui.
     * @return editButton global variable that controls edit button of gui
     */
    public Button getEditButton() {
        return editButton;
    }

    /**
     * Returns combobox (doorlist) of gui.
     * @return primaryDoors global variable that controls doorlist of gui
     */
    public BorderPane getRoot() {
        return root;
    }

    /**
     * Returns primary scene of gui.
     * @return primaryScene global variable that controls scene of gui
     */
    public Scene getPrimaryScene() {
        return primaryScene;
    }

    /**
     * Returns string name of current space.
     * @return currentSpace global variable that determines current space by string
     */
    public String getCurrentSpace() {
        return currentSpace;
    }

    /**
     * Main method of gui class.
     * @param args arguments provided when program is initiated
     */
    public static void main(String[] args) {
        launch(args);
    }

}
