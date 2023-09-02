package org.example;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import org.example.data.PageRepository;
import org.example.domain.Page;
import org.example.dto.PageResponse;

public class PageService {

    private final PageRepository pageRepository = new PageRepository();

    public PageResponse findById(final String id) {
        final Page page = pageRepository.findById(id);
        final List<Page> subPages = pageRepository.findAllByParentId(id);

        final Deque<Page> breadCrumbs = new ArrayDeque<>();
        collectBreadCrumbs(page.getParentId(), breadCrumbs);
        return PageResponse.of(page, subPages, breadCrumbs);
    }

    private void collectBreadCrumbs(final String id, final Deque<Page> breadCrumbs) {
        final Page page = pageRepository.findById(id);
        breadCrumbs.addFirst(page);

        if (page.getParentId() == null) {
            return;
        }
        collectBreadCrumbs(page.getParentId(), breadCrumbs);
    }
}
