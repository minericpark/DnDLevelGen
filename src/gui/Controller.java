package gui;

import epark.Door;
import epark.Level;
import epark.Space;

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
     * Event handles a file save request.
     */
    public void reactToFileSave() {
        System.out.println("File save");
    }

    /**
     * Event handles a file load request.
     */
    public void reactToFileLoad() {
        System.out.println("File load");
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
     * Event handles a space change request and returns specified space.
     * @param newChamber string of space selected
     * @return mainLevel.getChambers()/getPassages().get(index - 1) returns space that is currently being displayed
     */
    public ArrayList<Door> getSpaceDoors(String newChamber) {
        int index;
        Space foundSpace;

        index = Integer.parseInt(newChamber.replaceAll("\\D", ""));
        if (newChamber.contains("Chamber")) {
            return mainLevel.getChambers().get(index - 1).getDoors();
        } else {
            return mainLevel.getPassages().get(index - 1).getDoors();
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
