package epark;

import dnd.die.D20;
import dnd.models.ChamberContents;
import dnd.models.ChamberShape;
import dnd.models.DnDElement;
import dnd.models.Monster;
import dnd.models.Stairs;
import dnd.models.Trap;
import dnd.models.Treasure;
import java.util.ArrayList;

import epark.Chamber;
import epark.Door;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ChamberTest {
    ChamberShape testShape;
    ChamberContents testContents;
    Monster testMonster;
    Door testDoor;
    Treasure testTreasure;

    public ChamberTest() {
    }

    @Before
    public void setUp() {
        testShape = ChamberShape.selectChamberShape(rollD20());
        testShape.setNumExits();
        testContents = new ChamberContents();
        testMonster = new Monster();
        testDoor = new Door();
        testTreasure = new Treasure();
    }

    private int rollD20() {
        D20 die = new D20();

        return die.roll();
    }

    @Test
    public void testConstructor() {
        System.out.println ("constructor");
        Chamber testChamber = new Chamber();
        assertTrue(!testChamber.getDescription().isEmpty());
    }

    @Test
    public void testConstructorTwo() {
        System.out.println ("constructor two");
        Chamber testChamber = new Chamber(testShape, testContents);
        assertTrue(testChamber.getDescription().contains(testContents.getDescription()));
    }

    @Test
    public void testSetShape() {
        System.out.println ("setShape");
        Chamber testChamber = new Chamber();
        int shapeExits;
        int chambExits;

        testShape.setNumExits(4);
        shapeExits = testShape.getNumExits();
        testChamber.setShape(testShape);
        chambExits = testChamber.getDoors().size();
        assertEquals(shapeExits, chambExits);
    }

    @Test
    public void testSetDoor(){
        System.out.println ("setDoor");
        Chamber testChamb = new Chamber();
        int i;

        testChamb.setDoor(testDoor);
        for (i = 0; i < testChamb.getDoors().size(); i++) {
            if (testChamb.getDoor(i) == testDoor) {
                assertEquals(testChamb.getDoor(i), testDoor);
            }
        }
    }

    @Test
    public void testAddMonster() {
        System.out.println ("addMonster");
        Chamber testChamb = new Chamber();
        int i;

        testChamb.addMonster(testMonster);
        for (i = 0; i < testChamb.getMonsters().size(); i++) {
            if (testChamb.getMonsters().get(i) == testMonster) {
                assertEquals(testChamb.getMonsters().get(i), testMonster);
            }
        }
    }

    @Test
    public void testAddTreasure() {
        System.out.println ("addTreasure");
        Chamber testChamb = new Chamber();
        int i;

        testChamb.addTreasure(testTreasure);
        for (i = 0; i < testChamb.getTreasureList().size(); i++) {
            if (testChamb.getTreasureList().get(i) == testTreasure) {
                assertEquals(testChamb.getTreasureList().get(i), testTreasure);
            }
        }
    }

    @Test
    public void testGetDoors() {
        System.out.println ("getDoors");
        Chamber testChamb = new Chamber();
        ArrayList<Door> testDoors = new ArrayList<Door>();
        int i;

        testChamb.getDoors().clear();
        for (i = 0; i < 4; i++) {
            Door testDoor = new Door();
            testDoors.add(testDoor);
            testChamb.setDoor(testDoor);
        }
        assertEquals(testChamb.getDoors(), testDoors);
    }

    @Test
    public void testGetMonsters() {
        System.out.println ("getMonsters");
        Chamber testChamb = new Chamber();
        ArrayList<Monster> testMonsters = new ArrayList<Monster>();
        int i;

        testChamb.getMonsters().clear();
        for (i = 0; i < 4; i++) {
            Monster testMons = new Monster();
            testMonsters.add(testMons);
            testChamb.addMonster(testMons);
        }
        assertEquals(testChamb.getMonsters(), testMonsters);
    }

    @Test
    public void testGetTreasureList() {
        System.out.println ("getTreasureList");
        Chamber testChamb = new Chamber();
        ArrayList<Treasure> testTreasures = new ArrayList<Treasure>();
        int i;

        testChamb.getTreasureList().clear();

        for (i = 0; i < 4; i++) {
            Treasure testTreas = new Treasure();
            testTreasures.add(testTreas);
            testChamb.addTreasure(testTreas);
        }
        assertEquals(testChamb.getTreasureList(), testTreasures);
    }

    @Test
    public void testGetDescription() {
        System.out.println ("getDescription");
        ChamberContents testContent = new ChamberContents();
        Chamber testChamb = new Chamber(testShape, testContent);
        String testDescrip;

        testDescrip = testChamb.getDescription();
        assertTrue(testChamb.getDescription().contains(testContent.getDescription()));
    }

    @Test
    public void testGetDoor() {
        System.out.println ("getDoor");
        Chamber testChamb = new Chamber();

        testChamb.getDoors().clear();
        testChamb.setDoor(testDoor);
        assertEquals(testChamb.getDoor(0),testDoor);
    }

    /* set up similar to the sample in PassageTest.java */

    
}