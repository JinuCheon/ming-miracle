package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

//@SpringBootTest
class PageViewTest {

    PageUseCase pageUseCase = new PageUseCase();

    @Test
    void testPageView() {
        //given
        final long pageId = 1L;
        final PageViewRequest request = new PageViewRequest(pageId);

        //when
        final PageResponse response = pageUseCase.getPageView(request);

        //then
        assertThat(response.getId()).isEqualTo(pageId);
    }
}
