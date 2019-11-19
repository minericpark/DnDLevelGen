package gui;

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
     * @param newChamber string of chamber selected
     */
    public String reactToDescripChange(String newChamber) {
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
