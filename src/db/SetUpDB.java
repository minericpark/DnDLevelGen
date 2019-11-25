package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class SetUpDB {

    private Connection conn = null;
    private Statement stmt = null;

    /**
     * Main constructor for DBSetup.
     */
    public SetUpDB() {
        String name = DBDetails.username;
        String pass = DBDetails.password;

        deleteT(name, pass);     //Use to delete the tables completely
        runSript(name, pass);   //Use to build out the required tables
        setUpMonster(name, pass); //Use to build base data
    }

    /**
     * Deletes table.
     *
     * @param n username provided
     * @param p password provided
     */
    public void deleteT(String n, String p) {
        try {
            Class.forName(DBDetails.JDBC_DRIVER);
            conn = DriverManager.getConnection(DBDetails.DB_URL, n, p);
            stmt = conn.createStatement();

            stmt.executeUpdate("DROP TABLE Monsters");

            conn.close();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    /**
     * Creates empty table for database.
     *
     * @param n username provided
     * @param p password provided
     */
    public void runSript(String n, String p) {
        try {
            Class.forName(DBDetails.JDBC_DRIVER);
            conn = DriverManager.getConnection(DBDetails.DB_URL, n, p);
            stmt = conn.createStatement();

            //add Courses table
            String sql = "CREATE TABLE IF NOT EXISTS Monsters (\n"
                    + "	name text NOT NULL,\n"
                    + "	upper text,\n"
                    + "	lower text,\n"
                    + "	description text\n"
                    + ");";


            //System.out.println(sql); // echo the statement for debugging
            //System.out.println(sql2); // echo the statement for debugging

            stmt.executeUpdate(sql);

            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Sets up database with default monsters.
     *
     * @param n username provided
     * @param p password provided
     */
    public void setUpMonster(String n, String p) {
        DBConnection mainConnect = new DBConnection(n, p);

        mainConnect.addMonster("Ant, giant", "4", "1", "Giant ants with hard shell plates");
        mainConnect.addMonster("Badger", "4", "1", "Creatures from a planet called Earth");
        mainConnect.addMonster("Beetle, fire", "4", "1", "Beetles toasting and covered in flames");
        mainConnect.addMonster("Demon, mane", "4", "1", "Large brutes with wild manes");
        mainConnect.addMonster("Dwarf", "14", "4", "Small and sturdy civil humanoids");
        mainConnect.addMonster("Ear Seeker", "1", "1", "Creatures with an irresistable weakness for ears");
        mainConnect.addMonster("Elf", "11", "3", "Eternal humanoids with bows and arrows");
        mainConnect.addMonster("Gnome", "15", "5", "Tiny humanoids with sharp hats");
        mainConnect.addMonster("Goblin", "15", "5", "Green creatures equipped with leather armor");
        mainConnect.addMonster("Halfling", "16", "9", "Half humans with interesting mixed blood");
        mainConnect.addMonster("Hobgoblin", " 8", "2", "Smaller goblins with less control but more speed");
        mainConnect.addMonster("Human Bandit", "15", "5", "Humans who delight in the misery of others");
        mainConnect.addMonster("Kobold", "18", "6", "Tiny little humanoids with sharp spears");
        mainConnect.addMonster("Orc", "12", "7", "Smelly creatures with blunt teeth");
        mainConnect.addMonster("Piercer", "3", "1", "Large lobsters with incredibly sharp claws");
        mainConnect.addMonster("Rat, giant", "20", "5", "Giant rats with an infectious bite");
        mainConnect.addMonster("Rot brug", "3", "1", "A slow humanoid that can explode with the force of a bomb");
        mainConnect.addMonster("Shrieker", "4", "1", "Loud, ear-shrilling monsters");
        mainConnect.addMonster("Skeleton", "4", "1", "Undead beings that are enchanted with evil spirits");
        mainConnect.addMonster("Zombie", "3", "1", "Undead beings that ravaged a planet called Earth");
    }

    /**
     * Main calls setUpDB.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SetUpDB sInstance = new SetUpDB();
    }

}
