package epark;

import dnd.die.D20;
import dnd.die.Percentile;
import dnd.exceptions.NotProtectedException;
import dnd.models.Monster;
import dnd.models.Treasure;

import java.util.ArrayList;
import java.util.HashMap;

/* Represents a 10 ft section of passageway */

public class PassageSection implements java.io.Serializable {

    /**
     * Represents the description of the passage section.
     */
    private String passageDescription;
    /**
     * Represents the temporary description of the passage section.
     */
    private String tempDescription;
    /**
     * Stores and labels each passage section's potential descriptions.
     */
    private HashMap<Integer, String> passageTable;
    /**
     * Represents monsters within passage section (if it exists).
     */
    private ArrayList<Monster> passageMonsters;
    /**
     * Represents treasures within passage section (if it exists).
     */
    private ArrayList<Treasure> passageTreasures;
    /**
     * Represents the door of the passage section (if it exists).
     */
    private Door passageDoor;
    /**
     * Represents the boolean on whether monster exists within the passage section or not.
     */
    private boolean monsterExist;
    /**
     * Represents the boolean on whether treasure exists within the passage section or not.
     */
    private boolean treasureExist;
    /**
     * Represents the boolean on whether door exists within the passage section or not.
     */
    private boolean doorExist;
    /**
     * Represents the boolean on whether chamber is ahead of passage section or not.
     */
    private boolean chamberAhead;
    /**
     * Represents the boolean on whether passage section is dead end or not.
     */
    private boolean deadEnd;

    /**
     * Main constructor for passage section without a string import.
     * Initializes all required instances and calls all setup methods.
     */
    public PassageSection() {
        passageTable = new HashMap<Integer, String>();
        passageMonsters = new ArrayList<Monster>();
        passageTreasures = new ArrayList<Treasure>();
        this.passageDescription = "";
        this.setUpDescription(" ");
        this.genContents();
        this.updateDescription();
    }

    /**
     * Main constructor for passage section with a string import.
     * Initializes all required instances and calls all setup methods.
     *
     * @param description is imported string of passage section
     */
    public PassageSection(String description) {
        //sets up a specific passage based on the values sent in from
        //modified table 1
        passageTable = new HashMap<Integer, String>();
        passageMonsters = new ArrayList<Monster>();
        passageTreasures = new ArrayList<Treasure>();
        this.passageDescription = "";
        this.setUpDescription(description);
        this.genContents();
        this.updateDescription();
    }

    /**
     * Updates description of passage section accordingly.
     */
    private void updateDescription() {

        passageDescription = tempDescription;
        if (monsterExist) {
            this.passageDescription = this.passageDescription.concat(getMonsterDescrip());
        }
        if (treasureExist) {
            this.passageDescription = this.passageDescription.concat(getTreasureDescrip());
        }
        /*if (doorExist) {
            this.passageDescription = this.passageDescription.concat(indentString("Passage Door ID: " + this.passageDoor.getDescription()));
        }*/
    }

    /**
     * Generates contents of passage section dependent on string description of passage.
     */
    private void genContents() {
        /*if (passageDescription.toLowerCase().contains("door")) {
            this.genDoor();
        }*/
        if (passageDescription.toLowerCase().contains("monster")) {
            this.genMonster();
        }
        if (passageDescription.toLowerCase().contains("chamber")) {
            this.chamberAhead = true;
        }
        if (passageDescription.toLowerCase().contains("dead end")) {
            this.deadEnd = true;
        }
    }

    /**
     * Generates new door within passage section.
     */
    private void genNewDoor() {
        this.setDoorExist(true);
        Door door = new Door();
        if (passageDescription.toLowerCase().contains("archway")) {
            door.setArchway(true);
        } else {
            door.setArchway(false);
        }
        this.passageDoor = door;
    }

    /**
     * Generates given door within passage section.
     *
     * @param setDoor door used to generate new door
     */
    private void genDoor(Door setDoor) {
        this.setDoorExist(true);
        if (passageDescription.toLowerCase().contains("archway")) {
            setDoor.setArchway(true);
        } else {
            setDoor.setArchway(false);
        }
        this.passageDoor = setDoor;
    }

