package epark;

/**
 * DnD DM's Toolkit (Level Generator).
 *
 * @author Eric (Min)seo Park - 1001018
 * @version 3
 * @since October 28th, 2019
 */
final class Main {

    /**
     * Constructor for main.
     */
    private Main() {
    }
    /**
     * Main method of program: calls level to generate a simple, new level of the dungeon.
     *
     * @param args for main method
     */
    public static void main(String[] args) {

        Level testLevel = new Level();
        System.out.println(testLevel.getDescription());
    }

}
