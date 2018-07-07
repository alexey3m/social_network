package com.getjavajob.training.web1803.dao;

import com.getjavajob.training.web1803.common.Group;
import com.getjavajob.training.web1803.common.enums.GroupRole;
import com.getjavajob.training.web1803.common.enums.GroupStatus;
import com.getjavajob.training.web1803.dao.exceptions.DaoNameException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GroupDAO {
    private static final String SELECT_ALL_GROUPS = "SELECT * FROM soc_group";
    private static final String SELECT_GROUP_BY_NAME = SELECT_ALL_GROUPS + " WHERE name = ?";
    private static final String INSERT_GROUP = "INSERT INTO soc_group (name, photo, photo_file_name, create_date, " +
            "info, user_creator_id) VALUES(?, ?, ?, ?, ?, ?)";
    private static final String INSERT_ACCOUNT_IN_GROUP = "INSERT INTO account_in_group (group_id, user_member_id, role, status) " +
            "VALUES (?, ?, ?, ?)";
    private static final String SELECT_GROUPS_ID_BY_NAME = "SELECT group_id FROM soc_group WHERE name = ?";
    private static final String SELECT_GROUPS_ID_BY_USER_MEMBER_ID = "SELECT * FROM soc_group WHERE group_id IN " +
            "(SELECT group_id FROM account_in_group WHERE user_member_id = ? AND status = 2)";
    private static final String SELECT_ROLE_MEMBER = "SELECT role FROM account_in_group WHERE group_id = ? AND user_member_id = ?";
    private static final String SELECT_STATUS_MEMBER = "SELECT status FROM account_in_group WHERE group_id = ? AND user_member_id = ?";
    private static final String COLUMN_GROUP_ID = "group_id";
    private static final String SELECT_GROUP_BY_ID = SELECT_ALL_GROUPS + " WHERE group_id = ?";
    private static final String SELECT_USER_MEMBER_ID_FROM_ACCOUNT_IN_GROUP = "SELECT * FROM account_in_group WHERE group_id = ?";
    private static final String UPDATE_GROUP = "UPDATE soc_group SET name = ?, photo = ?, photo_file_name = ?, info = ? WHERE group_id = ?";
    private static final String UPDATE_STATUS_MEMBER = "UPDATE account_in_group SET status = ? WHERE group_id = ? AND user_member_id = ?";
    private static final String UPDATE_ROLE_MEMBER = "UPDATE account_in_group SET role = ? WHERE group_id = ? AND user_member_id = ?";
    private static final String REMOVE_ACCOUNT_IN_GROUP = "DELETE FROM account_in_group WHERE user_member_id = ? AND group_id = ?";
    private static final String SELECT_LAST_INSERT_ID = "SELECT LAST_INSERT_ID() AS id";
    private static final String REMOVE_GROUP = "DELETE FROM soc_group WHERE group_id = ?";
    private static final String SEARCH_GROUPS_BY_STRING = "SELECT * FROM soc_group WHERE LOWER(name) LIKE ?";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public GroupDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public boolean create(String name, InputStream photo, String photoFileName, String createDate, String info, int userCreatorId)
            throws DaoNameException {
        int nameExists = this.jdbcTemplate.query(SELECT_GROUP_BY_NAME, new Object[]{name}, rs -> rs.next() ? 1 : 0);
        if (nameExists == 0) {
            this.jdbcTemplate.update(INSERT_GROUP, name, photo, photoFileName, createDate, info, userCreatorId);
            int newGroupId = this.jdbcTemplate.queryForObject(SELECT_LAST_INSERT_ID, (rs, rowNum) -> rs.getInt("id"));
            this.jdbcTemplate.update(INSERT_ACCOUNT_IN_GROUP, newGroupId, userCreatorId, GroupRole.ADMIN.getStatus(),
                    GroupStatus.ACCEPTED.getStatus());
            return true;
        } else {
            throw new DaoNameException("Group name \"" + name + "\" is already used.");
        }
    }

    public Group get(int groupId) {
        List<Group> groups = this.jdbcTemplate.query(SELECT_GROUP_BY_ID, new Object[]{groupId},
                (rs, rowNum) -> createGroupFromResult(rs));
        if (groups.size() == 0) {
            return null;
        } else {
            return this.jdbcTemplate.query(SELECT_USER_MEMBER_ID_FROM_ACCOUNT_IN_GROUP, new Object[]{groupId}, rs -> {
                return createGroupMembersFromResult(groups.get(0), rs);
            });
        }

    }

    public List<Group> getAll() {
        List<Group> groups = this.jdbcTemplate.query(SELECT_ALL_GROUPS, (rs, rowNum) -> createGroupFromResult(rs));
        List<Group> result = new ArrayList<>();
        for (Group group : groups) {
            result.add(this.jdbcTemplate.query(SELECT_USER_MEMBER_ID_FROM_ACCOUNT_IN_GROUP, new Object[]{group.getId()},
                    rs -> {
                        return createGroupMembersFromResult(group, rs);
                    }));
        }
        return result;
    }

    public List<Group> getAllById(int userId) {
        List<Group> groups = this.jdbcTemplate.query(SELECT_GROUPS_ID_BY_USER_MEMBER_ID, new Object[]{userId},
                (rs, rowNum) -> createGroupFromResult(rs));
        List<Group> result = new ArrayList<>();
        for (Group group : groups) {
            result.add(this.jdbcTemplate.query(SELECT_USER_MEMBER_ID_FROM_ACCOUNT_IN_GROUP, new Object[]{group.getId()},
                    rs -> {
                        return createGroupMembersFromResult(group, rs);
                    }));
        }
        return result;
    }

    public List<Group> searchByString(String search) {
        List<Group> groups = this.jdbcTemplate.query(SEARCH_GROUPS_BY_STRING, new Object[]{"%" + search.toLowerCase() + "%"},
                (rs, rowNum) -> createGroupFromResult(rs));
        List<Group> result = new ArrayList<>();
        for (Group group : groups) {
            result.add(this.jdbcTemplate.query(SELECT_USER_MEMBER_ID_FROM_ACCOUNT_IN_GROUP, new Object[]{group.getId()},
                    rs -> {
                        return createGroupMembersFromResult(group, rs);
                    }));
        }
        return result;
    }

    public int getId(String name) {
        return this.jdbcTemplate.queryForObject(SELECT_GROUPS_ID_BY_NAME, new Object[]{name},
                (rs, rowNum) -> rs.getInt(COLUMN_GROUP_ID));
    }

    public GroupRole getRoleMemberInGroup(int groupId, int memberId) {
        int result = this.jdbcTemplate.query(SELECT_ROLE_MEMBER, new Object[]{groupId, memberId},
                rs -> rs.next() ? rs.getInt("role") : 0);
        return result != 0 ? GroupRole.values()[result] : GroupRole.UNKNOWN;
    }

    public GroupStatus getStatusMemberInGroup(int groupId, int memberId) {
        int result = this.jdbcTemplate.query(SELECT_STATUS_MEMBER, new Object[]{groupId, memberId},
                rs -> rs.next() ? rs.getInt("status") : 0);
        return result != 0 ? GroupStatus.values()[result] : GroupStatus.UNKNOWN;
    }

    @Transactional
    public boolean update(Group group) {
        int result = this.jdbcTemplate.update(UPDATE_GROUP, group.getName(), group.getPhoto(), group.getPhotoFileName(),
                group.getInfo(), group.getId());
        return result != 0;
    }

    @Transactional
    public boolean addPendingMemberToGroup(int idGroup, int idNewMember) {
        int result = this.jdbcTemplate.update(INSERT_ACCOUNT_IN_GROUP, idGroup, idNewMember, GroupRole.USER.getStatus(),
                GroupStatus.PENDING.getStatus());
        return result != 0;
    }

    @Transactional
    public boolean setStatusMemberInGroup(int idGroup, int member, GroupStatus status) {
        int result = this.jdbcTemplate.update(UPDATE_STATUS_MEMBER, status.getStatus(), idGroup, member);
        return result != 0;
    }

    @Transactional
    public boolean setRoleMemberInGroup(int idGroup, int member, GroupRole role) {
        int result = this.jdbcTemplate.update(UPDATE_ROLE_MEMBER, role.getStatus(), idGroup, member);
        return result != 0;
    }

    @Transactional
    public boolean removeMemberFromGroup(int idGroup, int idMemberToDelete) {
        int result = this.jdbcTemplate.update(REMOVE_ACCOUNT_IN_GROUP, idMemberToDelete, idGroup);
        return result != 0;
    }

    @Transactional
    public boolean remove(int idGroup) {
        int result = this.jdbcTemplate.update(REMOVE_GROUP, idGroup);
        return result != 0;
    }

    private Group createGroupFromResult(ResultSet resultSetGroup) throws SQLException {
        Group group = new Group();
        group.setId(resultSetGroup.getInt(COLUMN_GROUP_ID));
        group.setName(resultSetGroup.getString("name"));
        group.setPhoto(resultSetGroup.getBytes("photo"));
        group.setPhotoFileName(resultSetGroup.getString("photo_file_name"));
        group.setCreateDate(resultSetGroup.getString("create_date"));
        group.setInfo(resultSetGroup.getString("info"));
        group.setUserCreatorId(resultSetGroup.getInt("user_creator_id"));
        return group;
    }

    private Group createGroupMembersFromResult(Group group, ResultSet resultSetMembers) throws SQLException {
        if (group == null) {
            return null;
        }
        List<Integer> acceptedMembersId = new ArrayList<>();
        List<Integer> pendingMembersId = new ArrayList<>();
        List<Integer> adminsId = new ArrayList<>();
        while (resultSetMembers.next()) {
            int memberId = resultSetMembers.getInt("user_member_id");
            int role = resultSetMembers.getInt("role");
            int status = resultSetMembers.getInt("status");
            if (status == GroupStatus.ACCEPTED.getStatus()) {
                acceptedMembersId.add(memberId);
            } else if (status == GroupStatus.PENDING.getStatus()) {
                pendingMembersId.add(memberId);
            }
            if (role == GroupRole.ADMIN.getStatus()) {
                adminsId.add(memberId);
            }
        }
        group.setAcceptedMembersId(acceptedMembersId);
        group.setPendingMembersId(pendingMembersId);
        group.setAdminsId(adminsId);
        return group;
    }
}