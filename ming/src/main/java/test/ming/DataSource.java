package test.ming;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {
    private static final String URL = "jdbc:h2:tcp://localhost:9092/~/jpashop";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "";
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
