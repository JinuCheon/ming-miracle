package com.example.demo;

import java.sql.ResultSet;
import java.sql.SQLException;

interface DatabaseConnectionManager {
    ResultSet selectWithoutTransaction(String selectQuery) throws SQLException;

    void generateTable(String createPageTableQuery) throws SQLException;

    void insertQuery(String dummyInsertQueryForPageTable) throws SQLException;
}
