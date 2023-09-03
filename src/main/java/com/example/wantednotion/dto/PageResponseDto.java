package com.example.wantednotion.dto;

import com.example.wantednotion.domain.Page;
import com.example.wantednotion.domain.SubPage;
import lombok.Getter;

import java.util.Deque;
import java.util.List;

@Getter
public class PageResponseDto {

    private String id;
    private String title;
    private String content;
    private List<SubPage> subPages;
    private Deque<SubPage> breadcrumbs;

    public PageResponseDto(Page page,
                           List<SubPage> subPages,
                           Deque<SubPage> breadcrumbs) {
        this.id = page.getId();
        this.title = page.getTitle();
        this.content = page.getContent();
        this.subPages = subPages;
        this.breadcrumbs = breadcrumbs;
    }
}
