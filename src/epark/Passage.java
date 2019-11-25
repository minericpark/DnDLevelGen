package epark;

import db.DBMonster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Passage extends epark.Space implements java.io.Serializable {

    /**
     * Represents the list of passage sections composed for the passage.
     */
    private ArrayList<PassageSection> thePassage;
    /**
     * Represents the hashmap of passage sections connected to doors.
     */
    private HashMap<Door, PassageSection> doorMap;
    /**
     * Represents the description of the passage.
     */
    private String passageDescription;
    /**
     * Represents whether door exists within passage or not.
     */
    private boolean doorExist;
    /**
     * Represents the ID number of the passage.
     */
    private int passID;

    /**
     * Main constructor for passage.
     * Initializes all required instances and calls all setup methods.
     */
    public Passage() {
        Random random = new Random();
        thePassage = new ArrayList<PassageSection>();
        doorMap = new HashMap<Door, PassageSection>();
        this.doorExist = false;
        this.passageDescription = "";
        this.passID = random.nextInt(1000);
    }

    /**
     * Sets passage description (listing all passage sections).
     */
    private void setPassageDescription() {
        int i = 0;
        this.passageDescription = "";

        for (i = 0; i < thePassage.size(); i++) {
            this.passageDescription = this.passageDescription.concat("Passage Section " + (i + 1) + ": " + this.thePassage.get(i).getDescription());
        }
    }

    /**
     * Updates passage description after an action has been conducted.
     */
    public void updateDescription() {
        this.setPassageDescription();
    }

    /**
     * Adds door into current passage section through the hashmap.
     *
     * @param newDoor door that is to be connected to the latest passage section
     */
    @Override
    public void setDoor(Door newDoor) {
        //should add a door connection to the current Passage Section
        this.doorExist = true;
        if (thePassage.size() > 0) {
            createCurrentDoor(newDoor);
        } else {
            createBlankDoor(newDoor);
        }
        this.updateDescription();
    }

    /**
     * Adds door into current passage section through the hashmap.
     *
     * @param newDoor door that is to be connected to the latest passage section
     */
    private void createCurrentDoor(Door newDoor) {

        thePassage.get(thePassage.size() - 1).updateDoor(newDoor);
        newDoor.setSpace(this);
        this.doorMap.put(newDoor, thePassage.get(thePassage.size() - 1));
    }

    /**
     * Adds door into current passage section (blank) through the hashmap.
     *
     * @param newDoor door that is to be connected to the blank passage section
     */
    private void createBlankDoor(Door newDoor) {

        PassageSection newSec = new PassageSection();
        thePassage.add(newSec);
        thePassage.get(0).updateDoor(newDoor);
        newDoor.setSpace(this);
        this.doorMap.put(newDoor, thePassage.get(0));
    }

    /**
     * Adds monster newMonster into passage section i.
     *
     * @param newMonster monster to replace/add into passage section
     * @param i          number representing which passage section to add monster into
     */
    public void addMonster(DBMonster newMonster, int i) {
        // adds a monster to section 'i' of the passage
        this.thePassage.get(i).addMonster(newMonster);
        this.updateDescription();
    }

    /**
     * Adds passage section toAdd into passage.
     *
     * @param toAdd passage section to add into passage
     */
    public void addPassageSection(PassageSection toAdd) {
        if (toAdd.getDoorExist()) {
            this.doorExist = true;
        }
        this.thePassage.add(toAdd);
        this.updateDescription();
    }

    /**
     * Returns the complete description of the passage description.
     *
     * @return passageDescription string description of the complete passage
     */
    @Override
    public String getDescription() {
        return this.passageDescription;
    }

    /**
     * Returns whether door exists within passage or not.
     *
     * @return doorExist bool representation of whether door exists or not
     */
    public boolean getDoorExist() {
        return this.doorExist;
    }

    /**
     * Returns arraylist of all doors within passage.
     *
     * @return allDoors list of all passage section doors within passage
     */
    public ArrayList getDoors() {
        //gets all of the doors in the entire passage
        ArrayList<Door> allDoors = new ArrayList<>();
        int i;

        for (i = 0; i < thePassage.size(); i++) {
            allDoors.add(thePassage.get(i).getDoor());
        }

        return allDoors;
    }

    /**
     * Returns door of section i.
     *
     * @param i number representing which passage section to retrieve door from
     * @return thePassage.get(i).getDoor(); command that calls the door of the specified passage section
     */
    public Door getDoor(int i) {
        //returns the door in section 'i'. If there is no door, returns null
        return this.thePassage.get(i).getDoor();
    }

    /**
     * Returns monsters at passage section i.
     *
     * @param i number representing which passage section to get monster from
     * @return thePassage.get(i).getMonster(); command that calls the monster of the specified passage section
     */
    public ArrayList<DBMonster> getMonsters(int i) {
        //returns Monster door in section 'i'. If there is no Monster, returns null
        return this.thePassage.get(i).getMonsters();
    }

    /**
     * Gets the arraylist of passage sections that represent the passage.
     *
     * @return thePassage arraylist of passage sections that represent the completed passage
     */
    public ArrayList<PassageSection> getThePassage() {
        return this.thePassage;
    }

    /**
     * Returns the ID number of passage.
     *
     * @return chambID ID integer representative of passage.
     */
    public int getID() {
        return this.passID;
    }

    /**
     * Removes a passage section from the passage.
     *
     * @param i index of removed passage section
     */
    public void removeSection(int i) {
        thePassage.remove(i);
    }
}
