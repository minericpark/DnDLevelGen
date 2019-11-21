package epark;

public abstract class Space implements java.io.Serializable {
    /**
     * Returns description of space.
     *
     * @return String description of space.
     */
    public abstract String getDescription();

    /**
     * Sets door of space.
     *
     * @param theDoor door to add into space
     */
    public abstract void setDoor(Door theDoor);

    /**
     * Returns ID of space.
     *
     * @return ID num of space
     */
    public abstract int getID();
}
