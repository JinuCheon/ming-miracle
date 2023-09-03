package com.wanted.mingmiracle.page.repository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.wanted.mingmiracle.page.domain.Page;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PageRepository {
    @Autowired
    HikariDataSource dataSource;

    public Optional<Page> findById(String id) {
        Page result = Page.builder().id(id).build();
        final String sql = "SELECT \"title\", \"content\", \"parentId\" FROM PAGE WHERE \"id\" = ?";
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
            ) {
            ps.setString(1, id);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String title = rs.getString("title");
                String content = rs.getString("content");
                String parentId = rs.getString("parentId");
                result = Page.builder().id(id).parentId(parentId).title(title).content(content).build();
            }
            rs.close();
        } catch (SQLException e) {
            log.error("페이지 정보 조회 에러 발생!!", e);
        }
        return Optional.of(result);
    }

    public Optional<List<String[]>> findByParentId(String parentId) {
        List<String[]> result = new ArrayList<>();
        final String sql = "SELECT \"id\", \"title\" FROM PAGE WHERE \"parentId\" = ?";
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
            ) {
            ps.setString(1, parentId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                String title = rs.getString("title");
                result.add(new String[] {id, title});
            }
            rs.close();
        } catch (SQLException e) {
            log.error("하위 페이지 조회 에러 발생!!", e);
        }
        return Optional.of(result);
    }
}
