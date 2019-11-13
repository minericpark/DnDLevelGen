package epark;

import dnd.models.Exit;
import dnd.models.Monster;
import dnd.models.Stairs;
import java.util.ArrayList;

import dnd.models.Treasure;
import epark.Door;
import epark.Passage;
import epark.PassageSection;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;


public class PassageTest {
    //you don't have to use instance variables but it is easier
    // in many cases to have them and use them in each test
    private Passage testPass;
    private Passage testPassTwo;
    private PassageSection testSec;
    private PassageSection testSecTwo;
    private Monster testMonster;
    private Door testDoor;
    private Treasure testTreasure;

    public PassageTest() {
    }

    @Before
    public void setUp(){
        testPass = new Passage();
        testPassTwo = new Passage();
        testSec = new PassageSection();
        testSecTwo = new PassageSection();
        testMonster = new Monster();
        testDoor = new Door();
        testTreasure = new Treasure();
        //set up any instance variables here so that they have fresh values for every test
    }

    @Test
    public void testConstructor()
    {
        System.out.println ("constructor");
        assertTrue(testPass.getDescription().isEmpty());
    }

    @Test
    public void testSetDoor() {
        System.out.println ("setDoor");
        int i;

        testPass.setDoor(testDoor);
        for (i = 0; i < testPass.getDoors().size(); i++) {
            if (testPass.getDoor(i) == testDoor) {
                assertEquals(testPass.getDoor(i), testDoor);
            }
        }
    }

    @Test
    public void testAddMonster() {
        System.out.println ("addMonster");
        int i = 0;

        testPass.getThePassage().clear();
        testPass.addPassageSection(testSec);
        testPass.addMonster(testMonster, i);
        assertEquals(testSec.getMonster(), testPass.getMonster(i));
    }

    @Test
    public void testAddPassageSection() {
        System.out.println ("addPassageSection");
        int i = 0;

        testPass.getThePassage().clear();
        testPass.addPassageSection(testSec);
        assertEquals(testPass.getThePassage().get(i),testSec);
    }

    @Test
    public void testGetDescription() {
        System.out.println ("getDescription");
        String testDescrip;

        testDescrip = testPass.getDescription();
        assertTrue(testPass.getDescription().contains(testDescrip));
    }

    @Test
    public void testGetDoorExist() {
        System.out.println ("getDoorExist");

        testPass.getThePassage().clear();
        testSec.setDoor(testDoor);
        testPass.addPassageSection(testSec);
        assertTrue(testPass.getDoorExist());
    }

    @Test
    public void testGetDoors() {
        System.out.println ("getDoors");
        ArrayList<Door> testDoors = new ArrayList<Door>();
        int i;

        testPass.getThePassage().clear();
        testPass.getDoors().clear();
        for (i = 0; i < 4; i++) {
            Door testDoor = new Door();
            testSec = new PassageSection();
            testDoors.add(testDoor);
            testSec.setDoor(testDoor);
            testPass.addPassageSection(testSec);
        }
        assertEquals(testPass.getDoors(), testDoors);
    }

    @Test
    public void testGetDoor() {
        System.out.println ("getDoor");

        testPass.getThePassage().clear();
        testPass.getDoors().clear();
        testSec.setDoor(testDoor);
        testPass.addPassageSection(testSec);
        assertEquals(testPass.getDoor(0),testDoor);
    }

    @Test
    public void testGetMonster() {
        System.out.println ("getMonster");

        testPass.getThePassage().clear();
        testPass.getDoors().clear();
        testSec.updateMonster(testMonster);
        testPass.addPassageSection(testSec);
        assertEquals(testPass.getMonster(0), testMonster);
    }

    @Test
    public void testGetThePassage() {
        System.out.println ("getThePassage");
        ArrayList<PassageSection> testPassage = new ArrayList<PassageSection>();

        testPass.getThePassage().clear();
        testPass.addPassageSection(testSec);
        testPassage.add(testSec);
        testPass.addPassageSection(testSecTwo);
        testPassage.add(testSecTwo);
        assertEquals(testPass.getThePassage(), testPassage);
    }

    @Test
    public void testRemoveSection() {
        System.out.println ("removeSection");
        ArrayList<PassageSection> testSection = new ArrayList<PassageSection>();

        testPass.getThePassage().clear();
        testPass.addPassageSection(testSec);
        testPass.addPassageSection(testSecTwo);
        testSection.add(testSecTwo);
        testPass.removeSection(0);
        assertEquals(testPass.getThePassage(), testSection);
    }
  
}
