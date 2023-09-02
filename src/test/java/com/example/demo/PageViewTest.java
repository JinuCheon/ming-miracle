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
//            return loadPageInfoCommand.selectSummaryOfParentPages(pageId);
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
            List<SummaryPageInfo> subPages,
            List<SummaryPageInfo> breadcrumbs
    ) {
        public static PageResponse of(final Page page, final List<SummaryPageInfo> subPages, final List<SummaryPageInfo> breadcrumbs) {
            return new PageResponse(
                    page.id(),
                    page.title(),
                    page.content(),
                    subPages,
                    breadcrumbs
            );
        }
    }

    private record SummaryPageInfo(String id, String title) {
    }

    private interface DatabaseConnectionManager {
        Connection startConnect();
    }

    private interface LoadPageInfoCommand {
        Page selectAllColumns();
    }

    private static class Page {
        private final String id;
        private final String title;
        private final String content;

        public Page(final String id, final String title, final String content) {
            this.id = id;
            this.title = title;
            this.content = content;
        }

        public String id() {
            return id;
        }

        public String title() {
            return title;
        }

        public String content() {
            return content;
        }
    }
}
