package com.example.demo.persistence;

import com.example.demo.persistence.config.DatabaseConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoadPageInfoCommandImpl implements LoadPageInfoCommand {
    private final DatabaseConnectionManager databaseConnectionManager;

    public LoadPageInfoCommandImpl(final DatabaseConnectionManager databaseConnectionManager) {
        this.databaseConnectionManager = databaseConnectionManager;
    }

    @Override
    public Page selectAllAttributes(final String pageId) {
        try {
            final ResultSet resultSet = databaseConnectionManager.selectWithoutTransaction("select * from PAGE where ID = '" + pageId + "'");
            resultSet.next();
            return new Page(
                    resultSet.getString("ID"),
                    resultSet.getString("TITLE"),
                    resultSet.getString("CONTENT"),
                    resultSet.getString("PARENT_ID")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SummaryPageInfo selectSummaryOfParentPage(final String parentPageId) {
        try {
            final ResultSet resultSet = databaseConnectionManager.selectWithoutTransaction("select ID, TITLE, PARENT_ID from PAGE where ID = '" + parentPageId + "'");
            resultSet.next();
            return new SummaryPageInfo(
                    resultSet.getString("ID"),
                    resultSet.getString("TITLE"),
                    resultSet.getString("PARENT_ID")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<SummaryPageInfo> selectSummaryOfSubPages(final String pageId) {
        try {
            final ResultSet resultSet = databaseConnectionManager.selectWithoutTransaction("select ID, TITLE, PARENT_ID from PAGE where PARENT_ID = '" + pageId + "'");
            final List<SummaryPageInfo> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(new SummaryPageInfo(
                        resultSet.getString("ID"),
                        resultSet.getString("TITLE"),
                        resultSet.getString("PARENT_ID")
                ));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
