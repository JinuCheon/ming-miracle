package org.example.dto;

public class PageSummaryResponse {

    private final String id;
    private final String title;

    private PageSummaryResponse(final String id, final String title) {
        this.id = id;
        this.title = title;
    }
}
