package epark;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class Level implements java.io.Serializable {

    /**
     * Represents the hashmap of all doors and their connections.
     */
    private HashMap<Door, Chamber> doorConnection;
    /**
     * Represents the matched doors (and their target door).
     */
    private HashMap<Door, Door> doorMatch;
    /**
     * Represents the connection between doors and their spaces.
     */
    private HashMap<Door, ArrayList<Chamber>> completeMatch;
    /**
     * Represents the doors of entire level.
     */
    private ArrayList<Door> allDoors;
    /**
     * Represents the arraylist of all passages within level.
     */
    private ArrayList<Passage> levelPassages;
    /**
     * Represents the arraylist of all chambers within level.
     */
    private ArrayList<Chamber> levelChambers;
    /**
     * Represents the string description of the level.
     */
    private String levelDescription;

    /**
     * Main constructor for level.
     * Initializes all required instances and calls all setup methods.
     */
    public Level() {
        this.initAll();
        this.genMiniLevel();
        this.setUpMiniDescription();
    }

    /**
     * Initializes all required instances.
     */
    private void initAll() {
        levelDescription = "";
        levelPassages = new ArrayList<Passage>();
        levelChambers = new ArrayList<Chamber>();
        allDoors = new ArrayList<Door>();
        doorConnection = new HashMap<Door, Chamber>();
        doorMatch = new HashMap<Door, Door>();
        completeMatch = new HashMap<Door, ArrayList<Chamber>>();
    }

    /**
     * Sets up the description of the mini level and indents appropriately.
     */
    private void setUpMiniDescription() {
        int passCount = 0;
        Chamber targetChamb = new Chamber();
        levelDescription = "";

        for (int i = 0; i < 5; i++) {
            levelDescription = levelDescription.concat("----------------------" + "Chamber " + (i + 1) + " (ID: " + (levelChambers.get(i).getID()) + ")----------------------\n");
            levelDescription = levelDescription.concat(levelChambers.get(i).getDescription() + "\n");
            levelDescription = levelDescription.concat("------------------Connections from Chamber " + (i + 1) + "------------------\n");
            if (i >= 1) {
                passCount += levelChambers.get(i - 1).getDoors().size();
            }
            for (int j = passCount; j < levelChambers.get(i).getDoors().size() + passCount; j++) { /*Print all passages per chamber*/
                targetChamb = new Chamber();
                levelDescription = levelDescription.concat(levelPassages.get(j).getDescription());
                targetChamb = doorConnection.get(levelPassages.get(j).getThePassage().get(1).getDoor());
                levelDescription = levelDescription.concat("        Passage leads to Chamber ID: " + targetChamb.getID() + "\n\n");
            }
        }
    }

    /**
     * Sets up mini level appropriately by calling all create methods.
     */
    private void genMiniLevel() {
        Chamber tempChamb = new Chamber();
        Passage tempPass = new Passage();
        String chamberName;
        String doorName;

        this.clearArrayLists();
        this.createChambers(5);
        this.createAllDoors();
        this.assignDoors();
        this.connectDoors();
        this.connectMatch();
        this.createPassages();
    }

    /**
     * Allocates all doors that exist (generated) into allDoors arraylist.
     */
    private void createAllDoors() {

        int i;
        for (i = 0; i < levelChambers.size(); i++) {
            allDoors.addAll(levelChambers.get(i).getDoors());
        }

    }

    /**
     * Creates number of chambers appropriately.
     *
     * @param num number of chambers to generate for level
     */
    private void createChambers(int num) {

        do {
            levelDescription = "";
            genChamber();
        } while (levelChambers.size() < num);

    }

    /**
     * Creates number of passages according to number of chambers.
     */
    private void createPassages() {
        int i;

        for (i = 0; i < levelChambers.size(); i++) {
            createMiniConnection(i);
        }
    }

    /**
     * Creates mini connections (links) between each doors between the chambers.
     *
     * @param chambIndex index number of the chamber within levelChambers
     */
    private void createMiniConnection(int chambIndex) {
        ArrayList<Door> chambDoors = new ArrayList<Door>();
        ArrayList<PassageSection> connectSections = new ArrayList<PassageSection>();
        PassageSection tempSection = new PassageSection();
        PassageSection defSection = new PassageSection();
        Passage connection = new Passage();
        int i;

        chambDoors = levelChambers.get(chambIndex).getDoors();
        for (i = 0; i < chambDoors.size(); i++) {
            tempSection = new PassageSection(randomPSDescrip(randomD3()));
            defSection = new PassageSection("passage ends in Door to a Chamber");
            connection = new Passage();
            tempSection.setDoor(chambDoors.get(i));
            connection.addPassageSection(tempSection);
            defSection.setDoor(doorMatch.get(chambDoors.get(i)));
            connection.addPassageSection(defSection);
            levelPassages.add(connection);
        }
    }

    /**
     * Returns number from 0-2.
     *
     * @return rand.nextInt(3) random number from 0 to 2
     */
    private int randomD3() {
        Random rand = new Random();
        return rand.nextInt(3);
    }

    /**
     * Returns passage section description necessary for connection.
     *
     * @param index index number of passage description
     * @return psDescrip string description of passage section
     */
    private String randomPSDescrip(int index) {
        String psDescrip = "";

        if (index == 0) {
            psDescrip = "passage turns to left and continues for 10 ft";
        } else if (index == 1) {
            psDescrip = "passage turns to right and continues for 10 ft";
        } else {
            psDescrip = "passage goes straight for 10 ft";
        }
        return psDescrip;
    }

    /**
     * Assigns all doors a target appropriately.
     */
    private void assignDoors() {
        ArrayList<Chamber> tempChambers;
        ArrayList<Door> tempDoors;
        int i;
        int j;

        tempChambers = levelChambers;
        for (i = 0; i < levelChambers.size(); i++) {
            tempDoors = levelChambers.get(i).getDoors();
            for (j = 0; j < tempDoors.size(); j++) {
                doorConnection.put(tempDoors.get(j), tempChambers.get(i)); /*Connect door to chamber*/
            }
        }
    }

    /**
     * Connects all doors by sending them to the doorMatch instance hashmap.
     */
    private void connectDoors() {
        ArrayList<Chamber> tempChambers = new ArrayList<Chamber>();
        ArrayList<Door> tempDoors = new ArrayList<Door>();
        Door tempDoor = new Door();
        ArrayList<Door> otherDoors = new ArrayList<Door>();
        int i;
        int j;
        int m;

        tempChambers = levelChambers;
        for (i = 0; i < levelChambers.size(); i++) { /*Scan each chamber*/
            tempDoors = levelChambers.get(i).getDoors(); /*Get doors of specified chamber*/
            otherDoors.clear();
            for (j = 0; j < levelChambers.size(); j++) { /*Compiled of all doors without doors of current chamber*/
                if (j != i) {
                    otherDoors.addAll(0, levelChambers.get(j).getDoors());
                }
            }
            Collections.shuffle(otherDoors); /*Randomizes list content order*/
            scanAndConnect(tempDoors, otherDoors);
        }
    }

    /**
     * Adds and connects appropriate doors.
     *
     * @param tempDoors arraylist of doors to check
     * @param otherDoors arraylist of door that do not belong to specific chamber
     */
    private void scanAndConnect(ArrayList<Door> tempDoors, ArrayList<Door> otherDoors) {
        Random rand = new Random();
        int foundDoor = 0;
        int m;
        int k;
        int p;

        for (m = 0; m < tempDoors.size(); m++) { /*Scan through all doors of that specified chamber*/
            /*Other doors contains all other doors than the current selected door*/
            foundDoor = 0;
            k = 0;
            if (!doorMatch.containsKey(tempDoors.get(m))) { /*If hashmap don't contain door, find random*/
                while (foundDoor == 0 && k < otherDoors.size()) {
                    if (!doorMatch.containsKey(otherDoors.get(k))) {
                        connectDoor(tempDoors.get(m), otherDoors.get(k));
                        foundDoor = 1;
                    }
                    k++;
                }
                if (foundDoor == 0) { /*Covers keys that have not been allocated yet all other different keys have been targetted*/
                    p = rand.nextInt(otherDoors.size());
                    connectDoor(tempDoors.get(m), otherDoors.get(p));
                }
            }
        }
    }

    /**
     * Connects the specified doors (for function scanAndConnect).
     *
     * @param firstDoor first door to connect
     * @param secondDoor second door to connect
     */
    private void connectDoor(Door firstDoor, Door secondDoor) {
        doorMatch.put(firstDoor, secondDoor);
        doorMatch.put(secondDoor, firstDoor);
    }

    /**
     * Connects all doors to their appropriate spaces by sending them to completeMatch instance hashmap.
     */
    private void connectMatch() {

        int i;
        int j;
        Door tempDoor = new Door();
        Door tempNextDoor = new Door();
        Space tempSpace;
        Space tempNextSpace;
        ArrayList<Chamber> tempSpaces;

        for (i = 0; i < allDoors.size(); i++) {
            tempSpaces = new ArrayList<Chamber>();
            tempDoor = allDoors.get(i);

            tempSpace = tempDoor.getSpaces().get(0);
            tempSpaces.add((Chamber) tempSpace);
            tempNextDoor = doorMatch.get(tempDoor);
            tempNextSpace = tempNextDoor.getSpaces().get(0);
            tempSpaces.add((Chamber) tempNextSpace);
            completeMatch.put(tempDoor, tempSpaces);
        }
    }

    /**
     * Clears both ArrayLists within the Level class.
     */
    private void clearArrayLists() {
        levelPassages.clear();
        levelChambers.clear();
    }

    /**
     * Generates a chamber appropriately.
     */
    private void genChamber() {
        Chamber newChamb = new Chamber();
        addChamber(newChamb);
    }

    /**
     * Adds a passage into the arraylist of passages in level.
     *
     * @param newPassage takes in new passage to add into arraylist levelPassages
     */
    private void addPassage(Passage newPassage) {
        levelPassages.add(newPassage);
    }

    /**
     * Adds a chamber into the arraylist of chambers in level.
     *
     * @param newChamber takes in new chamber to add into arraylist levelChambers
     */
    private void addChamber(Chamber newChamber) {
        levelChambers.add(newChamber);
    }

    /**
     * Gets an unoccupied chamber door.
     *
     * @param someChamber selected chamber to seek unoccupied random door for.
     * @return someChamber.getDoor(tempDoor, i) returns door with no connection
     */
    private Door getUnoccupiedChamberDoor(Chamber someChamber) {
        int i;

        for (i = 0; i < someChamber.getDoors().size(); i++) {
            if (someChamber.getDoor(i).getSpaces().size() < 2) {
                return someChamber.getDoor(i);
            }
        }
        return null;
    }

    /**
     * Returns all passages in level.
     *
     * @return levelPassages all passages of level
     */
    public ArrayList<Passage> getPassages() {
        return levelPassages;
    }

    /**
     * Returns all chambers in level.
     *
     * @return levelChambers all chambers of level
     */
    public ArrayList<Chamber> getChambers() {
        return levelChambers;
    }

    /**
     * Returns the description of the entire level.
     *
     * @return levelDescription entire description of level
     */
    public String getDescription() {
        return levelDescription;
    }
}


