package com.getjavajob.training.web1803.service;

import com.getjavajob.training.web1803.common.Group;
import com.getjavajob.training.web1803.common.enums.GroupRole;
import com.getjavajob.training.web1803.common.enums.GroupStatus;
import com.getjavajob.training.web1803.dao.ConnectionPool;
import com.getjavajob.training.web1803.dao.GroupDAO;
import com.getjavajob.training.web1803.dao.Pool;
import com.getjavajob.training.web1803.dao.exceptions.DaoException;
import com.getjavajob.training.web1803.dao.exceptions.DaoNameException;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

public class GroupService {
    private GroupDAO groupDAO;
    private Pool connectionPool;


    public GroupService() {
        connectionPool = ConnectionPool.getPool();
        groupDAO = GroupDAO.getInstance();
    }

    //Constructor for tests
    public GroupService(GroupDAO groupDAO, Pool connectionPool) {
        this.groupDAO = groupDAO;
        this.connectionPool = connectionPool;
    }

    public boolean create(String name, InputStream photo, String photoFileName, String createDate, String info, int userCreatorId) throws DaoNameException {
        try {
            groupDAO.create(name, photo, photoFileName, createDate, info, userCreatorId);
            connectionPool.commit();
            return true;
        } catch (DaoException e) {
            connectionPool.rollback();
            e.printStackTrace();
            return false;
        }
    }

    public Group get(int groupId) {
        try {
            return groupDAO.get(groupId);
        } catch (DaoException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Group> getAll() {
        try {
            return groupDAO.getAll();
        } catch (DaoException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<Group> searchByString(String search) {
        try {
            return groupDAO.searchByString(search);
        } catch (DaoException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<Group> getAllById(int userId) {
        try {
            return groupDAO.getAllById(userId);
        } catch (DaoException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public GroupRole getRoleMemberInGroup(int groupId, int memberId) {
        return groupDAO.getRoleMemberInGroup(groupId, memberId);
    }

    public GroupStatus getStatusMemberInGroup(int groupId, int memberId) {
        return groupDAO.getStatusMemberInGroup(groupId, memberId);
    }

    public boolean addPendingMemberToGroup(int idGroup, int idNewMember) {
        try {
            groupDAO.addPendingMemberToGroup(idGroup, idNewMember);
            connectionPool.commit();
            return true;
        } catch (DaoException e) {
            connectionPool.rollback();
            e.printStackTrace();
            return false;
        }
    }

    public boolean setStatusMemberInGroup(int idGroup, int idMember, GroupStatus status) {
        try {
            groupDAO.setStatusMemberInGroup(idGroup, idMember, status);
            connectionPool.commit();
            return true;
        } catch (DaoException e) {
            connectionPool.rollback();
            e.printStackTrace();
            return false;
        }
    }

    public boolean setRoleMemberInGroup(int idGroup, int idMember, GroupRole role) {
        try {
            groupDAO.setRoleMemberInGroup(idGroup, idMember, role);
            connectionPool.commit();
            return true;
        } catch (DaoException e) {
            connectionPool.rollback();
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeMemberFromGroup(int idGroup, int idMemberToDelete) {
        try {
            groupDAO.removeMemberFromGroup(idGroup, idMemberToDelete);
            connectionPool.commit();
            return true;
        } catch (DaoException e) {
            connectionPool.rollback();
            e.printStackTrace();
            return false;
        }
    }

    public int getId(String name) {
        return groupDAO.getId(name);
    }

    public boolean update(String name, InputStream photo, String photoFileName, String info) {
        try {
            int id = groupDAO.getId(name);
            Group group = new Group();
            group.setId(id);
            group.setName(name);
            group.setPhoto(photo != null ? IOUtils.toByteArray(photo) : null);
            group.setPhotoFileName(photoFileName);
            group.setInfo(info);
            groupDAO.update(group);
            connectionPool.commit();
            return true;
        } catch (DaoException | IOException e) {
            connectionPool.rollback();
            e.printStackTrace();
            return false;
        }
    }

    public boolean remove(int id) {
        try {
            groupDAO.remove(id);
            connectionPool.commit();
            return true;
        } catch (DaoException e) {
            connectionPool.rollback();
            e.printStackTrace();
            return false;
        }
    }

    public void closeService() {
        connectionPool.returnConnection();
    }
}