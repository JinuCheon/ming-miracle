package org.example.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.example.domain.Page;
import org.example.domain.PageRepository;

public class JdbcPageRepository implements PageRepository {

    @Override
    public Optional<Page> findById(final String id) {
        final String sql = "SELECT * FROM PAGE WHERE id = ?";
        final ParameterSetter parameterSetter = statement -> statement.setString(1, id);
        final ResultMapper<Optional<Page>> resultMapper = resultSet -> {
            if (resultSet.next()) {
                return Optional.of(mapToEntity(resultSet));
            }
            return Optional.empty();
        };

        return executeQuery(sql, parameterSetter, resultMapper);
    }

    @Override
    public List<Page> findAllByParentId(final String parentId) {
        final String sql = "SELECT * FROM PAGE WHERE parentId = ?";
        final ParameterSetter parameterSetter = statement -> statement.setString(1, parentId);
        final ResultMapper<List<Page>> resultMapper = resultSet -> {
            final List<Page> results = new ArrayList<>();
            while (resultSet.next()) {
                results.add(mapToEntity(resultSet));
            }
            return results;
        };

        return executeQuery(sql, parameterSetter, resultMapper);
    }

    private Page mapToEntity(final ResultSet resultSet) throws SQLException {
        return new Page(
                resultSet.getString("id"),
                resultSet.getString("title"),
                resultSet.getString("content"),
                resultSet.getString("parentId"));
    }

    private <T> T executeQuery(final String sql,
                               final ParameterSetter parameterSetter,
                               final ResultMapper<T> resultMapper) {
        try (
                final Connection connection = DataSource.getConnection();
                final PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            parameterSetter.apply(statement);
            final ResultSet resultSet = statement.executeQuery();
            final T result = resultMapper.apply(resultSet);
            resultSet.close();
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("SQL을 실행하지 못하였습니다.", e);
        }
    }

    @FunctionalInterface
    private interface ParameterSetter {
        void apply(final PreparedStatement statement) throws SQLException;
    }

    @FunctionalInterface
    private interface ResultMapper<T> {
        T apply(final ResultSet resultSet) throws SQLException;
    }
}
