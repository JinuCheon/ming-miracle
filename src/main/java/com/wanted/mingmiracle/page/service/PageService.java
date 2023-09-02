package com.wanted.mingmiracle.page.service;

import com.wanted.mingmiracle.page.domain.Page;
import com.wanted.mingmiracle.page.dto.PageResponseDTO;
import com.wanted.mingmiracle.page.repository.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PageService {

    @Autowired
    PageRepository pageRepository;

    public PageResponseDTO getPageInfo(String id) {
        PageResponseDTO response;
        Page targetPage = pageRepository.findById(id).orElseThrow();
        List<String[]> subPages = pageRepository.findByParentId(id).orElse(null);
        List<String[]> breadcrumbs = new ArrayList<>();
        String parentId = targetPage.getParentId();
        Page parentPage = pageRepository.findById(parentId).orElseThrow();
        String parentTitle = parentPage.getTitle();

        while (parentId != null) {
            breadcrumbs.add(new String[] {parentId, parentTitle});
            parentPage = pageRepository.findById(parentId).orElseThrow();
            parentId = parentPage.getParentId();
            parentTitle = parentPage.getTitle();
        }

        response = new PageResponseDTO(targetPage, subPages, breadcrumbs);
        return response;
    }
}
