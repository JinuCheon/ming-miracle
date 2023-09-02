package org.example.data;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;
import org.example.DataSetup;
import org.example.domain.Page;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PageRepositoryTest {

    private final PageRepository pageRepository = new PageRepository();

    @BeforeAll
    static void setUp() {
        DataSetup.createTable();
    }

    @AfterEach
    void truncate() {
        DataSetup.truncate();
    }

    @DisplayName("id로 페이지를 조회할 수 있다.")
    @Test
    void findById() {
        // given
        final String rows = "('00001', '제목1', '내용1', null)";
        DataSetup.insertRows(rows);

        // when
        final Page found = pageRepository.findById("00001");

        // then
        assertThat(found.getTitle()).isEqualTo("제목1");
    }

    @DisplayName("부모 페이지id로 자식 페이지 목록을 조회할 수 있다.")
    @Test
    void findAllByParentId() {
        // given
        final String rows = "('00001', '제목1', '내용1', null), "
                + "('00002', '제목2', '내용2', '00001'), "
                + "('00003', '제목3', '내용3', '00001')";
        DataSetup.insertRows(rows);

        // when
        final List<Page> found = pageRepository.findAllByParentId("00001");

        // then
        final List<String> titles = found.stream()
                .map(Page::getTitle)
                .collect(Collectors.toList());
        assertThat(titles).containsExactly("제목2", "제목3");
    }
}
