package org.example;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import org.example.data.DataSource;

public class DataSetup {

    public static void createTable() {
        execute("CREATE TABLE IF NOT EXISTS PAGE ("
                + "  id VARCHAR(36) NOT NULL,"
                + "  title VARCHAR(255) NOT NULL,"
                + "  content VARCHAR(4000), "
                + "  parentId VARCHAR(36),"
                + "  PRIMARY KEY (id),"
                + "  FOREIGN KEY (parentId) REFERENCES PAGE(id)"
                + ");");
    }

    public static void truncate() {
        execute("SET REFERENTIAL_INTEGRITY FALSE");
        execute("TRUNCATE TABLE PAGE");
        execute("SET REFERENTIAL_INTEGRITY TRUE");
    }

    public static void insertRows(final String rows) {
        execute("INSERT INTO PAGE (id, title, content, parentId) VALUES"
                + rows
                + ";");
    }

    private static void execute(final String sql) {
        try (
                final Connection connection = DataSource.getConnection();
                final Statement statement = connection.createStatement();
        ) {
            statement.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
