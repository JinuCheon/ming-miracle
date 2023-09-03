package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

//@SpringBootTest
class PageViewTest {

    PageUseCase pageUseCase = new PageUseCase(new LoadPageInfoCommandImpl(new H2DatabaseConnectionManager()));

    @BeforeEach
    void setUp() throws SQLException {
        final DatabaseConnectionManager databaseConnectionManager = new H2DatabaseConnectionManager();
        databaseConnectionManager.generateTable(getCreatePageTableQuery());
        databaseConnectionManager.insertQuery(getDummyInsertQueryForPageTable());
    }

    private String getDummyInsertQueryForPageTable() {
        return """
                INSERT INTO PAGE ("ID", "TITLE", "CONTENT", "PARENT_ID") VALUES
                ('dummyuuid0001', 'Sample Title 1', 'Sample Content 1', NULL),
                ('dummyuuid0002', 'Sample Title 2', 'Sample Content 2', 'dummyuuid0001'),
                ('dummyuuid0003', 'Sample Title 3', 'Sample Content 3', 'dummyuuid0002'),
                ('dummyuuid0004', 'Sample Title 4', 'Sample Content 4', 'dummyuuid0003'),
                ('dummyuuid0005', 'Sample Title 5', 'Sample Content 5', 'dummyuuid0004');
                """;
    }

    private static String getCreatePageTableQuery() {
        return """
                CREATE TABLE IF NOT EXISTS PAGE (
                  "ID" VARCHAR(36) NOT NULL,
                  "TITLE" VARCHAR(255) NOT NULL,
                  "CONTENT" VARCHAR(4000),\s
                  "PARENT_ID" VARCHAR(36),
                  PRIMARY KEY ("ID"),
                  FOREIGN KEY ("PARENT_ID") REFERENCES "PAGE"("ID"));
                """;
    }

    @Test
    void testPageView() {
        //given
        final String pageId = "dummyuuid0003";
        final PageViewRequest request = new PageViewRequest(pageId);

        //when
        final PageResponse response = pageUseCase.getPageView(request);

        //then
        assertThat(response.id()).isEqualTo(pageId);
    }

    private static class PageUseCase {
        private final LoadPageInfoCommand loadPageInfoCommand;
        public PageUseCase(final LoadPageInfoCommand loadPageInfoCommand) {
            this.loadPageInfoCommand = loadPageInfoCommand;
        }

        public PageResponse getPageView(final PageViewRequest request) {
            final Page pageList = loadPageInfoCommand.selectAllAttributes(request.pageId());
            final List<SummaryPageInfo> subPages  = loadSubPages(request.pageId());
            final List<SummaryPageInfo> breadcrumbs  = loadBreadcrumbs(request.pageId());
            return PageResponse.of(pageList, subPages, breadcrumbs);
        }

        private List<SummaryPageInfo> loadBreadcrumbs(final String pageId) {
            SummaryPageInfo summaryOfParentPage = loadPageInfoCommand.selectSummaryOfParentPage(pageId);
            if (summaryOfParentPage.parentId() == null) {
                return new ArrayList<>(List.of(summaryOfParentPage));
            }
            final List<SummaryPageInfo> summaryPageInfoList = loadBreadcrumbs(summaryOfParentPage.parentId());
            summaryPageInfoList.add(summaryOfParentPage);
            return summaryPageInfoList;
        }

        private List<SummaryPageInfo> loadSubPages(final String pageId) {
            return loadPageInfoCommand.selectSummaryOfSubPages(pageId);
        }
    }

    private record PageViewRequest(String pageId) {
    }

    private record PageResponse(
            String id,
            String title,
            String content,
            List<SummaryPageResponse> subPages,
            List<SummaryPageResponse> breadcrumbs
    ) {
        private record SummaryPageResponse(String id, String title) {
        }

        public static PageResponse of(final Page page, final List<SummaryPageInfo> subPages, final List<SummaryPageInfo> breadcrumbs) {
            return new PageResponse(
                    page.id(),
                    page.title(),
                    page.content(),
                    convertSummary(breadcrumbs),
                    convertSummary(subPages)
            );
        }

        private static List<SummaryPageResponse> convertSummary(final List<SummaryPageInfo> subPages) {
            return subPages.stream()
                    .map(summaryPageInfo -> new SummaryPageResponse(summaryPageInfo.id(), summaryPageInfo.title()))
                    .toList();
        }
    }

    private record SummaryPageInfo(String id, String title, String parentId) {
    }

    private interface DatabaseConnectionManager {
        ResultSet selectWithoutTransaction(String selectQuery) throws SQLException;

        void generateTable(String createPageTableQuery) throws SQLException;

        void insertQuery(String dummyInsertQueryForPageTable) throws SQLException;
    }

    public static class H2DatabaseConnectionManager implements DatabaseConnectionManager {
        private static final String driver = "com.mysql.jdbc.Driver";
        private static final String url = "jdbc:h2:mem:ming_miracle";
        private static final String user = "sa";
        private static final String pw = "";

        @Override
        public ResultSet selectWithoutTransaction(final String selectQuery) throws SQLException {
            Connection connection = getConnection();
            connection.setAutoCommit(true);
            Statement stmt = connection.createStatement();
            return stmt.executeQuery(selectQuery);
        }

        @Override
        public void generateTable(final String createPageTableQuery) throws SQLException {
            Connection connection = getConnection();
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(createPageTableQuery);
        }

        @Override
        public void insertQuery(final String dummyInsertQueryForPageTable) throws SQLException {
            Connection connection = getConnection();
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(dummyInsertQueryForPageTable);
        }

        private Connection getConnection() throws SQLException {
            return DriverManager.getConnection(url, user, pw); // DB 연결;
        }
    }

    private interface LoadPageInfoCommand {
        Page selectAllAttributes(final String pageId);

        SummaryPageInfo selectSummaryOfParentPage(String pageId);

        List<SummaryPageInfo> selectSummaryOfSubPages(String pageId);
    }

    public static class LoadPageInfoCommandImpl implements LoadPageInfoCommand {
        private final DatabaseConnectionManager databaseConnectionManager;

        public LoadPageInfoCommandImpl(final DatabaseConnectionManager databaseConnectionManager) {
            this.databaseConnectionManager = databaseConnectionManager;
        }

        @Override
        public Page selectAllAttributes(final String pageId) {
            try {
                ResultSet resultSet = databaseConnectionManager.selectWithoutTransaction("select * from PAGE where ID = '" + pageId + "'");
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
                ResultSet resultSet = databaseConnectionManager.selectWithoutTransaction("select ID, TITLE, PARENT_ID from PAGE where ID = '" + parentPageId + "'");
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
            throw new UnsupportedOperationException("not implemented yet.");
        }
    }

    private record Page(String id, String title, String content, String parentId) {
    }
}
