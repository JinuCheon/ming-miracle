package com.example.demo;

import com.example.demo.persistence.config.DatabaseConnectionManager;
import com.example.demo.persistence.config.H2DatabaseConnectionManager;
import com.example.demo.persistence.LoadPageInfoCommandImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

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
                  "CONTENT" VARCHAR(4000),
                  "PARENT_ID" VARCHAR(36),
                  PRIMARY KEY ("ID"),
                  FOREIGN KEY ("PARENT_ID") REFERENCES "PAGE"("ID"));
                """;
    }

    @Test
    void testPageView() {
        //given
        final String pageId = "dummyuuid0003";
        final String expectedTitle = "Sample Title 3";
        final String expectedContent = "Sample Content 3";
        final int expectedSizeOfSubPages = 1;
        final int expectedSizeOfBreadCrumbs = 2;
        final PageViewRequest request = new PageViewRequest(pageId);

        //when
        final PageResponse response = pageUseCase.getPageView(request);

        //then
        assertThat(response.id()).isEqualTo(pageId);
        assertThat(response.title()).isEqualTo(expectedTitle);
        assertThat(response.content()).isEqualTo(expectedContent);
        assertThat(response.subPages()).hasSize(expectedSizeOfSubPages);
        assertThat(response.breadcrumbs()).hasSize(expectedSizeOfBreadCrumbs);

        //log
        System.out.println(response);
    }

}
