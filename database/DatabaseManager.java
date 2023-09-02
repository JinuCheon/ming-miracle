package database;

import org.h2.jdbcx.JdbcConnectionPool;

public class DatabaseManager {

    private static JdbcConnectionPool connectionPool;

    public static JdbcConnectionPool getConnectionPool() {
        if (connectionPool == null) {

            // 데이터베이스 연결 설정
            String jdbcUrl = "jdbc:h2:tcp://localhost/~/ming-miracle";
            String jdbcUsername = "sa";
            String jdbcPassword = "";

            connectionPool = JdbcConnectionPool.create(jdbcUrl, jdbcUsername, jdbcPassword);
        }
        return connectionPool;
    }

    // 데이터베이스 연결 풀 종료
    public static void closeConnectionPool() {
        if (connectionPool != null) {
            connectionPool.dispose();
        }
    }

}
