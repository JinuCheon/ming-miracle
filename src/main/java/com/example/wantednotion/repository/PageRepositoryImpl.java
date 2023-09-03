package com.example.wantednotion.repository;

import com.example.wantednotion.domain.Page;
import com.example.wantednotion.domain.SubPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
@RequiredArgsConstructor
public class PageRepositoryImpl implements PageRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Page> findById(String id) {
        try {
            String sql = "SELECT * FROM PAGE WHERE id = ?";
            List<Page> results = jdbcTemplate.query(sql, new Object[]{id}, pageRowMapper());

            return results.isEmpty() ? Optional.empty() : Optional.ofNullable(results.get(0));
        } catch (Exception e) {
            log.error("Error fetching page by ID", e);
            throw e;
        }
    }

    @Override
    public List<SubPage> findAllByParentId(String id) {
        try {
//            String sql1 = "SELECT COUNT(*) FROM PAGE";
//            Integer count = jdbcTemplate.queryForObject(sql1, Integer.class);
//            System.out.println("2 Number of rows: " + count);

            String sql = "SELECT * FROM PAGE WHERE parentId = ?";
            return jdbcTemplate.query(sql, new Object[]{id}, subPageRowMapper());
        } catch (Exception e) {
            log.error("Error fetching page by ID", e);
            throw e;
        }

    }

    private RowMapper<Page> pageRowMapper() {
        return (rs, rowNum) -> {
            System.out.println("Mapping row for Page: " + rowNum);
            Page page = new Page();
            page.setId(rs.getString("id"));
            page.setTitle(rs.getString("title"));
            page.setContent(rs.getString("content"));
            page.setParentId(rs.getString("parentId"));
            return page;
        };
    }

    private RowMapper<SubPage> subPageRowMapper() {
        return (rs, rowNum) -> {
            System.out.println("Mapping row for SubPage: " + rowNum);
            SubPage subPage = new SubPage();
            subPage.setId(rs.getString("id"));
            subPage.setTitle(rs.getString("title"));
            return subPage;
        };
    }
}
