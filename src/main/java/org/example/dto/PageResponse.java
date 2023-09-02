package org.example.dto;

import java.util.List;

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
}
