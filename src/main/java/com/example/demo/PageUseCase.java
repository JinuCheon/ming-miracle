package com.example.demo;

import com.example.demo.persistence.LoadPageInfoCommand;
import com.example.demo.persistence.Page;
import com.example.demo.persistence.SummaryPageInfo;

import java.util.ArrayList;
import java.util.List;

class PageUseCase {
    private final LoadPageInfoCommand loadPageInfoCommand;

    public PageUseCase(final LoadPageInfoCommand loadPageInfoCommand) {
        this.loadPageInfoCommand = loadPageInfoCommand;
    }

    public PageResponse getPageView(final PageViewRequest request) {
        final Page targetPage = loadPageInfoCommand.selectAllAttributes(request.pageId());
        final List<SummaryPageInfo> subPages = loadSubPages(request.pageId());
        final List<SummaryPageInfo> breadcrumbs = loadBreadcrumbs(targetPage.parentId());
        return PageResponse.of(targetPage, subPages, breadcrumbs);
    }

    private List<SummaryPageInfo> loadBreadcrumbs(final String pageId) {
        SummaryPageInfo summaryOfParentPage = loadPageInfoCommand.selectSummaryOfParentPage(pageId);
        if (summaryOfParentPage.parentId() == null) {
            return new ArrayList<>(List.of(summaryOfParentPage));
        }
        final List<SummaryPageInfo> summaryPageInfoList = loadBreadcrumbs(summaryOfParentPage.parentId());
        summaryPageInfoList.add(summaryOfParentPage);
        return summaryPageInfoList;
    }

    private List<SummaryPageInfo> loadSubPages(final String pageId) {
        return loadPageInfoCommand.selectSummaryOfSubPages(pageId);
    }
}
