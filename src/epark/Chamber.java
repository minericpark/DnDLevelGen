package epark;

import db.DBConnection;
import db.DBMonster;
import dnd.die.D20;
import dnd.die.Percentile;
import dnd.exceptions.NotProtectedException;
import dnd.exceptions.UnusualShapeException;
import dnd.models.ChamberContents;
import dnd.models.ChamberShape;
import dnd.models.Stairs;
import dnd.models.Trap;
import dnd.models.Treasure;
import dnd.models.Exit;

import java.util.ArrayList;
import java.util.Random;

public class Chamber extends epark.Space implements java.io.Serializable {

    /**
     * Represents the chamber contents of the chamber.
     */
    private ChamberContents myContents;
    /**
     * Represents the chamber shape + size of the chamber.
     */
    private ChamberShape mySize;
    /**
     * Represents the stairs within chamber (if they exist).
     */
    private Stairs chambStairs;
    /**
     * Represents the trap within chamber (if they exist).
     */
    private Trap chambTrap;
    /**
     * Represents the monsters within chamber (if they exist).
     */
    private ArrayList<DBMonster> chambMonsters;
    /**
     * Represents the treasures within chamber (if they exist).
     */
    private ArrayList<Treasure> chambTreasures;
    /**
     * Represents the exits within chamber (if they exist).
     */
    private ArrayList<Exit> chambExits;
    /**
     * Represents the doors within chamber (if they exist).
     */
    private ArrayList<Door> chambDoors;
    /**
     * Represents the string description of the chamber.
     */
    private String chambDescrip;
    /**
     * Represents the unusual status of the chamber shape.
     */
    private int unusualStatus;
    /**
     * Represents the length of chamber.
     */
    private int chambLength;
    /**
     * Represents the width of the chamber.
     */
    private int chambWidth;
    /**
     * Represents the ID of the chamber.
     */
    private int chambID;

    /**
     * Main constructor for chamber without chamber shape and chamber contents import.
     * Initializes all required instances and calls all setup methods.
     */
    public Chamber() {
        /*Set traits of chamber here e.g. setShape, etc.*/
        this.initNewSizeShape();
        this.initChamber();
        //numExits = 0;

        this.genSize();
        this.genContents();
        this.genDoors();
        this.setUpDescription();
    }

    /**
     * Main constructor for chamber with chamber shape and chamber contents import.
     * Initializes all required instances and calls all setup methods.
     *
     * @param theShape    chamber shape utilized to generate chamber
     * @param theContents chamber contents utilized to generate chamber
     */
    public Chamber(ChamberShape theShape, ChamberContents theContents) {
        this.mySize = theShape;
        this.myContents = theContents;
        this.initChamber();
        //numExits = 0;

        this.genContents();
        this.genSize();
        this.genDoors();
        this.setUpDescription();
    }

    /**
     * Initializes new instances for Chamber's ChamberContent and ChamberShape.
     */
    private void initNewSizeShape() {

        this.mySize = ChamberShape.selectChamberShape(rollD20());
        this.myContents = new ChamberContents();
        this.mySize.setNumExits();
        this.myContents.setDescription(rollD20());
    }

    /**
     * Initializes additional instances for Chamber.
     */
    private void initChamber() {

        Random rand = new Random();
        this.chambDoors = new ArrayList<Door>();
        this.chambExits = new ArrayList<Exit>();
        this.chambTreasures = new ArrayList<Treasure>();
        this.chambMonsters = new ArrayList<DBMonster>();
        this.chambID = rand.nextInt(1000);
    }

    /**
     * Generates size of chamber + number of exits.
     */
    private void genSize() {

        try { //Try and catch statement catches UnusualShapeException and determines if shape is unusual or not (to gather width and length)
            if (mySize.getLength() != 0) {
                this.chambLength = mySize.getLength();
                this.chambWidth = mySize.getWidth();
            } else {
                this.unusualStatus = 1;
            }
        } catch (UnusualShapeException e) {
            this.unusualStatus = 1;
        }

        this.chambExits = mySize.getExits(); //Initialize chamberExit arraylist with getExits method
        //numExits = chambExits.size();
        //System.out.println("The chamber is shaped like a " + mySize.getShape());
    }

