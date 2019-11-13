package epark;

import dnd.die.D20;
import dnd.models.Exit;
import dnd.models.Trap;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertTrue;

import epark.Chamber;
import epark.Door;
import epark.Passage;
import org.junit.Before;
import org.junit.Test;
import epark.Space;


public class DoorTest {

    private Door testDoor;
    private D20 testRoll;
    private Trap testTrap;
    private Chamber testChamb;
    private Passage testPass;
    private Exit testExit;

    public DoorTest() {
    }
    
   @Before
   public void setUp() {
        testDoor = new Door();
        testTrap = new Trap();
        testRoll = new D20();
        testChamb = new Chamber();
        testPass = new Passage();
        testExit = new Exit();
   }

   @Test
   public void testConstructor() {
        System.out.println ("constructor");
        assertTrue(!testDoor.getDescription().isEmpty());
   }

   @Test
   public void testConstructorTwo() {
        System.out.println ("constructor with exit");
        testDoor = new Door(testExit);
        assertTrue(testDoor.getDescription().contains(testExit.getLocation()));
   }

   @Test
   public void testSetSpace() {
        System.out.println ("setSpace");
        testDoor.getSpaces().clear();
        testDoor.setSpace(testChamb);
        assertEquals(testDoor.getSpaces().get(0), testChamb);
   }

   @Test
   public void testSetSpaces() {
        System.out.println ("setSpaces");
        ArrayList<Space> testSpaces = new ArrayList<Space>();
        testDoor.getSpaces().clear();
        testDoor.setSpace(testChamb);
        testSpaces.add(testChamb);
        testDoor.setSpace(testPass);
        testSpaces.add(testPass);
        assertEquals(testDoor.getSpaces(), testSpaces);
   }

   @Test
   public void testSetTrapped() {
       System.out.println ("setTrapped");
       int givenRoll = testRoll.roll();
       testTrap = new Trap();
       testTrap.setDescription(givenRoll);
       testDoor.setTrapped(true, givenRoll);
       assertTrue(testDoor.isTrapped());
   }

   @Test
   /*Archways are exceptions*/
   public void testSetOpen() {
        System.out.println ("setOpen");
        testDoor.setOpen(true);
        assertTrue(testDoor.getDescription().contains("open"));
   }

   @Test
   /*Archways currently dumbed down to 'open'*/
   public void testSetArchway() {
        System.out.println ("setArchway");
        testDoor.setArchway(true);
        assertTrue(testDoor.getDescription().contains("open"));
   }

   @Test
   /*Archways are exceptions*/
   public void testSetLocked() {
        System.out.println ("setLocked");
        testDoor.setLocked(true);
        assertTrue(testDoor.getDescription().contains("locked"));
   }

   @Test
   public void testIsTrapped() {
       System.out.println ("isTrapped");
       testDoor.setTrapped(true);
       assertTrue(testDoor.isTrapped());
   }

   @Test
   public void testIsOpen() {
       System.out.println ("isOpen");
       testDoor.setOpen(true);
       assertTrue(testDoor.isOpen());
   }

   @Test
   public void testIsArchway() {
       System.out.println ("isArchway");
       testDoor.setArchway(true);
       assertTrue(testDoor.isArchway());
   }

   @Test
   /*Archways exceptions: cannot be locked*/
   public void testIsLocked() {
       System.out.println ("isLocked");
       testDoor.setLocked(true);
       assertTrue(testDoor.isLocked() || testDoor.isArchway());
   }

   @Test
   public void testGetTrapDescription() {
       System.out.println ("getTrapDescription");
       int rolled = testRoll.roll();

       testDoor.setTrapped(true, rolled);
       testTrap.setDescription(rolled);
       assertEquals(testDoor.getTrapDescription(), testTrap.getDescription());
   }

   @Test
   public void testGetSpaces() {
       System.out.println ("getSpaces");
       ArrayList<Space> testList = new ArrayList<Space>();

       testDoor.getSpaces().clear();
       testDoor.setSpace(testChamb);
       testList.add(testChamb);
       testDoor.setSpace(testPass);
       testList.add(testPass);
       assertEquals(testDoor.getSpaces(), testList);
   }

   @Test
   public void testGetDescription() {
       System.out.println ("getDescription");

       testDoor = new Door(testExit);
       assertTrue(testDoor.getDescription().contains(testExit.getDirection()));
   }
/* set up similar to the sample in PassageTest.java */

}
