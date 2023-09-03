package com.example.wantednotion.domain;

import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
public class Page {

    private String id;
    private String title;
    private String content;
    private String parentId;

    public Page(String id, String title, String content, String parentId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.parentId = parentId;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getParentId() {
        return parentId;
    }
}