    /**
     * Generates monster within passage section.
     */
    private void genMonster() {
        Monster generatedMonster = new Monster();

        this.setMonsterExist(true);
        generatedMonster.setType(rollD100());
        this.passageMonsters.add(generatedMonster);
    }

    /**
     * Sets whether monster exists within passage section or not.
     *
     * @param exist bool representation of whether monster should exists or not
     */
    public void setMonsterExist(boolean exist) {
        this.monsterExist = exist;
    }

    /**
     * Sets whether door exists within passage section or not.
     *
     * @param exist bool representation of whether door should exists or not
     */
    public void setDoorExist(boolean exist) {
        this.doorExist = exist;
    }

    /**
     * Sets whether chamber is ahead of passage section or not.
     *
     * @param ahead bool representation of whether chamber should be ahead of passage section or not
     */
    public void setChamberAhead(boolean ahead) {
        this.chamberAhead = ahead;
    }

    /**
     * Sets whether new door of chamber section.
     *
     * @param newDoor new door to replace the passage section's current door.
     */
    public void setDoor(Door newDoor) {
        this.genDoor(newDoor);
        this.updateDescription();
    }

    /**
     * Sets up passage chance table dependent on whether description is " " or not.
     *
     * @param description string description of passage section, if " " calls a randomized passage generation
     */
    private void setUpDescription(String description) {
        if (!description.equals(" ")) {
            this.setDescription(description);
        } else {
            this.setUpPassageTable();
            this.setDescription(" ");
        }
        this.genContents();

    }

    /**
     * Sets description of passage section by either rolling into table, or importing appropriate string.
     *
     * @param description string description of passage section, if " " rolls for random passage generation
     */
    private void setDescription(String description) {
        D20 die = new D20();
        int roll = 0;
        if (!description.equals(" ")) {
            this.tempDescription = description + "\n";
            this.passageDescription = description + "\n";
        } else {
            roll = die.roll();
            this.tempDescription = this.passageTable.get(roll);
            this.passageDescription = this.passageTable.get(roll);
        }
    }

    /**
     * Sets up randomely generated passage section descriptions tables dependent on roll.
     */
    private void setUpPassageTable() {
        this.passageTable.put(1, "passage goes straight for 10 ft\n");
        this.passageTable.put(2, "passage goes straight for 10 ft\n");
        this.passageTable.put(3, "passage ends in Door to a Chamber\n");
        this.passageTable.put(4, "passage ends in Door to a Chamber\n");
        this.passageTable.put(5, "passage ends in Door to a Chamber\n");
        this.passageTable.put(6, "archway (door) to right (main passage continues straight for 10 ft\n");
        this.passageTable.put(7, "archway (door) to right (main passage continues straight for 10 ft\n");
        this.passageTable.put(8, "archway (door) to left (main passage continues straight for 10 ft\n");
        this.passageTable.put(9, "archway (door) to left (main passage continues straight for 10 ft\n");
        this.passageTable.put(10, "passage turns to left and continues for 10 ft\n");
        this.passageTable.put(11, "passage turns to left and continues for 10 ft\n");
        this.passageTable.put(12, "passage turns to right and continues for 10 ft\n");
        this.passageTable.put(13, "passage turns to right and continues for 10 ft\n");
        this.passageTable.put(14, "passage ends in archway (door) to chamber\n");
        this.passageTable.put(15, "passage ends in archway (door) to chamber\n");
        this.passageTable.put(16, "passage ends in archway (door) to chamber\n");
        this.passageTable.put(17, "Stairs, (passage continues straight for 10 ft)\n");
        this.passageTable.put(18, "Dead End\n");
        this.passageTable.put(19, "Dead End\n");
        this.passageTable.put(20, "Wandering Monster (passage continues straight for 10 ft)\n");
    }

