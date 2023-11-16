package org.example.dao.impl;

import org.example.connection.DbConnection;
import org.example.dao.CatDao;
import org.example.exception.CatNotFoundException;
import org.example.exception.ConnectionException;
import org.example.exception.EntityNotFoundException;
import org.example.exception.SqlExecuteException;
import org.example.model.entity.Cat;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CatDaoImpl implements CatDao {

    private final Optional<Connection> connection;

    public CatDaoImpl() {
        this.connection = new DbConnection().getConnection();
    }

    @Override
    public Optional<Cat> getById(Long id) {
        if (connection.isPresent()) {
            String sql = "SELECT id, name, breed, color, age FROM cat WHERE id = ?";
            try (var preparedStatement = connection.get().prepareStatement(sql)) {
                preparedStatement.setLong(1, id);
                var resultSet = preparedStatement.executeQuery();
                var list = getAllFromResultSet(resultSet);
                return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
            } catch (SQLException ex) {
                throw new SqlExecuteException(ex);
            }
        }
        throw new ConnectionException();
    }

    @Override
    public List<Cat> getAll() {
        if (connection.isPresent()) {
            String sql = "SELECT id, name, breed, color, age FROM cat";
            try (var preparedStatement = connection.get().prepareStatement(sql)) {
                var resultSet = preparedStatement.executeQuery();
                return getAllFromResultSet(resultSet);
            } catch (SQLException ex) {
                throw new SqlExecuteException(ex);
            }
        }
        throw new ConnectionException();
    }

    @Override
    public Cat create(Cat cat) {
        if (connection.isPresent()) {
            String sql = "INSERT INTO cat (name, breed, color, age) VALUES (?,?,?)";
            try (PreparedStatement ps = connection.get().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, cat.getName());
                ps.setString(2, cat.getBreed());
                ps.setString(3, cat.getColor());
                ps.setInt(4, cat.getAge());
                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return getById(rs.getLong(1)).get();
                }
            } catch (SQLException ex) {
                throw new EntityNotFoundException();
            }
        }
        throw new ConnectionException();
    }

    @Override
    public Cat update(Cat cat) {
        if (connection.isPresent()) {
            String sql = "UPDATE cat SET name = ?, breed = ?, color = ?, age = ? WHERE id = ?";
            Optional<Cat> exists = getById(cat.getId());
            if (exists.isPresent()) {
                try (var preparedStatement = connection.get().prepareStatement(sql)) {
                    preparedStatement.setString(1, cat.getName());
                    preparedStatement.setString(2, cat.getBreed());
                    preparedStatement.setString(3, cat.getColor());
                    preparedStatement.setInt(4, cat.getAge());
                    preparedStatement.setLong(5, cat.getId());
                    preparedStatement.executeUpdate();
                } catch (SQLException ex) {
                    throw new SqlExecuteException(ex);
                }
            } else {
                throw new CatNotFoundException(cat.getId());
            }
            return getById(cat.getId()).orElseThrow(
                    () -> new CatNotFoundException(cat.getId())
            );
        }
        throw new ConnectionException();
    }

    @Override
    public void delete(Long id) {
        if (connection.isPresent()) {
            String sql = "DELETE FROM cat WHERE id = ?";
            try (var preparedStatement = connection.get().prepareStatement(sql)) {
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();
            } catch (SQLException ex) {
                throw new SqlExecuteException(ex);
            }
        } else {
            throw new ConnectionException();
        }
    }

    private List<Cat> getAllFromResultSet(ResultSet resultSet) {
        try {
            List<Cat> catList = new ArrayList<>();
            while (resultSet.next()) {
                var cat = Cat
                        .builder()
                        .id(resultSet.getLong("id"))
                        .name(resultSet.getString("name"))
                        .breed(resultSet.getString("breed"))
                        .color(resultSet.getString("color"))
                        .age(resultSet.getInt("age"))
                        .build();
                catList.add(cat);
            }
            return catList;
        } catch (SQLException ignored) {
        }
        return List.of();
    }
}
