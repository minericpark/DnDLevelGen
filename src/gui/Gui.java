package gui;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

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
        currentSpace = "null";
        primaryStage = givenStage;
        root = setUpRoot();
        primaryScene = new Scene(root, 820, 675);
        root = adjustablePane(root);
        descriptionPane = new Popup();
        this.attachDefCSS(primaryScene);
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
        HBox chamberView = createGeneralHbox();
        VBox doorView = createGeneralVbox();
        Node leftMenu = setUpLeftMenu();
        Node topMenu = setUpTopMenu();

        chamberView.setAlignment(Pos.CENTER);
        editButton = setUpEditButt();
        primaryText = setUpMainText();
        primaryDoors = setUpAdditionalText();
        doorView.getChildren().add(primaryDoors);
        doorView.getChildren().add(editButton);
        chamberView.getChildren().addAll(leftMenu, primaryText, doorView);
        temp.setTop(topMenu);
        temp.setLeft(leftMenu);
        temp.setCenter(primaryText);
        temp.setRight(doorView);
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
        ObservableList<String> observeList;
        ListView<String> viewList;

        observeList = FXCollections.observableArrayList(theController.getAllSpaces());
        viewList = new ListView<>(observeList);

        viewList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                theController.reactToSpaceChange(t1);
            }
        });
        viewList.setPrefWidth(130);
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
        temp.getStyleClass().addAll("editButton");
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
        temp.getStyleClass().addAll("mainText");
        return temp;
    }

    /**
     * Creates initial combobox (door list).
     * @return temp a plain combobox
     */
    private ComboBox setUpAdditionalText() {
        ArrayList<String> tempList = new ArrayList<>();
        ComboBox<String> temp = new ComboBox<>();
        ObservableList<String> observeList;
        tempList.add("Temporary");
        observeList = FXCollections.observableArrayList(tempList);
        temp.setItems(observeList);
        temp.getStyleClass().addAll("doorMenu");

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
     * Updates the combobox used to display the list of doors.
     * @param newSpace string name of new space
     */
    public void updateComboBox(String newSpace) {
        ObservableList<String> observeList;
        ChangeListener<String> listener = new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (getDoorCombo().getValue() != null && t1 != null && newSpace.equals(getCurrentSpace())) {
                    theController.reactToBoxChange(newSpace, t1);
                } else {
                    getDoorCombo().getSelectionModel().selectedItemProperty().removeListener(this);
                }
            }
        };

        getDoorCombo().getSelectionModel().selectedItemProperty().removeListener(listener);
        getDoorCombo().getSelectionModel().clearSelection();
        getDoorCombo().setItems(null);
        getDescriptionPane().getContent().clear();
        observeList = FXCollections.observableArrayList(theController.getAllDoors(newSpace));
        getDoorCombo().setItems(observeList);
        getDoorCombo().getSelectionModel().selectedItemProperty().addListener(listener);
    }

    /**
     * Creates new pop up door with new description.
     * @param description string description on what to display on popup
     * @return new popup that displays description
     */
    public Popup updatePopUpDoor(String description) {
        Popup doorPop = new Popup();
        Label doorDescrip = new Label(description);
        Button close = new Button("Close");

        doorPop.getContent().add(doorDescrip);
        close.setOnAction(e -> doorPop.hide());
        doorDescrip.getStyleClass().addAll("popUp");
        return doorPop;
    }

    /**
     * Opens edit popup menu on call.
     */
    public void openEdit() {
        Stage newPop = new Stage();
        Scene newScene;
        FlowPane newPane = createGeneralPane();
        Button monsterButton = new Button();
        Button treasureButton = new Button();

        monsterButton.setText("Modify monsters");
        monsterButton.setOnAction((ActionEvent event) -> {
            this.openMonsterEdit();
        });
        treasureButton.setText("Modify treasures");
        treasureButton.setOnAction((ActionEvent event) -> {
            this.openTreasEdit();
        });
        newPane.getChildren().add(monsterButton);
        newPane.getChildren().add(treasureButton);
        newScene = new Scene(newPane);
        this.attachDefCSS(newScene);
        monsterButton.getStyleClass().addAll("subEditButton");
        treasureButton.getStyleClass().addAll("subEditButton");
        newPane.getStyleClass().addAll("editPop");
        newPop.setScene(newScene);
        newPop.setTitle("Edit Popup");
        newPop.show();
    }

    /**
     * Opens error popup that occurs when user does something invalid.
     */
    public void openError() {
        Stage newPop = new Stage();
        Scene newScene;
        FlowPane newPane = createGeneralPane();
        Text error = new Text();

        error.setText("Invalid request");
        newPane.getChildren().add(error);
        newScene = new Scene(newPane);
        this.attachDefCSS(newScene);
        newPane.getStyleClass().addAll("editPop");
        error.getStyleClass().addAll("message");
        newPop.setScene(newScene);
        newPop.setTitle("Error");
        newPop.show();
    }

    /**
     * Opens popup that request passage section number.
     * @return psNum passage section number
     * */
    public int openPSNum() {
        Stage newPop = new Stage();
        Scene newScene;
        FlowPane newPane = createGeneralPane();
        TextField passageField = createGeneralTextF("Passage section number");
        Button confirmButton = createGeneralButton("Submit passage section number");
        AtomicInteger psNum = new AtomicInteger();

        psNum.set(0);
        confirmButton.setOnAction((ActionEvent ev) -> {
            /*React to remove monster*/
            if (theController.isInteger(passageField.getText()) != 0) {
                psNum.set(Integer.parseInt(passageField.getText().replaceAll("\\D", "")));
                newPop.close();
            }
        });
        newPane.getChildren().add(passageField);
        newPane.getChildren().add(confirmButton);
        newScene = new Scene(newPane);
        this.attachDefCSS(newScene);
        confirmButton.getStyleClass().addAll("subEditButton");
        newPane.getStyleClass().addAll("passageNumAdd");
        newPop.setScene(newScene);
        newPop.setTitle("Passage Section Number Input");
        newPop.showAndWait();

        return psNum.get();
    }

    /**
     * Opens monster's popup menu on call.
     */
    private void openMonsterEdit() {
        Stage newPop = new Stage();
        Scene newScene;
        FlowPane newPane = createGeneralPane();
        VBox addMon = new VBox();
        VBox removeMon = new VBox();
        Button addButton = createGeneralButton("Add Monster");
        Button removeButton = createGeneralButton("Remove Monster");
        /*Dropdown of monster to add*/
        ComboBox<String> typesDisplay;
        ArrayList<String> monsterTypes = new ArrayList<>();
        ComboBox<String> existingDisplay;
        ArrayList<String> monstersInSpace = new ArrayList<>();
        final String[] selectedMonster = {""};
        final String[] selectedExistMons = {""};
        int index;

        monsterTypes.addAll(theController.getMainLevel().listOfMonster());
        typesDisplay = new ComboBox(FXCollections.observableArrayList(monsterTypes));

        if (!currentSpace.equals("null")) {
            index = theController.parseForIndex(currentSpace);
            if (currentSpace.contains("Chamber")) {
                monstersInSpace.addAll(theController.getAllChambMonsters(index));
            } else {
                monstersInSpace.addAll(theController.getAllPassMonsters(index));
            }
        }
        existingDisplay = new ComboBox(FXCollections.observableArrayList(monstersInSpace));

        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                selectedMonster[0] = typesDisplay.getValue();
            }
        };
        EventHandler<ActionEvent> event2 = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                selectedExistMons[0] = existingDisplay.getValue();
            }
        };
        typesDisplay.setOnAction(event);
        existingDisplay.setOnAction(event2);

        addButton.setOnAction((ActionEvent ev) -> {
            /*React to add monster*/
            theController.reactToAddMonster(selectedMonster[0]);
            newPop.close();
        });
        removeButton.setOnAction((ActionEvent ev) -> {
            /*React to remove monster*/
            theController.reactToRemMonster(selectedExistMons[0]);
            newPop.close();
        });

        addMon.getChildren().add(typesDisplay);
        addMon.getChildren().add(addButton);
        removeMon.getChildren().add(existingDisplay);
        removeMon.getChildren().add(removeButton);
        newPane.getChildren().add(addMon);
        newPane.getChildren().add(removeMon);
        newScene = new Scene(newPane);
        this.attachDefCSS(newScene);
        typesDisplay.getStyleClass().addAll("itemMenu");
        existingDisplay.getStyleClass().addAll("itemMenu");
        addButton.getStyleClass().addAll("subEditButton");
        removeButton.getStyleClass().addAll("subEditButton");
        newPane.getStyleClass().addAll("editPop");
        newPop.setScene(newScene);
        newPop.setTitle("Monster Edit");
        newPop.show();
    }

    /**
     * Opens treasure edit popup menu on call.
     */
    private void openTreasEdit() {
        Stage newPop = new Stage();
        Scene newScene;
        FlowPane newPane = createGeneralPane();
        VBox addTreas = new VBox();
        VBox removeTreas = new VBox();
        Button addButton = createGeneralButton("Add Treasure");
        Button removeButton = createGeneralButton("Remove Treasure");
        TextField treasureIndex = createGeneralTextF("Enter treasure index of existing monster"); /*Remove*/
        ComboBox<String> typesDisplay;
        ArrayList<String> treasureTypes = new ArrayList<>();
        final String[] selectedTreasure = {""};

        treasureTypes.addAll(theController.getMainLevel().listOfTreasure());
        typesDisplay = new ComboBox(FXCollections.observableArrayList(treasureTypes));
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                selectedTreasure[0] = typesDisplay.getValue();
            }
        };
        typesDisplay.setOnAction(event);
        addButton.setOnAction((ActionEvent ev) -> {
            /*React to add monster*/
            theController.reactToAddTreasure(selectedTreasure[0]);
            newPop.close();
        });
        removeButton.setOnAction((ActionEvent ev) -> {
            /*React to remove monster*/
            if (theController.isInteger(treasureIndex.getText()) != 0) {
                theController.reactToRemTreasure(theController.parseForIndex(treasureIndex.getText()));
                newPop.close();
            }
        });

        addTreas.getChildren().add(typesDisplay);
        addTreas.getChildren().add(addButton);
        removeTreas.getChildren().add(treasureIndex);
        removeTreas.getChildren().add(removeButton);
        newPane.getChildren().add(addTreas);
        newPane.getChildren().add(removeTreas);
        newScene = new Scene(newPane);
        this.attachDefCSS(newScene);
        typesDisplay.getStyleClass().addAll("itemMenu");
        addButton.getStyleClass().addAll("subEditButton");
        removeButton.getStyleClass().addAll("subEditButton");
        newPane.getStyleClass().addAll("editPop");
        newPop.setScene(newScene);
        newPop.setTitle("Treasure Edit");
        newPop.show();
    }

    /**
     * Creates popup confirming user's action, and returns 0/1 dependent on action.
     * @return 0/1, 0 for no and 1 for yes
     */
    public int openConfirm() {
        Stage newPop = new Stage();
        Scene newScene;
        FlowPane newPane = createGeneralPane();
        Text confirmText = new Text();
        Button confirmButton = createGeneralButton("Save Edit");
        Button discardButton = createGeneralButton("Discard Edit");
        HBox buttonBox = new HBox();
        AtomicInteger success = new AtomicInteger();

        success.set(0);
        confirmButton.setOnAction((ActionEvent ev) -> {
            /*React to remove monster*/
            success.set(1);
            newPop.close();
        });
        discardButton.setOnAction((ActionEvent ev) -> {
            /*React to remove monster*/
            success.set(0);
            newPop.close();
        });
        confirmText.setText("Are you sure you want to save this edit?");

        buttonBox.getChildren().add(confirmButton);
        buttonBox.getChildren().add(discardButton);
        newPane.getChildren().add(confirmText);
        newPane.getChildren().add(buttonBox);
        newScene = new Scene(newPane);
        this.attachDefCSS(newScene);
        confirmButton.getStyleClass().addAll("subEditButton");
        discardButton.getStyleClass().addAll("subEditButton");
        newPane.getStyleClass().addAll("editPop");
        confirmText.getStyleClass().addAll("message");
        newPop.setScene(newScene);
        newPop.setTitle("Confirm Edits");
        newPop.showAndWait();
        return success.get();
    }

    /**
     * Attaches main style sheet of GUI to provided pane.
     * @param givenScene provided scene to attach style sheet to
     */
    private void attachDefCSS(Scene givenScene) {
        givenScene.getStylesheets().add((new File("res/styleSheet.css")).toURI().toString());
    }

    /**
     * Create and return a general VBox.
     * @return temp generated vbox
     */
    private VBox createGeneralVbox() {
        VBox temp = new VBox(10);

        temp.getStyleClass().addAll("modMenu");

        return temp;
    }

    /**
     * Create and return a general HBox.
     * @return temp generated hbox
     */
    private HBox createGeneralHbox() {
        HBox temp = new HBox(10);

        return temp;
    }

    /**
     * Creates and returns a generalized flowpane.
     * @return newPane general flowpane for use
     */
    private FlowPane createGeneralPane() {
        FlowPane newPane = new FlowPane();

        newPane.setAlignment(Pos.CENTER);
        newPane.setPadding(new Insets(20));
        newPane.setHgap(20);
        newPane.setVgap(10);
        return newPane;
    }

    /**
     * Creates and returns a generalized button with associated label.
     * @param label given string to label button with
     * @return newButton generalized button with new label
     */
    private Button createGeneralButton(String label) {
        Button newButton = new Button();

        newButton.setText(label);
        return newButton;
    }

    /**
     * Creates and returns a generalized text field with associated label.
     * @param label given string to label textfield with
     * @return newField generalized textfield with new lavel
     */
    private TextField createGeneralTextF(String label) {
        TextField newField = new TextField();

        newField.setPromptText(label);
        return newField;
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
