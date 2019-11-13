package epark;

import dnd.die.D20;
import dnd.die.D10;
import dnd.models.Exit;
import dnd.models.Trap;

import java.util.ArrayList;
import java.util.Random;

public class Door {

    /**
     * Represents the description of the door.
     */
    private String doorDescription;
    /**
     * Represents the description of the trap.
     */
    private String trapDescription;
    /**
     * Represents the description of the direction of the door.
     */
    private String doorDirection;
    /**
     * Represents the description of the location of the door.
     */
    private String doorLocation;
    /**
     * Represents the spaces connected to door.
     */
    private ArrayList<Space> connectedSpaces;
    /**
     * Represents the trap connected to door (if it exists).
     */
    private Trap doorTrap;
    /**
     * Represents the boolean on whether door is trapped or not: trapped if true.
     */
    private boolean doorTrapped;
    /**
     * Represents the boolean on whether door is locked or not: locked if true.
     */
    private boolean doorLocked;
    /**
     * Represents the boolean on whether the door is closed or not: closed if true.
     */
    private boolean doorClosed;
    /**
     * Represents the boolean on whether the door is an archway: archway if true.
     */
    private boolean doorArch;
    /**
     * Represents the ID of the door.
     */
    private int doorID;

    /**
     * Main constructor for door without an exit import.
     * Initializes all required instances and calls setUpDoor method.
     */
    public Door() {
        connectedSpaces = new ArrayList<Space>();
        Exit newExit = new Exit();
        setUpDoor(newExit);
    }

    /**
     * Main constructor for door with an exit import.
     * Initializes all required instances and calls setUpDoor method.
     *
     * @param theExit is imported exit
     */
    public Door(Exit theExit) {
        //sets up the door based on the Exit from the tables
        connectedSpaces = new ArrayList<Space>();
        setUpDoor(theExit);
    }

    /**
     * Updates the door's description.
     */
    private void updateDescription() {
        this.setDescription();
    }

    /**
     * Creates trap with a roll (or null) for door, and sets trap to instance doorTrap.
     *
     * @param roll takes in roll to call random trap
     */
    private void createTrap(int... roll) {

        Trap newTrap = new Trap();
        if (roll.length > 0) {
            newTrap.setDescription(roll[0]);
        } else {
            newTrap.setDescription(rollD20());
        }
        this.doorTrap = newTrap;
        this.trapDescription = doorTrap.getDescription();
    }

    /**
     * Sets up door with exit, uses chance variables whether door is archway, locked, or open.
     *
     * @param exit is imported exit
     */
    private void setUpDoor(Exit exit) {
        /*Take description of given exit, store into here*/
        /*Else randomely generate description*/
        Random random = new Random();
        D20 trapDie = new D20();
        D10 archDie = new D10();

        this.setLocation(exit);
        this.setOpen(random.nextBoolean());
        if (doorClosed) {
            this.setLocked(random.nextBoolean());
        }
        if (trapDie.roll() == 1) {
            this.setTrapped(true);
        }
        if (archDie.roll() == 1) {
            this.setArchway(true);
        }
        this.doorID = random.nextInt(1000);
        this.setDescription();
    }

    /**
     * Takes in exit and assigns it to the instances.
     * @param exit exit to assign
     */
    private void setLocation(Exit exit) {
        this.doorDirection = exit.getDirection();
        this.doorLocation = exit.getLocation();
    }

    /**
     * Creates description of door dependent on type, e.g. archway, closed, open, etc.
     * Adds trap description if door is trapped.
     */
    private void setDescription() {
        /*Door is arched or not*/
        /*Door is closed or open*/
        /*Door is trapped or not*/

        if (doorLocked) {
            this.doorDescription = this.doorID + " - There is a closed, locked door at the " + this.doorLocation + ", " + this.doorDirection + ".\n";
        } else if (!doorClosed) {
            this.doorDescription = this.doorID + " - There is an open, unlocked door at the " + this.doorLocation + ", " + this.doorDirection + ".\n";
        } else if (doorArch) {
            this.doorDescription = this.doorID + " - There is an archway at the " + this.doorLocation + ", " + this.doorDirection + ".\n";
        } else {
            this.doorDescription = this.doorID + " - There is a closed unlocked door at the " + this.doorLocation + ", " + this.doorDirection + ".\n";
        }
        if (doorTrapped) {
            this.doorDescription = this.doorDescription.concat(indentString("There is a trap: " + this.trapDescription.trim() + "\n"));
        }
    }

