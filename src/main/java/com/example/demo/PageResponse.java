package com.example.demo;

import com.example.demo.persistence.Page;
import com.example.demo.persistence.SummaryPageInfo;

import java.util.List;

record PageResponse(
        String id,
        String title,
        String content,
        List<SummaryPageResponse> subPages,
        List<SummaryPageResponse> breadcrumbs
) {
    private record SummaryPageResponse(String id, String title) {
        @Override
        public String toString() {
            return "{" +
                    "\n\t\tid='" + id + "'," +
                    "\n\t\ttitle='" + title + '\'' +
                    "\n\t}";
        }
    }

    public static PageResponse of(final Page page, final List<SummaryPageInfo> subPages, final List<SummaryPageInfo> breadcrumbs) {
        return new PageResponse(
                page.id(),
                page.title(),
                page.content(),
                convertSummary(subPages),
                convertSummary(breadcrumbs)
        );
    }

    private static List<SummaryPageResponse> convertSummary(final List<SummaryPageInfo> subPages) {
        return subPages.stream()
                .map(summaryPageInfo -> new SummaryPageResponse(summaryPageInfo.id(), summaryPageInfo.title()))
                .toList();
    }

    @Override
    public String toString() {
        return "\n{" +
                "\n\tid='" + id + "'," +
                "\n\ttitle='" + title + "'," +
                "\n\tcontent='" + content + "'," +
                "\n\tsubPages= " + subPages.toString() + "'," +
                "\n\tbreadcrumbs= " + breadcrumbs.toString() +
                "\n}";
    }
}