    /**
     * Generates contents of chamber dependent on string description of contents rendered.
     * Calls an appropriate method for every string matched.
     */
    private void genContents() {

        /*Determine how to see if myContents is either monster, etc.*/
        if (this.myContents.getDescription().contains("monster")) {
            this.genMonster();
        }
        if (this.myContents.getDescription().contains("treasure")) {
            this.genTreasure();
        }
        if (this.myContents.getDescription().contains("stairs")) {
            this.genStairs();
        }
        if (this.myContents.getDescription().contains("trap")) {
            this.genTrap();
        }

    }

    /**
     * Generates doors of chamber dependent on number of chamber exits.
     */
    private void genDoors() {
        int i;

        this.chambDoors.clear();

        if (chambExits.size() > 0) {
            this.allExitsToDoor();
        } else {
            this.genRandomExit();
            this.exitToDoor(0);
        }
    }

    /**
     * Generates a random exit for the Chamber.
     */
    private void genRandomExit() {
        Exit randExit = new Exit();

        this.chambExits.add(randExit);
    }

    /**
     * Converts an exit to a Door.
     * @param exitNum exit
     */
    private void exitToDoor(int exitNum) {

        if (exitNum < chambExits.size() && exitNum > 0) {
            Door temp = new Door(chambExits.get(exitNum));
            this.setDoor(temp);
        }
    }

    /**
     * Converts all exits within Chamber to Doors.
     */
    private void allExitsToDoor() {
        int i;

        for (i = 0; i < chambExits.size(); i++) {
            Door temp;
            if (i <= chambExits.size()) {
                temp = new Door(chambExits.get(i));
            } else {
                temp = new Door();
            }
            this.setDoor(temp);
        }
    }

    /**
     * Generates treasure of chamber and adds into arraylist of treasures.
     */
    private void genTreasure() {
        Treasure generatedReward = new Treasure();

        generatedReward.setContainer(rollD20()); //Generates container of treasure randomly
        generatedReward.setDescription(rollD100()); //Generates description of treasure randomly

        this.addTreasure(generatedReward);
    }

    /**
     * Generates monster of chamber and adds into arraylist of monsters.
     */
    private void genMonster() {
        DBConnection mainConnection = new DBConnection();
        DBMonster generatedMonster;
        generatedMonster = mainConnection.randMonster();
        this.addMonster(generatedMonster);
    }

    /**
     * Generates stairs of chamber and assigns it to instance chambStairs.
     */
    private void genStairs() {
        Stairs generatedStairs = new Stairs();

        generatedStairs.setType(rollD20()); //Generates container of treasure randomly
        this.chambStairs = generatedStairs;
    }

    /**
     * Generates traps of chamber and assigns it to instance chambTraps.
     */
    private void genTrap() {
        Trap generatedTrap = new Trap();

        generatedTrap.setDescription(rollD20()); //Generates container of treasure randomly
        this.chambTrap = generatedTrap;
    }

    /**
     * Sets up complete description of chamber by concatenating generated strings.
     */
    private void setUpDescription() {
        this.chambDescrip = this.getSizeDescrip();
        this.chambDescrip = this.chambDescrip.concat(getContents());
        this.chambDescrip = this.chambDescrip.concat(getMiniDoorDescrip());
    }

    /**
     * Sets imported shape to chamber's shape.
     *
     * @param theShape shape imported to set chamber's shape as
     */
    public void setShape(ChamberShape theShape) {
        this.mySize = theShape;
        this.genSize();
        this.genDoors();
        this.setUpDescription();
    }

    /**
     * Adds imported door into arraylist of doors for chamber.
     *
     * @param newDoor new door imported to add into list of doors of chamber
     */
    @Override
    public void setDoor(Door newDoor) {
        //should add a door connection to this room
        newDoor.setSpace(this);
        this.chambDoors.add(newDoor);
        this.setUpDescription();
    }

