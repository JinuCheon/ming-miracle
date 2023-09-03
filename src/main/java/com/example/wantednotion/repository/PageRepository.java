package com.example.wantednotion.repository;

import com.example.wantednotion.domain.Page;
import com.example.wantednotion.domain.SubPage;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PageRepository {
    Optional<Page> findById(String id);
    List<SubPage> findAllByParentId(String id);
}
