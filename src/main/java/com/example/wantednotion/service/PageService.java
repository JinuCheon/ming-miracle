package com.example.wantednotion.service;

import com.example.wantednotion.domain.Page;
import com.example.wantednotion.domain.SubPage;
import com.example.wantednotion.dto.PageResponseDto;
import com.example.wantednotion.repository.PageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PageService {

    private final PageRepository pageRepository;
    private final Map<String, Page> map = new HashMap<>();

    public void loadPageToMap() {
        List<Page> pages = pageRepository.findAll();
        for (Page page : pages) {
            map.put(page.getId(), page);
        }
    }

    // Service Bean 등록시 먼저 실행해 map 초기화(캐싱기능)
    @PostConstruct
    public void init() {
        loadPageToMap();
    }

    public PageResponseDto findPageById(String id) {
        Page page = map.getOrDefault(id, null);
        if(page == null) {
            throw new RuntimeException("아이디로 조회 실패");
        }

        List<SubPage> subPages = pageRepository.findAllByParentId(id);
        Deque<SubPage> breadcrumbs =getBreadcrumbs(id);

        return new PageResponseDto(page, subPages, breadcrumbs);
    }

    public Deque<SubPage> getBreadcrumbs(String id) {
        Deque<SubPage> deque = new LinkedList<>();

        while(id != null) {
            Page page = map.getOrDefault(id, null);
            if(page != null) {
                deque.push(new SubPage(page.getId(), page.getTitle()));
                id = page.getParentId();
            } else {
                break;
            }
        }
        return deque;
    }

}// class