    /**
     * Adds monster imported from inputs of gui.
     *
     * @param indexNum number of monster type specified
     * @return 0-1, true of false dependent on if action has succeeded
     */
    public int addMonGui(int indexNum) {
        Monster newMonster = new Monster();
        if (indexNum < 1 || indexNum > 100) {
            return 0; /*Fail*/
        } else {
            newMonster.setType(indexNum);
            this.addMonster(newMonster);
            return 1;
        }
    }

    /**
     * Adds new monsters to passagesection.
     *
     * @param newMonster new monster within passage section
     */
    public void addMonster(Monster newMonster) {
        this.passageMonsters.add(newMonster);
        this.monsterExist = true;
        this.updateDescription();
    }

    /**
     * Removes monster imported from inputs of gui.
     *
     * @param selectedMonster specifies monster to remove
     * @return 0-1, true or false dependent on if action has succeeded
     */
    public int removeMonGui(String selectedMonster) {
        int monsIndex = 0;
        int monsNotFound = 0;
        int i;

        for (i = 0; i < passageMonsters.size(); i++) {
            if (selectedMonster.equals(passageMonsters.get(i).getDescription())) {
                monsIndex = i;
                break;
            } else {
                monsNotFound = 1;
            }
        }
        if (monsNotFound == 1) {
            return 0;
        } else {
            if (passageMonsters.size() > monsIndex) {
                this.removeMonster(monsIndex);
                return 1;
            } else {
                return 0;
            }
        }
    }

    /**
     * Removes requested monster from arraylist of monsters for chamber.
     *
     * @param indexNum index number of monster within arraylist to remove
     */
    public void removeMonster(int indexNum) {
        this.passageMonsters.remove(indexNum);
        if (passageMonsters.size() == 0) {
            this.monsterExist = false;
        }
        this.updateDescription();
    }

    /**
     * Adds treasure imported from inputs of gui.
     *
     * @param indexNum index of treasure type specified
     * @return 0-1, true of false dependent on if action has succeeded
     */
    public int addTreasGui(int indexNum) {
        Treasure newTreasure = new Treasure();
        if (indexNum < 1 || indexNum > 100) {
            return 0; /*Fail*/
        } else {
            newTreasure.setDescription(indexNum);
            newTreasure.setContainer(rollD20());
            this.addTreasure(newTreasure);
            return 1;
        }
    }

    /**
     * Adds new treasure to passagesection.
     *
     * @param newTreasure new treasure within passage section
     */
    public void addTreasure(Treasure newTreasure) {
        this.passageTreasures.add(newTreasure);
        this.treasureExist = true;
        this.updateDescription();
    }

