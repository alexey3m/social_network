package com.getjavajob.training.web1803.dao;

import com.getjavajob.training.web1803.common.Status;
import com.getjavajob.training.web1803.dao.exceptions.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RelationshipDAO {
    private static final String CREATE_QUERY_FRIEND = "INSERT INTO relationship (user_one_id, user_two_id, status, action_user_id) " +
            "VALUES (?, ?, ?, ?)";
    private static final String UPDATE_QUERY_FRIEND = "UPDATE relationship SET status = ?, action_user_id = ? " +
            "WHERE user_one_id = ? AND user_two_id = ?";
    private static final String REMOVE_FRIEND = "DELETE FROM relationship WHERE user_one_id = ? AND user_two_id = ?";
    private static final String SELECT_STATUS = "SELECT status FROM relationship WHERE user_one_id = ? AND user_two_id = ?";
    private static final String SELECT_STATUS_TO_ME = "SELECT status FROM relationship WHERE user_one_id = ? AND user_two_id = ? AND action_user_id = ?";
    private static final String GET_FRIENDS = "SELECT user_one_id AS id FROM relationship WHERE (user_one_id = ? OR user_two_id = ?) AND status = ? " +
            "UNION " +
            "SELECT user_two_id FROM relationship WHERE (user_one_id = ? OR user_two_id = ?) AND status = ?";
    private static final String GET_PENDING_REQUEST_TO_ID = "SELECT user_one_id AS id FROM relationship WHERE status = ? AND action_user_id <> ? AND (user_one_id = ? OR user_two_id = ?) " +
            "UNION " +
            "SELECT user_two_id FROM relationship WHERE status = ? AND action_user_id <> ? AND (user_one_id = ? OR user_two_id = ?)";
    private static final String GET_REQUESTS_FROM_ID = "SELECT user_one_id AS id FROM relationship WHERE user_two_id = ? AND status = ? AND action_user_id = ? " +
            "UNION " +
            "SELECT user_two_id FROM relationship WHERE user_one_id = ? AND status = ? AND action_user_id = ?";
    private Connection connection;
    private Pool connectionPool;

    public RelationshipDAO() throws DaoException {
        connectionPool = ConnectionPool.getPool();
    }

    // Constructor for tests
    public RelationshipDAO(Pool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public boolean createQueryFriend(int idFrom, int idTo, int actionId) throws DaoException {
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(CREATE_QUERY_FRIEND)) {
            preparedStatement.setInt(1, idFrom);
            preparedStatement.setInt(2, idTo);
            preparedStatement.setInt(3, Status.PENDING.getStatus());
            preparedStatement.setInt(4, actionId);
            preparedStatement.executeUpdate();
//            this.connection.commit();
            return true;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    public boolean updateQueryFriend(int idFrom, int idTo, int status, int actionId) throws DaoException {
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(UPDATE_QUERY_FRIEND)) {
            preparedStatement.setInt(1, status);
            preparedStatement.setInt(2, actionId);
            preparedStatement.setInt(3, idFrom);
            preparedStatement.setInt(4, idTo);
            preparedStatement.executeUpdate();
//            this.connection.commit();
            return true;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    public boolean removeFriend(int idFrom, int idTo) throws DaoException {
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(REMOVE_FRIEND)) {
            preparedStatement.setInt(1, idFrom);
            preparedStatement.setInt(2, idTo);
            preparedStatement.executeUpdate();
//            this.connection.commit();
            return true;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    public Status getStatus(int idFrom, int idTo) throws DaoException {
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(SELECT_STATUS)) {
            preparedStatement.setInt(1, idFrom);
            preparedStatement.setInt(2, idTo);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int status = resultSet.getInt("status");
                    return status > 2 ? Status.DECLINE : Status.values()[status + 1];
                }
            }
            return Status.UNKNOWN;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    public Status getPendingRequestToMe(int idFrom, int idTo, int actionId) throws DaoException {
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(SELECT_STATUS_TO_ME)) {
            preparedStatement.setInt(1, idFrom);
            preparedStatement.setInt(2, idTo);
            preparedStatement.setInt(3, actionId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int status = resultSet.getInt("status");
                    return status > 2 ? Status.DECLINE : Status.values()[status + 1];
                }
            }
            return Status.UNKNOWN;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    public List<Integer> getFriendsIdList(int id) throws DaoException {
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(GET_FRIENDS)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, id);
            preparedStatement.setInt(3, Status.ACCEPTED.getStatus());
            preparedStatement.setInt(4, id);
            preparedStatement.setInt(5, id);
            preparedStatement.setInt(6, Status.ACCEPTED.getStatus());
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
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    public List<Integer> getPendingRequestToId(int id) throws DaoException {
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(GET_PENDING_REQUEST_TO_ID)) {
            preparedStatement.setInt(1, Status.PENDING.getStatus());
            preparedStatement.setInt(2, id);
            preparedStatement.setInt(3, id);
            preparedStatement.setInt(4, id);
            preparedStatement.setInt(5, Status.PENDING.getStatus());
            preparedStatement.setInt(6, id);
            preparedStatement.setInt(7, id);
            preparedStatement.setInt(8, id);
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
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    public List<Integer> getFriendRequestsFromId(int id) throws DaoException {
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(GET_REQUESTS_FROM_ID)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, Status.PENDING.getStatus());
            preparedStatement.setInt(3, id);
            preparedStatement.setInt(4, id);
            preparedStatement.setInt(5, Status.PENDING.getStatus());
            preparedStatement.setInt(6, id);
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
        } finally {
            connectionPool.returnConnection(connection);
        }
    }
}