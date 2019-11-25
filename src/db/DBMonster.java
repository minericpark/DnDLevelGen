package db;

public class DBMonster implements java.io.Serializable {

    private String monsterName;
    private String upperBound;
    private String lowerBound;
    private String description;

    /**
     * Default constructor for DBMonster.
     */
    public DBMonster() {

    }

    /**
     * DBMonster constructor with 4 params of strings.
     * @param name name of DBMonster
     * @param upper upperbound of DBMonster
     * @param lower lowerbound of DBMonster
     * @param desc description of DBMonster
     */
    public DBMonster(String name, String upper, String lower, String desc) {
        setName(name);
        setUpperBound(upper);
        setLowerBound(lower);
        setDescription(desc);

    }

    /**
     * Sets name of DBMonster.
     * @param name provided new name
     */
    public void setName(String name) {
        monsterName = name;
    }

    /**
     * Sets upper bound of DBMonster.
     * @param upper provided new upper bound
     */
    public void setUpperBound(String upper) {
        upperBound = upper;
    }

    /**
     * Sets lowerbound of DBMonster.
     * @param lower provided new lower bound
     */
    public void setLowerBound(String lower) {
        lowerBound = lower;
    }

    /**
     * Sets description of DBMonster.
     * @param desc provided new description
     */
    public void setDescription(String desc) {
        description = desc;
    }

    /**
     * Gets name of DBMonster.
     * @return monsterName name of monster
     */
    public String getName() {
        return monsterName;
    }

    /**
     * Gets upper bound of DBMonster.
     * @return upperBound upperbound of monster
     */
    public String getUpper() {
        return upperBound;
    }

    /**
     * Gets lower bound of DBMonster.
     * @return lowerBound lowerbound of monster
     */
    public String getLower() {
        return lowerBound;
    }

    /**
     * Gets descripion of DBMonster.
     * @return description description of monster
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets toString of monster.
     * @return getName() + " max:" + getUpper()
     */
    @Override
    public String toString() {
        return getName() + " max:" + getUpper();  //TODO needs a better toString()

    }
}
