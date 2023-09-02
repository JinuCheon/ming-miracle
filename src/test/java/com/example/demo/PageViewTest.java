package com.example.demo;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

//@SpringBootTest
class PageViewTest {

    PageUseCase pageUseCase = new PageUseCase();

    @Test
    void testPageView() {
        //given
        final String pageId = "dummyuuid1234";
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
            final Page pageList = loadPageInfoCommand.selectAllColumns();
            final List<SummaryPageInfo> subPages  = loadSubPages(request.pageId());
            final List<SummaryPageInfo> breadcrumbs  = loadBreadcrumbs(request.pageId());
            return PageResponse.of(pageList, subPages, breadcrumbs);
        }

        private List<SummaryPageInfo> loadBreadcrumbs(final String pageId) {
            SummaryPageInfo summaryOfParentPage = loadPageInfoCommand.selectSummaryOfParentPage(pageId);
            if (summaryOfParentPage.parentId() == null) {
                return List.of(summaryOfParentPage);
            }
            return loadBreadcrumbs(summaryOfParentPage.parentId());
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
        Connection startConnect();
    }

    private interface LoadPageInfoCommand {
        Page selectAllColumns();

        SummaryPageInfo selectSummaryOfParentPage(String pageId);

        List<SummaryPageInfo> selectSummaryOfSubPages(String pageId);
    }

    private record Page(String id, String title, String content, String parentId) {
    }
}
