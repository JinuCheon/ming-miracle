package org.example.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatException;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import java.util.stream.Collectors;
import org.example.data.JdbcPageRepository;
import org.example.dto.PageResponse;
import org.example.dto.PageSummaryResponse;
import org.example.support.DataSetup;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PageServiceTest {

    private final PageService pageService = new PageService(new JdbcPageRepository());

    @BeforeAll
    static void setUp() {
        DataSetup.createTable();
    }

    @AfterEach
    void truncate() {
        DataSetup.truncate();
    }

    @DisplayName("id로 page 조회")
    @Nested
    class findById {

        @BeforeEach
        void insertRows() {
            final String rows = "('00001', 'root', '내용1', null), "
                    + "('00002', 'sub of 1 - 1', '내용2', '00001'), "
                    + "('00003', 'sub of 1 - 2', '내용3', '00001'), "
                    + "('00004', 'sub of 2', '내용4', '00002'), "
                    + "('00005', 'sub of 4 - 1', '내용5', '00004'), "
                    + "('00006', 'sub of 4 - 2', '내용6', '00004')";
            DataSetup.insertRows(rows);
        }

        @DisplayName("하면 자식 페이지, bread crumbs를 함께 조회한다.")
        @Test
        void success() {
            // given
            final String subOf2Id = "00004"; // page 4, sub of 2

            // when
            final PageResponse response = pageService.findById(subOf2Id);

            // then
            final List<String> subPageTitles = collectTitles(response.getSubPages());
            final List<String> breadCrumbTitles = collectTitles(response.getBreadcrumbs());

            assertAll(
                    () -> assertThat(subPageTitles).containsExactly("sub of 4 - 1", "sub of 4 - 2"),
                    () -> assertThat(breadCrumbTitles).containsExactly("root", "sub of 1 - 1")
            );
        }

        @DisplayName("할 때, root 페이지를 조회하면 빈 breadCrumbs를 반환한다.")
        @Test
        void returnEmptyBreadCrumbs_ifPageIsRoot() {
            // given
            final String rootId = "00001";

            // when
            final PageResponse response = pageService.findById(rootId);

            // then
            final List<String> subPageTitles = collectTitles(response.getSubPages());

            assertAll(
                    () -> assertThat(subPageTitles).containsExactly("sub of 1 - 1", "sub of 1 - 2"),
                    () -> assertThat(response.getBreadcrumbs()).isEmpty()
            );
        }

        @DisplayName("할 때, 해당하는 페이지가 없으면 예외가 발생한다.")
        @Test
        void throwsException_whenPageNotFound() {
            // given
            final String invalidId = "00000";

            // when & then
            assertThatException()
                    .isThrownBy(() -> pageService.findById(invalidId))
                    .withMessageContaining("존재하지 않습니다");
        }

        private List<String> collectTitles(final List<PageSummaryResponse> summaryResponses) {
            return summaryResponses.stream()
                    .map(PageSummaryResponse::getTitle)
                    .collect(Collectors.toList());
        }
    }
}
