package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

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
        assertThat(response.getId()).isEqualTo(pageId);
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
    }

    private record SummaryPageInfo(String id, String title) {
    }
}
