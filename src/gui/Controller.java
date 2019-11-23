package gui;

import epark.Level;
import epark.Space;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.nio.file.Paths;

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
     * Event handles click of edit button.
     */
    public void reactToEditButton() {
        System.out.println(mainGui.getCurrentSpace());
        mainGui.openEdit();
    }

    /**
     * Event handles addition of treasure.
     * @param selectedTreasure name of treasure selected for addition
     */
    public void reactToAddTreasure(String selectedTreasure) {
        int givenSpace;
        int givenTreasure;
        int givenPS;

        if (!selectedTreasure.equals("")) {
            if (mainGui.openConfirm() == 1) {
                if (mainGui.getCurrentSpace().contains("Chamber")) {
                    givenSpace = Integer.parseInt(mainGui.getCurrentSpace().replaceAll("\\D", ""));
                    givenTreasure = (int) mainLevel.mapOfTreasures().get(selectedTreasure);
                    if (mainLevel.getChambers().get(givenSpace - 1).addTreasGui(givenTreasure) == 0) {
                        mainGui.openError();
                    }
                } else {
                    givenSpace = Integer.parseInt(mainGui.getCurrentSpace().replaceAll("\\D", ""));
                    givenTreasure = (int) mainLevel.mapOfTreasures().get(selectedTreasure);
                    /*Create popup*/
                    givenPS = mainGui.openPSNum() - 1;
                    if (givenPS < mainLevel.getPassages().get(givenSpace - 1).getThePassage().size() && givenPS >= 0) {
                        mainLevel.getPassages().get(givenSpace - 1).getThePassage().get(givenPS).addTreasGui(givenTreasure);
                    } else {
                        mainGui.openError();
                    }
                    mainLevel.getPassages().get(givenSpace - 1).updateDescription();
                }
                reactToSpaceChange(mainGui.getCurrentSpace());
                /*System.out.println(mainLevel.getDescription());*/
            } else {
                System.out.println("Add failed");
            }
        }
    }

    /**
     * Event handles addition of monster.
     * @param selectedMonster name of monster selected for addition
     */
    public void reactToAddMonster(String selectedMonster) {
        int givenSpace;
        int givenMonster;
        int givenPS;

        if (!selectedMonster.equals("")) {
            if (mainGui.openConfirm() == 1) {
                if (mainGui.getCurrentSpace().contains("Chamber")) {
                    givenSpace = Integer.parseInt(mainGui.getCurrentSpace().replaceAll("\\D", ""));
                    givenMonster = (int) mainLevel.mapOfMonsters().get(selectedMonster);
                    if (mainLevel.getChambers().get(givenSpace - 1).addMonGui(givenMonster) == 0) {
                        mainGui.openError();
                    }
                } else {
                    givenSpace = Integer.parseInt(mainGui.getCurrentSpace().replaceAll("\\D", ""));
                    givenMonster = (int) mainLevel.mapOfMonsters().get(selectedMonster);
                    /*Create popup*/
                    givenPS = mainGui.openPSNum() - 1;
                    if (givenPS < mainLevel.getPassages().get(givenSpace - 1).getThePassage().size() && givenPS >= 0) {
                        mainLevel.getPassages().get(givenSpace - 1).getThePassage().get(givenPS).addMonGui(givenMonster);
                    } else {
                        mainGui.openError();
                    }
                    mainLevel.getPassages().get(givenSpace - 1).updateDescription();
                }
                reactToSpaceChange(mainGui.getCurrentSpace());
                /*System.out.println(mainLevel.getDescription());*/
            } else {
                System.out.println("Add failed");
            }
        }
    }

    /**
     * Event handles removal of treasure.
     * @param indexTreasure index of treasure selected for removal
     */
    public void reactToRemTreasure(int indexTreasure) {

        int givenSpace;
        int givenPS;

        if (indexTreasure != 0) {
            if (mainGui.openConfirm() == 1) {
                if (mainGui.getCurrentSpace().contains("Chamber")) {
                    givenSpace = Integer.parseInt(mainGui.getCurrentSpace().replaceAll("\\D", ""));
                    if (mainLevel.getChambers().get(givenSpace - 1).removeTreasGui(indexTreasure - 1) == 0) {
                        mainGui.openError();
                    }
                } else {
                    givenSpace = Integer.parseInt(mainGui.getCurrentSpace().replaceAll("\\D", ""));
                    /*Create popup*/
                    givenPS = mainGui.openPSNum() - 1;
                    if (givenPS < mainLevel.getPassages().get(givenSpace - 1).getThePassage().size() && givenPS >= 0 && mainLevel.getPassages().get(givenSpace - 1).getThePassage().get(givenPS).getTreasures().size() > indexTreasure - 1) {
                        mainLevel.getPassages().get(givenSpace - 1).getThePassage().get(givenPS).removeTreasGui(indexTreasure - 1);
                    } else {
                        mainGui.openError();
                    }
                    mainLevel.getPassages().get(givenSpace - 1).updateDescription();
                }
                reactToSpaceChange(mainGui.getCurrentSpace());
            } else {
                System.out.println("Remove failed");
            }
        }
    }

    /**
     * Event handles removal of monster.
     * @param indexMonster index of monster selected for removal
     */
    public void reactToRemMonster(int indexMonster) {
        int givenSpace;
        int givenMonster;
        int givenPS;

        if (indexMonster != 0) {
            if (mainGui.openConfirm() == 1) {
                if (mainGui.getCurrentSpace().contains("Chamber")) {
                    givenSpace = Integer.parseInt(mainGui.getCurrentSpace().replaceAll("\\D", ""));
                    if (mainLevel.getChambers().get(givenSpace - 1).removeMonGui(indexMonster - 1) == 0) {
                        mainGui.openError();
                    }
                } else {
                    givenSpace = Integer.parseInt(mainGui.getCurrentSpace().replaceAll("\\D", ""));
                    /*Create popup*/
                    givenPS = mainGui.openPSNum() - 1;
                    if (givenPS < mainLevel.getPassages().get(givenSpace - 1).getThePassage().size() && givenPS >= 0 && mainLevel.getPassages().get(givenSpace - 1).getThePassage().get(givenPS).getMonsters().size() > indexMonster - 1) {
                        mainLevel.getPassages().get(givenSpace - 1).getThePassage().get(givenPS).removeMonGui(indexMonster - 1);
                    } else {
                        mainGui.openError();
                    }
                    mainLevel.getPassages().get(givenSpace - 1).updateDescription();
                }
                reactToSpaceChange(mainGui.getCurrentSpace());
            } else {
                System.out.println("Remove failed");
            }
        }
    }

    /**
     * Event handles changes when new space is selected to display.
     * @param newSpace name of new space
     */
    public void reactToSpaceChange(String newSpace) {
        mainGui.setCurrentSpace(newSpace);
        mainGui.getPrimaryText().setText(getSpaceDescrip(newSpace));
        /*Change dropdown*/
        mainGui.updateComboBox(newSpace);
    }

    /**
     * Event handles changes to the list of doors when the selected space changes.
     * @param newSpace name of new space
     * @param newDoor name of new door
     */
    public void reactToBoxChange(String newSpace, String newDoor) {
        if (newSpace != null) {
            mainGui.setDescriptionPane(mainGui.updatePopUpDoor(getDoorDescrip(newSpace, newDoor)));
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
        String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
        File initialDirectory = new File(currentPath);
        File file;

        filePick.setInitialDirectory(initialDirectory);
        file = filePick.showSaveDialog(mainGui.getPrimaryStage());
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
    }

    /**
     * Event handles a file load request.
     */
    public void reactToFileLoad() {
        FileChooser filePick = new FileChooser();
        String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
        File initialDirectory = new File(currentPath);
        File file;

        filePick.setInitialDirectory(initialDirectory);
        file = filePick.showOpenDialog(mainGui.getPrimaryStage());
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
        /*Implement better change when there is time*/
        reactToSpaceChange("Chamber 1");
    }

    /**
     * Checks if given string is integer.
     * @param givenString provided string to check
     * @return 0/1 - 0 for false, 1 for true
     */
    public int isInteger(String givenString) {
        try {
            double d = Integer.parseInt(givenString);
        } catch (NumberFormatException | NullPointerException ne) {
            return 0;
        }
        if (Integer.parseInt(givenString) <= 0) {
            return 0;
        }
        return 1;
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
