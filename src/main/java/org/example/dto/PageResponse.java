package org.example.dto;

import java.util.Collection;
import java.util.List;
import org.example.domain.Page;

public class PageResponse {

    private final String id;
    private final String title;
    private final String content;
    private final List<PageSummaryResponse> subPages;
    private final List<PageSummaryResponse> breadcrumbs;

    private PageResponse(final String id,
                         final String title,
                         final String content,
                         final List<PageSummaryResponse> subPages,
                         final List<PageSummaryResponse> breadcrumbs) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.subPages = subPages;
        this.breadcrumbs = breadcrumbs;
    }

    public static PageResponse of(final Page page, final Collection<Page> subPages,
                                  final Collection<Page> breadCrumbs) {
        return new PageResponse(
                page.getId(),
                page.getTitle(),
                page.getContent(),
                PageSummaryResponse.from(subPages),
                PageSummaryResponse.from(breadCrumbs)
        );
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public List<PageSummaryResponse> getSubPages() {
        return subPages;
    }

    public List<PageSummaryResponse> getBreadcrumbs() {
        return breadcrumbs;
    }
}
