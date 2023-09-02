package com.wanted.mingmiracle.page.dto;

import com.wanted.mingmiracle.page.domain.Page;
import lombok.*;

import java.util.List;

@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
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
