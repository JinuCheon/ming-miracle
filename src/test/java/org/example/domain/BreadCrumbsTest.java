package org.example.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatException;

import java.util.Collection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BreadCrumbsTest {

    @DisplayName("bread crumbs에 직전 페이지를 추가한다.")
    @Test
    void addPrevious() {
        // given
        final Page child = new Page("1", "child", "자식 페이지", "2");
        final Page parent = new Page("2", "parent", "부모 페이지", "3");
        final Page root = new Page("3", "root", "최상위 페이지", null);

        // when
        final BreadCrumbs breadCrumbs = new BreadCrumbs();
        breadCrumbs.addPrevious(child);
        breadCrumbs.addPrevious(parent);
        breadCrumbs.addPrevious(root);

        // then
        assertThat(breadCrumbs.getPages()).containsExactly(root, parent, child);
    }

    @DisplayName("페이지 목록 조회 시 수정할 수 없는 목록을 응답한다.")
    @Test
    void getPages_returnsUnmodifiableCollection() {
        // given
        final Page additionalPage = new Page("1", "some title", "some contents", null);

        // when & then
        final Collection<Page> pages = new BreadCrumbs().getPages();
        assertThatException()
                .isThrownBy(() -> pages.add(additionalPage));
    }
}
