package org.example.domain;

public class Page {

    private final String id;
    private final String title;
    private final String content;
    private final String parentId;

    public Page(final String id, final String title, final String content, final String parentId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.parentId = parentId;
    }

    public boolean isRoot() {
        return parentId == null;
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
