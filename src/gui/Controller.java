package gui;

import epark.Level;
import epark.Space;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Controller {

    private Gui mainGui;
    private Level mainLevel;

    /**
     * Constructor for Controller.
     * @param givenGui Gui provided for controller
     */
    public Controller(Gui givenGui) {
        mainGui = givenGui;
        mainLevel = new Level();
        /*System.out.println (mainLevel.getDescription());*/
    }

    /**
     * Temporary template for button action event handling.
     */
    public void reactToEditButton() {
        System.out.println("To be implemented\n");
    }

    /**
     * Event handles changes when new space is selected to display.
     * @param newSpace name of new space
     */
    public void reactToSpaceChange(String newSpace) {
        mainGui.setCurrentSpace(newSpace);
        mainGui.getPrimaryText().setText(getSpaceDescrip(newSpace));
        /*Change dropdown*/
        updateComboBox(newSpace);
        updateEditButton(newSpace);
    }

    /**
     * Event handles changes to the list of doors when the selected space changes.
     * @param newSpace name of new space
     * @param newDoor name of new door
     */
    public void reactToBoxChange(String newSpace, String newDoor) {
        if (newSpace != null) {
            mainGui.setDescriptionPane(popUpDoor(getDoorDescrip(newSpace, newDoor)));
            if (mainGui.getDescriptionPane().isShowing()) {
                mainGui.getDescriptionPane().hide();
            } else {
                mainGui.getDescriptionPane().show(mainGui.getPrimaryStage());
            }
            mainGui.getDescriptionPane().setAutoHide(true);
        }
    }

    /**
     * Event handles a file save request.
     */
    public void reactToFileSave() {
        FileChooser filePick = new FileChooser();
        File file = filePick.showSaveDialog(mainGui.getPrimaryStage());
        if (file != null) {
            try {
                FileOutputStream fileOut = new FileOutputStream(file.getAbsolutePath());
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(mainLevel);
                out.close();
                fileOut.close();
            } catch (IOException i) {
                System.out.println("Save failed");
            }
        }
        System.out.println("File save");
    }

    /**
     * Event handles a file load request.
     */
    public void reactToFileLoad() {
        FileChooser filePick = new FileChooser();
        File file = filePick.showOpenDialog(mainGui.getPrimaryStage());
        if (file != null) {
            try {
                FileInputStream fileIn = new FileInputStream(file.getAbsolutePath());
                ObjectInputStream in = new ObjectInputStream(fileIn);
                mainLevel = (Level) in.readObject();
                in.close();
                fileIn.close();
            } catch (IOException i) {
                System.out.println("Load failed");
            } catch (ClassNotFoundException c) {
                System.out.println("Class not found");
            }
        }
        System.out.println("File load");
    }

    private void updateComboBox(String newSpace) {
        ComboBox<String> tempBox = new ComboBox<>();
        ArrayList<String> tempList = new ArrayList<>();
        ObservableList<String> observeList;
        int i;
        ChangeListener<String> listener = new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (mainGui.getDoorCombo().getValue() != null && t1 != null && newSpace.equals(mainGui.getCurrentSpace())) {
                    reactToBoxChange(newSpace, t1);
                } else {
                    mainGui.getDoorCombo().getSelectionModel().selectedItemProperty().removeListener(this);
                }
            }
        };

        mainGui.getDoorCombo().getSelectionModel().selectedItemProperty().removeListener(listener);
        mainGui.getDoorCombo().getSelectionModel().clearSelection();
        mainGui.getDoorCombo().setItems(null);
        mainGui.getDescriptionPane().getContent().clear();
        for (i = 0; i < getNumDoors(newSpace); i++) {
            String temp;
            temp = "Door " + (i + 1);
            tempList.add(temp);
        }
        observeList = FXCollections.observableArrayList(tempList);
        mainGui.getDoorCombo().setItems(observeList);
        mainGui.getDoorCombo().getSelectionModel().selectedItemProperty().addListener(listener);
    }

    private void updateEditButton(String newSpace) {
        Button temp = new Button();
        temp.setText("Edit");
        temp.setOnAction((ActionEvent event) -> {
            reactToEditButton();
        });/*
        System.out.println (newSpace);
        mainGui.getEditButton() = temp;*/
    }

    private Popup popUpEdit() {
        Stage newPop = new Stage();
        Popup editPop = new Popup();
        Button monsterButton = new Button();
        Button treasureButton = new Button();

        FlowPane newPane = new FlowPane();

        monsterButton.setText("Modify monsters");
        treasureButton.setText("Modify treasures");

        return null;
    }

    private Popup popUpMonsterEdit() {
        Stage newPop = new Stage();
        Popup editPop = new Popup();
        Button addButton = new Button();
        Button removeButton = new Button();
        TextField monsterIndex = new TextField(); /*Remove*/
        TextField monsterType = new TextField(); /*Add type*/

        FlowPane newPane = new FlowPane();

        monsterIndex.setPromptText("Enter monster index of existing monster");
        monsterType.setPromptText("Enter monster type from 1-100");

        addButton.setText("Add Monster");
        addButton.setOnAction((ActionEvent event) -> {
           /*React to add monster*/
        });
        removeButton.setText("Remove Monster");
        removeButton.setOnAction((ActionEvent event) -> {
           /*React to remove monster*/
        });
        return null;
    }

    private Popup popUpTreasEdit() {
        Stage newPop = new Stage();
        Popup editPop = new Popup();
        Button addButton = new Button();
        Button removeButton = new Button();
        TextField treasureIndex = new TextField(); /*Remove*/
        TextField treasureType = new TextField(); /*Add type*/

        FlowPane newPane = new FlowPane();

        treasureIndex.setPromptText("Enter monster index of existing monster");
        treasureType.setPromptText("Enter treasure type from 1-100");

        addButton.setText("Add Monster");
        addButton.setOnAction((ActionEvent event) -> {
            /*React to add monster*/
        });
        removeButton.setText("Remove Monster");
        removeButton.setOnAction((ActionEvent event) -> {
            /*React to remove monster*/
        });
        return null;
    }

    /**
     * Creates new pop up door with new description.
     * @param description string description on what to display on popup
     * @return new popup that displays description
     */
    private Popup popUpDoor(String description) {
        Popup doorPop = new Popup();
        Label doorDescrip = new Label(description);
        Button close = new Button("Close");

        doorPop.getContent().add(doorDescrip);
        close.setOnAction(e -> doorPop.hide());

        return doorPop;
    }

    /**
     * Event handles a description change request.
     * @param newChamber string of space selected
     * @return textString string description of space returned
     */
    public String getSpaceDescrip(String newChamber) {
        int index;
        String textString;
        /*System.out.println("Description change");
        System.out.println(newChamber);*/
        index = Integer.parseInt(newChamber.replaceAll("\\D", ""));
        /*System.out.println(index);*/
        if (newChamber.contains("Chamber")) {
            /*Chamber*/
            textString = mainLevel.getChambers().get(index - 1).getDescription();
        } else {
            /*Passage*/
            textString = mainLevel.getPassages().get(index - 1).getDescription();
        }
        return textString;
    }

    /**
     * Event handles a door description change request.
     * @param newSpace string of space selected
     * @param newDoor string of door selected
     * @return textString string description of space returned
     */
    public String getDoorDescrip(String newSpace, String newDoor) {
        int spaceIndex;
        int doorIndex;
        String textString = "ID: ";
        int spaceNum;
        /*System.out.println("Description change");
        System.out.println(newChamber);*/
        spaceIndex = Integer.parseInt(newSpace.replaceAll("\\D", ""));
        doorIndex = Integer.parseInt(newDoor.replaceAll("\\D", ""));
        /*System.out.println(index);*/
        if (newSpace.contains("Chamber")) {
            /*Chamber*/
            textString = textString.concat(mainLevel.getChambers().get(spaceIndex - 1).getDoor(doorIndex - 1).getDescription());
            spaceNum = mainLevel.nextSpaceIndex(mainLevel.getChambers().get(spaceIndex - 1).getDoor(doorIndex - 1));
        } else {
            /*Passage*/
            textString = textString.concat(mainLevel.getPassages().get(spaceIndex - 1).getDoor(doorIndex - 1).getDescription());
            spaceNum = mainLevel.nextSpaceIndex(mainLevel.getPassages().get(spaceIndex - 1).getDoor(doorIndex - 1));
        }
        textString = textString.concat("This door leads to Chamber " + spaceNum);
        return textString;
    }

    /**
     * Event handles a space change request and returns specified space.
     * @param newSpace string of space selected
     * @return mainLevel.getChambers()/getPassages().get(index - 1).size() returns space that is currently being displayed
     */
    public int getNumDoors(String newSpace) {
        int index;
        Space foundSpace;

        index = Integer.parseInt(newSpace.replaceAll("\\D", ""));
        if (newSpace.contains("Chamber")) {
            return mainLevel.getChambers().get(index - 1).getDoors().size();
        } else {
            return mainLevel.getPassages().get(index - 1).getDoors().size();
        }
    }

    /**
     * Returns a generated description of level.
     * @return mainLevel.getDescription description of level generated
     */
    public String getMainDescription() {
        return mainLevel.getDescription();
    }

    /**
     * Returns main level generated.
     * @return mainLevel level generated
     */
    public Level getMainLevel() {
        return mainLevel;
    }

}
