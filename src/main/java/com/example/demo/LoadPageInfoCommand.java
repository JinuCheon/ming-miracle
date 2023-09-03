package com.example.demo;

import java.util.List;

interface LoadPageInfoCommand {
    Page selectAllAttributes(final String pageId);

    SummaryPageInfo selectSummaryOfParentPage(String pageId);

    List<SummaryPageInfo> selectSummaryOfSubPages(String pageId);
}