    /**
     * Adds monster imported from inputs of gui.
     *
     * @param monsterName name of monster
     * @return 0-1, true of false dependent on if action has succeeded
     */
    public int addMonGui(String monsterName) {
        DBConnection mainConnection = new DBConnection();
        DBMonster newMonster = new DBMonster();
        if (!monsterName.equals("null")) {
            newMonster = mainConnection.findMonster(monsterName);
            this.addMonster(newMonster);
            this.updateDescription();
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * Adds imported monster into arraylist of monsters for chamber.
     *
     * @param theMonster new monster imported to add into list of monsters of chamber
     */
    public void addMonster(DBMonster theMonster) {
        this.chambMonsters.add(theMonster);
    }

    /**
     * Removes monster imported from inputs of gui.
     *
     * @param selectedMonster specified monster
     * @return 0-1, true or false dependent on if action has succeeded
     */
    public int removeMonGui(String selectedMonster) {
        int i;
        int monsIndex = 0;
        int monsNotFound = 0;
        for (i = 0; i < chambMonsters.size(); i++) {
            if (selectedMonster.equals(chambMonsters.get(i).getName())) {
                monsNotFound = 0;
                monsIndex = i;
                break;
            } else {
                monsNotFound = 1;
            }
        }
        if (monsNotFound == 1) {
            return 0;
        } else {
            if (chambMonsters.size() > monsIndex) {
                this.removeMonster(monsIndex);
                this.updateDescription();
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
        this.chambMonsters.remove(indexNum);
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
            this.updateDescription();
            return 1;
        }
    }

    /**
     * Adds imported treasure into arraylist of treasures for chamber.
     *
     * @param theTreasure new treasure imported to add into list of treasures of chamber
     */
    public void addTreasure(Treasure theTreasure) {
        this.chambTreasures.add(theTreasure);
    }

    /**
     * Removes Treasure imported from inputs of gui.
     *
     * @param indexNum specified treasure index number
     * @return 0-1, true or false dependent on if action has succeeded
     */
    public int removeTreasGui(int indexNum) {
        if (chambTreasures.size() > indexNum) {
            this.removeTreasure(indexNum);
            this.updateDescription();
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * Removes treasure specified at index number for chamber.
     *
     * @param indexNum index number of treasure within arraylist to remove
     */
    public void removeTreasure(int indexNum) {
        this.chambTreasures.remove(indexNum);
    }

    /**
     * Returns the arraylist of doors of chamber.
     *
     * @return chambDoors instance arraylist of all doors within chamber
     */
    public ArrayList<Door> getDoors() {
        return this.chambDoors;
    }

    /**
     * Returns the arraylist of monsters of chamber.
     *
     * @return chambMonsters instance arraylist of all monsters within chamber
     */
    public ArrayList<DBMonster> getMonsters() {
        return this.chambMonsters;
    }

    /**
     * Returns the arraylist of treasures of chamber.
     *
     * @return chambTreasures instance arraylist of all treasures within chamber
     */
    public ArrayList<Treasure> getTreasureList() {
        return this.chambTreasures;
    }

    /**
     * Returns the complete string description of chamber.
     *
     * @return chambDescrip complete description of entire chamber
     */
    @Override
    public String getDescription() {
        return this.chambDescrip;
    }

    /**
     * Returns the string description of the size and shape of chamber.
     *
     * @return sizeDescrip string description of size and shape of chamber
     */
    private String getSizeDescrip() {
        String sizeDescrip;

        sizeDescrip = "The chamber is shaped like a " + this.mySize.getShape() + "\n";

        if (this.unusualStatus == 1) { //If unusual, do not print chamber length and chamber width
            sizeDescrip = sizeDescrip.concat("The shape of the chamber is unusual, thus length and width cannot be provided.\n");
            //System.out.println("The shape of the chamber is unusual, thus length and width cannot be provided.");
        } else {
            sizeDescrip = sizeDescrip.concat("It is " + this.chambLength + " ft long, and " + this.chambWidth + " ft wide.\n");
            //System.out.println("It is " + chambLength + " ft long, and " + chambWidth + " ft wide.");
        }
        sizeDescrip = sizeDescrip.concat("The area is " + this.mySize.getArea() + " square ft.\n");
        //System.out.println("The area is " + mySize.getArea() + " square ft.");

        return sizeDescrip;
    }

    /**
     * Returns the string description of the contents of chamber.
     *
     * @return contentDescrip string description of contents of chamber
     */
    private String getContents() {
        String contentDescrip;

        contentDescrip = "";
        /*Determine how to see if myContents is either monster, etc.*/
        if (this.chambMonsters.size() > 0) {
            contentDescrip = contentDescrip.concat(getMonsterDescrip());
        }
        if (this.chambTreasures.size() > 0) {
            contentDescrip = contentDescrip.concat(getTreasureDescrip());
        }
        if (this.myContents.getDescription().contains("stairs")) {
            contentDescrip = contentDescrip.concat(getStairsDescrip());
        }
        if (this.myContents.getDescription().contains("trap")) {
            contentDescrip = contentDescrip.concat(getTrapDescrip());
        }

        return contentDescrip;
    }

    /**
     * Returns the string description of the doors of chamber.
     *
     * @return doorDescrip string description of doors of chamber
     */
    private String getDoorDescrip() {
        int i;
        String doorDescrip = "";

        if (this.chambDoors.size() > 0) {
            doorDescrip = doorDescrip.concat("There is/are " + this.chambDoors.size() + " doors within the chamber.\n");
            for (i = 0; i < this.chambDoors.size(); i++) {
                doorDescrip = doorDescrip.concat(indentString("Door " + (i + 1) + " ID: " + this.chambDoors.get(i).getDescription()));
                //System.out.println("Exit " + exitNo + " is at the " + chambExits.get(i).getLocation() + ", " + chambExits.get(i).getDirection() + ".");
            }
        }

        return doorDescrip;
    }

    /**
     * Returns a mini description of the doors of chamber.
     * @return doorDescrip mini string description of doors of chamber
     */
    private String getMiniDoorDescrip() {
        int i;
        String doorDescrip = "";

        if (this.chambDoors.size() > 0) {
            doorDescrip = doorDescrip.concat("There is/are " + this.chambDoors.size() + " doors within the chamber.\n");
            for (i = 0; i < chambDoors.size(); i++) {
                doorDescrip = doorDescrip.concat(indentString("Door " + (i + 1) + "- ID: " + this.chambDoors.get(i).getDoorID() + "\n"));
            }
        }
        return doorDescrip;
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

        if (this.chambTreasures.size() > 0) {
            treasureDescrip = treasureDescrip.concat("There is/are " + this.chambTreasures.size() + " potential treasures/types of treasures within the chamber.\n");
            for (i = 0; i < this.chambTreasures.size(); i++) {
                try { //Try statement checks whether generatedReward.getProtection() is null or not
                    if (this.chambTreasures.get(i).getProtection() != null) {
                        protectStatus = this.chambTreasures.get(i).getProtection();
                    } else {
                        protectStatus = "nothing";
                    }
                } catch (NotProtectedException e) { //Catch statement catches any NotProtectedExceptions
                    protectStatus = "nothing";
                }
                treasureDescrip = treasureDescrip.concat(indentString((i + 1) + ". The treasure is contained in " + this.chambTreasures.get(i).getContainer() + " and holds " + this.chambTreasures.get(i).getDescription() + ".\n"));
                treasureDescrip = treasureDescrip.concat(indentString("It is guarded by " + protectStatus + ".\n"));
            }
        }
        return treasureDescrip;
    }

    /**
     * Returns the string description of the monster of chamber.
     *
     * @return monsterDescrip string description of monster of chamber
     */
    private String getMonsterDescrip() {
        String monsterDescrip = "";
        int i;

        if (this.chambMonsters.size() > 0) {
            monsterDescrip = monsterDescrip.concat("There is/are " + this.chambMonsters.size() + " potential monsters/types of monsters within the chamber.\n");
            for (i = 0; i < this.chambMonsters.size(); i++) {
                monsterDescrip = monsterDescrip.concat(indentString((i + 1) + ". The monster is/are a " + this.chambMonsters.get(i).getName() + "\n"));
                monsterDescrip = monsterDescrip.concat(indentString("Description: " + this.chambMonsters.get(i).getDescription() + "\n"));
                monsterDescrip = monsterDescrip.concat(indentString("The amount of monsters of this type potentially spawning is: " + this.chambMonsters.get(i).getLower() + " to " + this.chambMonsters.get(i).getUpper() + "\n"));
            }
        }
        return monsterDescrip;
    }

    /**
     * Returns the string description of the stairs of chamber.
     *
     * @return stairDescrip string description of stairs of chamber
     */
    private String getStairsDescrip() {
        String stairDescrip;

        stairDescrip = "The stairs is/are a " + chambStairs.getDescription() + "\n";

        return stairDescrip;
    }

    /**
     * Returns the string description of the trap of chamber.
     *
     * @return trapDescrip string description of trap of chamber
     */
    private String getTrapDescrip() {
        String trapDescrip;

        trapDescrip = "The trap is a " + this.chambTrap.getDescription().trim() + "\n";

        return trapDescrip;
    }

    /**
     * Returns the door of number i of chamber.
     *
     * @param i          index of door being found and returned
     * @return chambDoors.get(i) command which calls door of index i within arraylsit chambDoors
     */
    public Door getDoor(int i) {
        return this.chambDoors.get(i);
    }

    /**
     * Returns the ID number of chamber.
     *
     * @return chambID ID integer representative of chamber.
     */
    public int getID() {
        return this.chambID;
    }

    /**
     * Updates complete description of chamber by concatenating generated strings.
     */
    private void updateDescription() {
        this.setUpDescription();
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
