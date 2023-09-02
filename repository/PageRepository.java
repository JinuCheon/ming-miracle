package repository;

import model.Page;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.h2.jdbcx.JdbcConnectionPool;

public class PageRepository {

    private final JdbcConnectionPool connectionPool;

    public PageRepository(JdbcConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    // 특정 페이지 정보 조회
    public Page getPageInfo(String pageId) {
        try (Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(
                        "SELECT * FROM PAGE WHERE id = ?")) {
            ps.setString(1, pageId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Page page = new Page();
                page.setId(rs.getString("id"));
                page.setTitle(rs.getString("title"));
                page.setContent(rs.getString("content"));

                List<Page> subPages = getSubPages(connection, pageId);
                page.setSubPages(subPages);

                List<Page> breadcrumbs = getBreadcrumbs(connection, pageId);
                page.setBreadcrumbs(breadcrumbs);

                return page;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // 서브 페이지 정보 조회
    private List<Page> getSubPages(Connection connection, String parentId) {
        List<Page> subPages = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT * FROM PAGE WHERE parentId = ?")) {
            ps.setString(1, parentId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Page subPage = new Page();
                subPage.setId(rs.getString("id"));
                subPage.setTitle(rs.getString("title"));

                subPages.add(subPage);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subPages;
    }

    // BreadCrumbs 정보 조회
    private List<Page> getBreadcrumbs(Connection connection, String pageId) {
        List<Page> breadcrumbs = new ArrayList<>();
        try {
            while (pageId != null) {
                try (PreparedStatement ps = connection.prepareStatement(
                        "SELECT * FROM PAGE WHERE id = ?")) {
                    ps.setString(1, pageId);
                    ResultSet rs = ps.executeQuery();

                    if (rs.next()) {
                        Page breadcrumb = new Page();
                        breadcrumb.setId(rs.getString("id"));
                        breadcrumb.setTitle(rs.getString("title"));

                        breadcrumbs.add(breadcrumb);
                        pageId = rs.getString("parentId");
                    } else {
                        break;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return breadcrumbs;
    }

    // 모든 페이지 조회
    public List<Page> getAllPages() {
        List<Page> allPages = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement("SELECT * FROM PAGE");
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Page page = new Page();
                page.setId(rs.getString("id"));
                page.setTitle(rs.getString("title"));
                page.setContent(rs.getString("content"));
                allPages.add(page);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allPages;
    }
}