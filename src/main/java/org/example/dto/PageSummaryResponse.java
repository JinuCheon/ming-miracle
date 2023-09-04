package org.example.dto;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.example.domain.Page;

public class PageSummaryResponse {

    private final String id;
    private final String title;

    private PageSummaryResponse(final String id, final String title) {
        this.id = id;
        this.title = title;
    }

    public static List<PageSummaryResponse> from(final Collection<Page> pages) {
        return pages.stream()
                .map(PageSummaryResponse::from)
                .collect(Collectors.toList());
    }

    private static PageSummaryResponse from(final Page page) {
        return new PageSummaryResponse(page.getId(), page.getTitle());
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