    /**
     * Removes Treasure imported from inputs of gui.
     *
     * @param indexNum specified treasure index number
     * @return 0-1, true or false dependent on if action has succeeded
     */
    public int removeTreasGui(int indexNum) {
        if (passageTreasures.size() > indexNum) {
            this.removeTreasure(indexNum);
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * Removes treasure specified at index number for passage section.
     *
     * @param indexNum index number of treasure within arraylist to remove
     */
    public void removeTreasure(int indexNum) {
        this.passageTreasures.remove(indexNum);
        if (passageTreasures.size() == 0) {
            this.treasureExist = false;
        }
        this.updateDescription();
    }

    /**
     * Updates/sets current passage section door to newDoor.
     *
     * @param newDoor new door that is to replace current/null door field within passage section
     */
    public void updateDoor(Door newDoor) {
        this.passageDoor = newDoor;
        this.doorExist = true;
        this.updateDescription();
    }

    /**
     * Returns door of passage section.
     *
     * @return passageDoor door generated in passage section
     */
    public Door getDoor() {
        //returns the door that is in the passage section, if there is one
        return this.passageDoor;
    }

    /**
     * Returns all treasures of passage section.
     * @return passageTreasures treasures generated in passage section
     */
    public ArrayList<Treasure> getTreasures() {
        return this.passageTreasures;
    }

    /**
     * Returns monster of passage section.
     *
     * @param num specified monster
     * @return passageMonster monster generated in passage section
     */
    public Monster getMonster(int num) {
        //returns the monster that is in the passage section, if there is one
        return this.passageMonsters.get(num);
    }

    /**
     * Returns all monsters of passage section.
     *
     * @return passageMonster monster generated in passage section
     */
    public ArrayList<Monster> getMonsters() {
        return this.passageMonsters;
    }

    /**
     * Returns string description of monster in passage section.
     *
     * @return monsterDescrip description of monster within passage section
     */
    private String getMonsterDescrip() {
        String monsterDescrip = "";
        int i;

        if (this.passageMonsters.size() > 0) {
            monsterDescrip = monsterDescrip.concat("There is/are " + this.passageMonsters.size() + " potential monsters/types of monsters within the chamber.\n");
            for (i = 0; i < this.passageMonsters.size(); i++) {
                monsterDescrip = monsterDescrip.concat(indentString((i + 1) + " The monster is/are a " + this.passageMonsters.get(i).getDescription() + "\n"));
                monsterDescrip = monsterDescrip.concat(indentString("The amount of monsters potentially spawning is: " + this.passageMonsters.get(i).getMinNum() + " to " + this.passageMonsters.get(i).getMaxNum() + "\n"));
            }
        }
        return monsterDescrip;
    }

    /**
     * Returns the string description of the treasure of chamber.
     *
     * @return treasureDescrip string description of treasures of chamber
     */
    private String getTreasureDescrip() {
        String protectStatus;
        String treasureDescrip = "";
        int i;

        if (this.passageTreasures.size() > 0) {
            treasureDescrip = treasureDescrip.concat("There is/are " + this.passageTreasures.size() + " potential treasures/types of treasures within the chamber.\n");
            for (i = 0; i < this.passageTreasures.size(); i++) {
                try { //Try statement checks whether generatedReward.getProtection() is null or not
                    if (this.passageTreasures.get(i).getProtection() != null) {
                        protectStatus = this.passageTreasures.get(i).getProtection();
                    } else {
                        protectStatus = "nothing";
                    }
                } catch (NotProtectedException e) { //Catch statement catches any NotProtectedExceptions
                    protectStatus = "nothing";
                }
                treasureDescrip = treasureDescrip.concat(indentString("The treasure is contained in " + this.passageTreasures.get(i).getContainer() + " and holds " + this.passageTreasures.get(i).getDescription() + ".\n"));
                treasureDescrip = treasureDescrip.concat(indentString("The treasure is guarded by " + protectStatus + ".\n"));
            }
        }
        return treasureDescrip;
    }

    /**
     * Returns string description of passage section.
     *
     * @return passageDescription description of passage section
     */
    public String getDescription() {
        return this.passageDescription;
    }

    /**
     * Returns whether monster exists within passage section or not.
     *
     * @return monsterExist bool representation of whether monster exists or not
     */
    public boolean getMonsterExist() {
        return this.monsterExist;
    }

    /**
     * Returns whether door exists within passage section or not.
     *
     * @return doorExist bool representation of whether door exists or not
     */
    public boolean getDoorExist() {
        return this.doorExist;
    }

    /**
     * Returns whether chamber is ahead of passage section or not.
     *
     * @return chamberAhead bool representation of whether chamber is ahead of passage section or not
     */
    public boolean getChamberAhead() {
        return this.chamberAhead;
    }

    /**
     * Returns whether passage section is a dead end or not.
     *
     * @return deadEnd bool representation of whether passage section is dead end or not
     */
    public boolean getDeadEnd() {
        return this.deadEnd;
    }

    /**
     * Indents selected string.
     *
     * @param string string taken in to indent
     * @return newString indented string
     */
    private String indentString(String string) {
        String newString;
        newString = "  " + string;
        return newString;
    }

    /**
     * Returns a random die roll from 1-20.
     *
     * @return die.roll() randomized number from 1-20
     */
    private int rollD20() {
        D20 die = new D20();

        return die.roll();
    }

    /**
     * Returns a die roll of 1-100.
     *
     * @return D100.roll() randomized number from 1-20
     */
    private int rollD100() {
        Percentile d100 = new Percentile();
        return d100.roll();
    }
}