    /**
     * Sets spaces that door connects between.
     *
     * @param spaceOne first space to connect door with
     * @param spaceTwo second space to connect door with
     */
    public void setSpaces(Space spaceOne, Space spaceTwo) {
        //identifies the two spaces with the door
        // this method should also call the addDoor method from Space
        this.connectedSpaces.add(spaceOne);
        this.connectedSpaces.add(spaceTwo);
    }

    /**
     * Sets a single space that door connects to.
     *
     * @param newSpace first space to connect door with
     */
    public void setSpace(Space newSpace) {
        this.connectedSpaces.add(newSpace);
    }

    /**
     * Sets whether door is trapped or not using the flag bool, or takes in roll when true.
     *
     * @param flag decides whether trap should be created or not
     * @param roll int for trap type, or not required
     */
    public void setTrapped(boolean flag, int... roll) {
        // true == trapped.  Trap must be rolled if no integer is given
        if (flag) {
            /*Door is trapped*/
            this.doorTrapped = true;
            this.createTrap(roll);
        } else {
            this.doorTrapped = false;
        }
        this.updateDescription();
    }

    /**
     * Sets whether door is open or not using the flag bool.
     *
     * @param flag that decides whether door should be open or not
     */
    public void setOpen(boolean flag) {
        //true == open
        if (!doorArch) {
            if (flag) {
                /*unlock door*/
                this.doorClosed = false;
                this.doorLocked = false;
            } else {
                /*Leave door locked*/
                this.doorClosed = true;
                this.doorLocked = true;
            }
        }
        this.updateDescription();
    }

    /**
     * Sets whether door is archway or not using the flag bool.
     *
     * @param flag that decides whether door should be archway or not
     */
    public void setArchway(boolean flag) {
        //true == is archway
        if (flag) {
            /*archway*/
            this.doorArch = true;
            this.doorClosed = false;
            this.doorLocked = false;
            this.doorTrapped = false;
        } else {
            /*Not archway*/
            this.doorArch = false;
        }
        this.updateDescription();
    }

    /**
     * Sets whether door is locked or not using the flag bool.
     *
     * @param flag that decides whether door should be locked or not
     */
    public void setLocked(boolean flag) {
        if (!doorArch) {
            if (flag) {
                this.doorLocked = true;
                this.doorClosed = true;
            } else {
                this.doorLocked = false;
            }
        }
        this.updateDescription();
    }

    /**
     * Returns whether door is trapped or not.
     *
     * @return doorTrapped bool representation of whether door is trapped or not
     */
    public boolean isTrapped() {
        return this.doorTrapped;
    }

    /**
     * Returns whether door is open or not.
     *
     * @return doorClosed bool representation of whether door is closed or not
     */
    public boolean isOpen() {
        return !this.doorClosed;
    }

    /**
     * Returns whether door is archway or not.
     *
     * @return doorArch bool representation of whether door is archway or not
     */
    public boolean isArchway() {
        return this.doorArch;
    }

    /**
     * Returns whether door is locked or not.
     *
     * @return doorLocked bool representation of whether door is locked or not
     */
    public boolean isLocked() {
        return this.doorLocked;
    }

    /**
     * Returns door's trap description.
     *
     * @return trapDescription string description of trap
     */
    public String getTrapDescription() {
        return this.trapDescription;
    }

    /**
     * Returns door's connected spaces.
     *
     * @return connectedSpaces arraylist of all spaces connected to door
     */
    public ArrayList<Space> getSpaces() {
        //returns the two spaces that are connected by the door
        return this.connectedSpaces;
    }

    /**
     * Returns door's main description.
     *
     * @return doorDescription string description of door
     */
    public String getDescription() {
        return this.doorDescription;
    }

    /**
     * Returns door's ID number.
     *
     * @return doorID integer representation of door
     */
    public int getDoorID() {
        return this.doorID;
    }

    /**
     * Indents selected string.
     *
     * @param string string taken in to indent
     * @return newString indented string
     */
    private String indentString(String string) {

        String newString;
        newString = "   " + string;
        return newString;
    }

    /**
     * Returns a random die roll from 20.
     *
     * @return die.roll() randomized number from 1-20
     */
    private int rollD20() {

        D20 die = new D20();
        return die.roll();
    }
}
