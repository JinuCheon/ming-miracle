package org.example.service;

import java.util.List;
import java.util.Optional;
import org.example.domain.BreadCrumbs;
import org.example.domain.Page;
import org.example.domain.PageRepository;
import org.example.dto.PageResponse;

public class PageService {

    private final PageRepository pageRepository;

    public PageService(final PageRepository pageRepository) {
        this.pageRepository = pageRepository;
    }

    public PageResponse findById(final String id) {
        final Page page = pageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 페이지가 존재하지 않습니다."));

        final List<Page> subPages = pageRepository.findAllByParentId(id);
        final BreadCrumbs breadCrumbs = collectBreadCrumbs(page.getParentId(), new BreadCrumbs());

        return PageResponse.of(page, List.copyOf(subPages), breadCrumbs);
    }

    private BreadCrumbs collectBreadCrumbs(final String id, final BreadCrumbs breadCrumbs) {
        final Optional<Page> found = pageRepository.findById(id);
        if (found.isEmpty()) {
            return breadCrumbs;
        }

        final Page page = found.get();
        breadCrumbs.addPrevious(page);

        return collectBreadCrumbs(page.getParentId(), breadCrumbs);
    }
}
