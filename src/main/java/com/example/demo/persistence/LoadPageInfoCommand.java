package com.example.demo.persistence;

import java.util.List;

public interface LoadPageInfoCommand {
    Page selectAllAttributes(final String pageId);

    SummaryPageInfo selectSummaryOfParentPage(String pageId);

    List<SummaryPageInfo> selectSummaryOfSubPages(String pageId);
}
