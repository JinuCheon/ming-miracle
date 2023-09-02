package com.wanted.mingmiracle.page.service;

import com.wanted.mingmiracle.page.domain.Page;
import com.wanted.mingmiracle.page.dto.PageResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@ActiveProfiles("develop")
@Sql(value = "classpath:db/teardown.sql") // 매 Test 실행시 teardown.sql 실행해 초기 환경으로 되돌림
class PageServiceTest {

    @Autowired
    PageService pageService;

    @DisplayName("페이지 정보 가져오기 테스트")
    @Test
    void getPageInfoTest() {
        // given
        final String testId = "4e7468eb-1449-4f07-b982-ae1f33abfdf4"; // '페이지 3의 서브페이지 1'의 id

        // when
        final PageResponseDTO response = pageService.getPageInfo(testId);

        final List<String[]> subPages = response.getSubPages();

        final List<String[]> breadCrumbs = response.getBreadcrumbs();

        // then
        assertThat(response.getId()).isEqualTo(testId);
        assertThat(response.getTitle()).isEqualTo("페이지 3의 서브페이지 1"); // '페이지 3의 서브페이지 1'의 제목
        assertThat(response.getContent()).isEqualTo("서브페이지 1의 내용"); // '페이지 3의 서브페이지 1'의 내용

        assertAll(
                () -> assertThat(subPages).containsExactly(
                        new String[]{"eff293d2-d200-4807-9efb-ff9909408ce4", "페이지 3의 서브페이지 1의 서브페이지 1"}, // '페이지 3의 서브페이지 1'의 하위 페이지1의 id와 제목
                        new String[]{"2c8015a3-76f0-4d2d-afed-4b7fb8ea8527", "페이지 3의 서브페이지 1의 서브페이지 2"} // '페이지 3의 서브페이지 1'의 하위 페이지2의 id와 제목
                ),
                () -> assertThat(breadCrumbs).containsExactly(
                        new String[]{"a98e6a21-6f80-4b71-aa3b-e5c96f6a2537", "페이지 3"} // '페이지 3의 서브페이지 1'의 부모페이지(페이지 3)의 id와 제목
                )
        );
    }
}