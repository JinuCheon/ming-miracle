package org.example;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import java.util.stream.Collectors;
import org.example.dto.PageResponse;
import org.example.dto.PageSummaryResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PageServiceTest {

    private final PageService pageService = new PageService();

    @BeforeAll
    static void setUp() {
        DataSetup.createTable();
    }

    @AfterEach
    void truncate() {
        DataSetup.truncate();
    }

    @DisplayName("id로 페이지, 자식 페이지, bread crumbs를 조회한다.")
    @Test
    void findById() {
        // given
        final String rows = "('00001', 'root', '내용1', null), "
                + "('00002', 'parent', '내용2', '00001'), " //
                + "('00003', 'not expected', '내용3', '00001'), "
                + "('00004', 'target', '내용4', '00002'), "
                + "('00005', 'sub1', '내용5', '00004'), "
                + "('00006', 'sub2', '내용6', '00004')";
        DataSetup.insertRows(rows);

        final PageResponse response = pageService.findById("00004");

        final List<String> subPageTitles = response.getSubPages()
                .stream()
                .map(PageSummaryResponse::getTitle)
                .collect(Collectors.toList());

        final List<String> breadCrumbTitles = response.getBreadcrumbs()
                .stream()
                .map(PageSummaryResponse::getTitle)
                .collect(Collectors.toList());

        assertAll(
                () -> assertThat(subPageTitles).containsExactly("sub1", "sub2"),
                () -> assertThat(breadCrumbTitles).containsExactly("root", "parent")
        );
    }
}
