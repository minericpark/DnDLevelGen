package gui;

import epark.Level;
import epark.Space;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
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
        mainGui.getPrimaryText().setText(getSpaceDescrip(newSpace));
        /*Change dropdown*/
        updateComboBox(newSpace);
    }

    /**
     * Event handles changes to the list of doors when the selected space changes.
     * @param newSpace name of new space
     */
    public void reactToBoxChange(String newSpace) {
        if (newSpace != null) {
            mainGui.setDescriptionPane(popUpDoor(getDoorDescrip(newSpace, newSpace)));
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

        mainGui.getDoorCombo().setItems(null);
        for (i = 0; i < getNumDoors(newSpace); i++) {
            String temp;
            temp = "Door " + (i + 1);
            tempList.add(temp);
        }
        observeList = FXCollections.observableArrayList(tempList);
        mainGui.getDoorCombo().setItems(observeList);
        mainGui.getDoorCombo().getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                reactToBoxChange(t1);
            }
        });
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
        String textString;
        /*System.out.println("Description change");
        System.out.println(newChamber);*/
        spaceIndex = Integer.parseInt(newSpace.replaceAll("\\D", ""));
        doorIndex = Integer.parseInt(newDoor.replaceAll("\\D", ""));
        /*System.out.println(index);*/
        if (newSpace.contains("Chamber")) {
            /*Chamber*/
            textString = mainLevel.getChambers().get(spaceIndex - 1).getDoor(doorIndex - 1).getDescription();
        } else {
            /*Passage*/
            textString = mainLevel.getPassages().get(spaceIndex - 1).getDoor(doorIndex - 1).getDescription();
        }
        return "ID: " + textString;
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
