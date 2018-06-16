package com.getjavajob.training.web1803.dao;

import com.getjavajob.training.web1803.dao.exceptions.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RelationshipDAO {
    private static final String INSERT_NEW_RELATIONSHIP = "INSERT INTO relationship (user_one_id, user_two_id, status) VALUES (?, ?, ?)";
    private static final String REMOVE_FRIEND = "DELETE FROM relationship WHERE user_one_id = ? AND user_two_id = ?";
    private static final String SELECT_FRIENDS = "SELECT user_one_id AS id FROM relationship WHERE (user_one_id = ? OR user_two_id = ?) " +
            "AND status = 1 UNION SELECT user_two_id FROM relationship WHERE (user_one_id = ? OR user_two_id = ?) AND status = 1";

    private Connection connection;
    private ConnectionPool connectionPool;

    public RelationshipDAO() throws DaoException {
        connectionPool = ConnectionPool.getPool();
        connection = connectionPool.getConnection();
    }

    // Constructor for tests
    public RelationshipDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean addFriend(int idFrom, int idTo) throws DaoException {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(INSERT_NEW_RELATIONSHIP)) {
            preparedStatement.setInt(1, idFrom);
            preparedStatement.setInt(2, idTo);
            preparedStatement.setInt(3, 1);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean removeFriend(int idFrom, int idTo) throws DaoException {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(REMOVE_FRIEND)) {
            preparedStatement.setInt(1, idFrom);
            preparedStatement.setInt(2, idTo);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<Integer> getFriendsIdList(int id) throws DaoException {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(SELECT_FRIENDS)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, id);
            preparedStatement.setInt(3, id);
            preparedStatement.setInt(4, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Integer> friends = new ArrayList<>();
            while (resultSet.next()) {
                int friendId = resultSet.getInt("id");
                if (friendId != id) {
                    friends.add(friendId);
                }
            }
            return friends;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public void closeConnection() {
        connectionPool.returnConnection(connection);
    }
}