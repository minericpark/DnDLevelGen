package epark;

import dnd.models.Exit;
import dnd.models.Monster;
import dnd.models.Treasure;
import epark.Door;
import epark.PassageSection;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class PassageSectionTest {

    private PassageSection testSec;
    private Monster testMonster;
    private Door testDoor;
    private Treasure testTreasure;

    public PassageSectionTest() {

    }

    @Before
    public void setUp() {
        testSec = new PassageSection();
        testMonster = new Monster();
        testDoor = new Door();
        testTreasure = new Treasure();
    }

    @Test
    public void testConstructor() {
        System.out.println ("constructor");
        assertTrue(!testSec.getDescription().isEmpty());
    }

    @Test
    public void testConstructorTwo() {
        System.out.println ("constructor with description");
        String testDescrip = "test";
        testSec = new PassageSection(testDescrip);
        assertEquals(testSec.getDescription(), testDescrip + "\n");
    }

    @Test
    public void testSetMonsterExist(){
        System.out.println ("setMonsterExist");
        boolean monsterExist = true;
        testSec.setMonsterExist(monsterExist);
        assertEquals(testSec.getMonsterExist(), monsterExist);
    }

    @Test
    public void testSetDoorExist() {
        System.out.println ("setDoorExist");
        boolean doorExist = true;
        testSec.setDoorExist(doorExist);
        assertEquals(testSec.getDoorExist(), doorExist);
    }

    @Test
    public void testSetChamberAhead() {
        System.out.println ("setChamberAhead");
        boolean chambAhead = true;
        testSec.setChamberAhead(true);
        assertEquals(testSec.getChamberAhead(), chambAhead);
    }

    @Test
    public void testSetDoor() {
        System.out.println ("setDoor");
        testSec.setDoor(testDoor);
        assertEquals(testSec.getDoor(), testDoor);
    }

    @Test
    public void testUpdateMonster() {
        System.out.println ("updateMonster");
        testSec.updateMonster(testMonster);
        assertEquals(testSec.getMonster(), testMonster);
    }

    @Test
    public void testUpdateDoor() {
        System.out.println ("updateDoor");
        testSec.updateDoor(testDoor);
        assertEquals(testSec.getDoor(), testDoor);
    }

    @Test
    public void testGetDoor() {
        System.out.println ("getDoor");
        testSec.updateDoor(testDoor);
        assertEquals(testSec.getDoor(), testDoor);
    }

    @Test
    public void testGetMonster() {
        System.out.println ("getMonster");
        testSec.updateMonster(testMonster);
        assertEquals(testSec.getMonster(), testMonster);
    }

    @Test
    public void testGetDescription() {
        System.out.println ("getDescription");
        String testString = "testDescription";
        testSec = new PassageSection(testString);
        assertEquals(testSec.getDescription(), testString + "\n");
    }

    @Test
    public void testGetMonsterExist() {
        System.out.println ("getMonsterExist");
        testSec.updateMonster(testMonster);
        assertEquals(testSec.getMonsterExist(), true);
    }

    @Test
    public void testGetDoorExist() {
        System.out.println ("getDoorExist");
        testSec.setDoor(testDoor);
        assertEquals(testSec.getDoorExist(), true);
    }

    @Test
    public void testGetChamberAhead() {
        System.out.println ("getChamberAhead");
        boolean chambAhead = false;
        testSec.setChamberAhead(chambAhead);
        assertEquals(testSec.getChamberAhead(), chambAhead);
    }

    @Test
    public void testGetDeadEnd() {
        System.out.println ("getDeadEnd");
        testSec = new PassageSection("dead end");
        assertEquals(testSec.getDeadEnd(), true);
    }

}
