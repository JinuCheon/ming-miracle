package model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Page {

    private String id;
    private String title;
    private String content;
    private String parentId;
    private List<Page> subPages;
    private List<Page> breadcrumbs;

    // 기본 생성자
    public Page() {

    }

    // Getter & Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public List<Page> getSubPages() {
        return subPages;
    }

    public void setSubPages(List<Page> subPages) {
        this.subPages = subPages;
    }

    public List<Page> getBreadcrumbs() {
        return breadcrumbs;
    }

    public void setBreadcrumbs(List<Page> breadcrumbs) {
        this.breadcrumbs = breadcrumbs;
    }

    // toString
    @Override
    public String toString() {
        return "Page{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", parentId='" + parentId + '\'' +
                ", subPages=" + subPages +
                ", breadcrumbs=" + breadcrumbs +
                '}';
    }
}