package test.ming;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Component
public class Repository {
    public Page findById(String pageId) {
        String sql = "SELECT * FROM PAGE WHERE id = ?";

        try (Connection connection = DataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, pageId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new Page(resultSet.getString("id"), resultSet.getString("title"), resultSet.getString("content"), resultSet.getString("parentId"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public PageDto.InfoParent findPageInfoById(String pageId) {
        String sql = "SELECT id,title,parentId FROM PAGE WHERE id = ?";

        try (Connection connection = DataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, pageId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new PageDto.InfoParent(resultSet.getString("id"), resultSet.getString("title"), resultSet.getString("parentId"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<PageDto.Info> findAllByParentId(String parentId) {
        String sql = "SELECT id,title FROM PAGE WHERE parentId = ?";

        List<PageDto.Info> resultList = new ArrayList<>();
        try (Connection connection = DataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, parentId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    resultList.add(new PageDto.Info(resultSet.getString("id"), resultSet.getString("title")));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultList;
    }
}
