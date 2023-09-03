package org.example.data;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.example.domain.Page;
import org.example.support.DataSetup;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class JdbcPageRepositoryTest {

    private final JdbcPageRepository jdbcPageRepository = new JdbcPageRepository();

    @BeforeAll
    static void setUp() {
        DataSetup.createTable();
    }

    @AfterEach
    void truncate() {
        DataSetup.truncate();
    }

    @DisplayName("id로 페이지 조회")
    @Nested
    class findById {

        @BeforeEach
        void insert3Pages() {
            final String rows = "('00001', 'expected', '내용1', null), "
                    + "('00002', 'not expected1', '내용2', '00001'), "
                    + "('00003', 'not expected2', '내용3', '00001')";
            DataSetup.insertRows(rows);
        }

        @DisplayName("할 수 있다.")
        @Test
        void success() {
            // given & when
            final Page found = jdbcPageRepository.findById("00001").get();

            // then
            assertThat(found.getTitle()).isEqualTo("expected");
        }

        @DisplayName("할 때, 해당하는 페이지가 없으면 빈 값을 반환한다.")
        @Test
        void returnEmpty_whenPageNotFound() {
            // given
            final String invalidId = "00000";

            // when
            final Optional<Page> found = jdbcPageRepository.findById(invalidId);

            // then
            assertThat(found).isEmpty();
        }
    }

    @DisplayName("부모 페이지id로 자식 페이지 목록을 조회할 수 있다.")
    @Test
    void findAllByParentId() {
        // given
        final String rows = "('00001', 'parent', '내용1', null), "
                + "('00002', 'sub1', '내용2', '00001'), "
                + "('00003', 'sub2', '내용3', '00001')";
        DataSetup.insertRows(rows);

        // when
        final List<Page> found = jdbcPageRepository.findAllByParentId("00001");

        // then
        final List<String> titles = found.stream()
                .map(Page::getTitle)
                .collect(Collectors.toList());
        assertThat(titles).containsExactly("sub1", "sub2");
    }
}
