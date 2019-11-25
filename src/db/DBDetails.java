package db;

public class DBDetails implements java.io.Serializable {

    /**
     * Provided username and password of programmer's ID.
     */
    public static final String username = "epark03";
    public static final String password = "1001018";

    public static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String DB_URL = String.format("jdbc:mysql://dursley.socs.uoguelph.ca:3306/%s?useLegacyDatetimeCode=false&serverTimezone=America/New_York", username);

}
