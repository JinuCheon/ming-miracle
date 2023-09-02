package service;

import model.Page;
import java.util.List;
import repository.PageRepository;

public class PageService {

    private final PageRepository pageRepository;

    public PageService(PageRepository pageRepository) {
        this.pageRepository = pageRepository;
    }

    public Page getPageInfo(String pageId) {
        return pageRepository.getPageInfo(pageId);
    }

    public List<Page> getAllPages() {
        return pageRepository.getAllPages();
    }

}