package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class H2DatabaseConnectionManager implements DatabaseConnectionManager {
    private static final String driver = "com.mysql.jdbc.Driver";
    private static final String url = "jdbc:h2:mem:ming_miracle";
    private static final String user = "sa";
    private static final String pw = "";

    @Override
    public ResultSet selectWithoutTransaction(final String selectQuery) throws SQLException {
        final Connection connection = getConnection();
        connection.setAutoCommit(true);
        final Statement stmt = connection.createStatement();
        return stmt.executeQuery(selectQuery);
    }

    @Override
    public void generateTable(final String createPageTableQuery) throws SQLException {
        final Connection connection = getConnection();
        final Statement stmt = connection.createStatement();
        stmt.executeUpdate(createPageTableQuery);
    }

    @Override
    public void insertQuery(final String dummyInsertQueryForPageTable) throws SQLException {
        final Connection connection = getConnection();
        final Statement stmt = connection.createStatement();
        stmt.executeUpdate(dummyInsertQueryForPageTable);
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, pw); // DB 연결;
    }
}
