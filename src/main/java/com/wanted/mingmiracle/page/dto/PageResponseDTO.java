package com.wanted.mingmiracle.page.dto;

import com.wanted.mingmiracle.page.domain.Page;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class PageResponseDTO {
    String id;
    String title;
    String content;
    List<String[]> subPages;
    List<String[]> breadcrumbs;

    public PageResponseDTO(Page page, List<String[]> subPages, List<String[]> breadcrumbs) {
        this.id = page.getId();
        this.title = page.getTitle();
        this.content = page.getContent();
        this.subPages = subPages;
        this.breadcrumbs = breadcrumbs;
    }
}
