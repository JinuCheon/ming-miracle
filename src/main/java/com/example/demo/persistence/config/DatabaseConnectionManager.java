package com.example.demo.persistence.config;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface DatabaseConnectionManager {
    ResultSet selectWithoutTransaction(String selectQuery) throws SQLException;

    void generateTable(String createPageTableQuery) throws SQLException;

    void insertQuery(String dummyInsertQueryForPageTable) throws SQLException;
}
