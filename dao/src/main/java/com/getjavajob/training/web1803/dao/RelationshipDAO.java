package com.getjavajob.training.web1803.dao;

import com.getjavajob.training.web1803.common.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RelationshipDAO {
    private static final String CREATE_QUERY_FRIEND = "INSERT INTO relationship (user_one_id, user_two_id, status, action_user_id) " +
            "VALUES (?, ?, ?, ?)";
    private static final String UPDATE_QUERY_FRIEND = "UPDATE relationship SET status = ?, action_user_id = ? " +
            "WHERE user_one_id = ? AND user_two_id = ?";
    private static final String REMOVE_FRIEND = "DELETE FROM relationship WHERE user_one_id = ? AND user_two_id = ?";
    private static final String SELECT_STATUS = "SELECT status FROM relationship WHERE user_one_id = ? AND user_two_id = ?";
    private static final String SELECT_STATUS_TO_ME = "SELECT status FROM relationship WHERE user_one_id = ? AND user_two_id = ? " +
            "AND action_user_id = ?";
    private static final String GET_FRIENDS = "SELECT user_one_id AS id FROM relationship WHERE (user_one_id = ? OR user_two_id = ?) " +
            "AND status = ? " +
            "UNION " +
            "SELECT user_two_id FROM relationship WHERE (user_one_id = ? OR user_two_id = ?) AND status = ?";
    private static final String GET_PENDING_REQUEST_TO_ID = "SELECT user_one_id AS id FROM relationship WHERE status = ? " +
            "AND action_user_id <> ? AND (user_one_id = ? OR user_two_id = ?) " +
            "UNION " +
            "SELECT user_two_id FROM relationship WHERE status = ? AND action_user_id <> ? AND (user_one_id = ? OR user_two_id = ?)";
    private static final String GET_REQUESTS_FROM_ID = "SELECT user_one_id AS id FROM relationship WHERE user_two_id = ? " +
            "AND status = ? AND action_user_id = ? " +
            "UNION " +
            "SELECT user_two_id FROM relationship WHERE user_one_id = ? AND status = ? AND action_user_id = ?";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public RelationshipDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public boolean createQueryFriend(int idFrom, int idTo, int actionId) {
        int result = this.jdbcTemplate.update(CREATE_QUERY_FRIEND, idFrom, idTo, Status.PENDING.getStatus(), actionId);
        return result != 0;
    }

    @Transactional
    public boolean updateQueryFriend(int idFrom, int idTo, int status, int actionId) {
        int result = this.jdbcTemplate.update(UPDATE_QUERY_FRIEND, status, actionId, idFrom, idTo);
        return result != 0;
    }

    @Transactional
    public boolean removeFriend(int idFrom, int idTo) {
        int result = this.jdbcTemplate.update(REMOVE_FRIEND, idFrom, idTo);
        return result != 0;
    }

    public Status getStatus(int idFrom, int idTo) {
        return this.jdbcTemplate.query(SELECT_STATUS, new Object[]{idFrom, idTo},
                rs -> rs.next() ? Status.values()[rs.getInt("status") + 1] : Status.UNKNOWN);
    }

    public Status getPendingRequestToMe(int idFrom, int idTo, int actionId) {
        return this.jdbcTemplate.query(SELECT_STATUS_TO_ME, new Object[]{idFrom, idTo, actionId},
                rs -> rs.next() ? Status.values()[rs.getInt("status") + 1] : Status.UNKNOWN);
    }

    public List<Integer> getFriendsIdList(int id) {
        return this.jdbcTemplate.query(GET_FRIENDS, new Object[]{id, id, Status.ACCEPTED.getStatus(), id, id,
                Status.ACCEPTED.getStatus()}, rs -> {
            List<Integer> result = new ArrayList<>();
            while (rs.next()) {
                int friendId = rs.getInt("id");
                if (friendId != id) {
                    result.add(friendId);
                }
            }
            return result;
        });
    }

    public List<Integer> getPendingRequestToId(int id) {
        return this.jdbcTemplate.query(GET_PENDING_REQUEST_TO_ID, new Object[]{Status.PENDING.getStatus(), id, id, id,
                Status.PENDING.getStatus(), id, id, id}, rs -> {
            List<Integer> result = new ArrayList<>();
            while (rs.next()) {
                int friendId = rs.getInt("id");
                if (friendId != id) {
                    result.add(friendId);
                }
            }
            return result;
        });
    }

    public List<Integer> getFriendRequestsFromId(int id) {
        return this.jdbcTemplate.query(GET_REQUESTS_FROM_ID,
                new Object[]{id, Status.PENDING.getStatus(), id, id, Status.PENDING.getStatus(), id}, rs -> {
                    List<Integer> result = new ArrayList<>();
                    while (rs.next()) {
                        int friendId = rs.getInt("id");
                        if (friendId != id) {
                            result.add(friendId);
                        }
                    }
                    return result;
                });
    }
}