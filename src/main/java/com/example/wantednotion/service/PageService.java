package com.example.wantednotion.service;

import com.example.wantednotion.domain.Page;
import com.example.wantednotion.domain.SubPage;
import com.example.wantednotion.dto.PageResponseDto;
import com.example.wantednotion.repository.PageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PageService {

    private final PageRepository pageRepository;

    public PageResponseDto findPageById(String id) {
        Page page = pageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("아이디로 조회 실패"));

        List<SubPage> subPages = pageRepository.findAllByParentId(id);
        List<SubPage> breadcrumbs =getBreadcrumbs(id);

        return new PageResponseDto(page, subPages, breadcrumbs);
    }

    public List<SubPage> getBreadcrumbs(String id) {
        List<SubPage> breadcrumbs = new ArrayList<>();

        while(id != null) {
            Page page = pageRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("아이디로 조회 실패"));
            if(page != null) {
                breadcrumbs.add(0, new SubPage(page.getId(), page.getTitle()));
                id = page.getParentId();
            } else {
                break;
            }
        }
        return breadcrumbs;
    }

}// class
