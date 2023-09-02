package com.wanted.mingmiracle.page.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Page {
    private String id;
    private String parentId;
    private String title;
    private String content;
}
