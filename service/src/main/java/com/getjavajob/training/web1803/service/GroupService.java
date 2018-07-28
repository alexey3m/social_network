package com.getjavajob.training.web1803.service;

import com.getjavajob.training.web1803.common.Group;
import com.getjavajob.training.web1803.common.enums.GroupRole;
import com.getjavajob.training.web1803.common.enums.GroupStatus;
import com.getjavajob.training.web1803.dao.GroupDAO;
import com.getjavajob.training.web1803.dao.exceptions.DaoNameException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {
    private GroupDAO groupDAO;

    @Autowired
    public GroupService(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

    public boolean create(Group group) throws DaoNameException {
        return groupDAO.create(group);
    }

    public Group get(int groupId) {
        return groupDAO.get(groupId);
    }

    public List<Group> getAll() {
        return groupDAO.getAll();
    }

    public List<Group> searchByString(String search) {
        return groupDAO.searchByString(search);
    }

    public List<Group> getAllByUserId(int userId) {
        return groupDAO.getAllByUserId(userId);
    }

    public GroupRole getRoleMemberInGroup(int groupId, int memberId) {
        return groupDAO.getRoleMemberInGroup(groupId, memberId);
    }

    public byte[] getPhoto(int id) {
        byte[] photo = groupDAO.getPhoto(id);
        if (photo != null) {
            return photo.length == 0 ? null : photo;
        } else {
            return null;
        }
    }

    public GroupStatus getStatusMemberInGroup(int groupId, int memberId) {
        return groupDAO.getStatusMemberInGroup(groupId, memberId);
    }

    public boolean addPendingMemberToGroup(int idGroup, int idNewMember) {
        return groupDAO.addPendingMemberToGroup(idGroup, idNewMember);
    }

    public boolean setStatusMemberInGroup(int idGroup, int idMember, GroupStatus status) {
        return groupDAO.setStatusMemberInGroup(idGroup, idMember, status);
    }

    public boolean setRoleMemberInGroup(int idGroup, int idMember, GroupRole role) {
        return groupDAO.setRoleMemberInGroup(idGroup, idMember, role);
    }

    public boolean removeMemberFromGroup(int idGroup, int idMemberToDelete) {
        return groupDAO.removeMemberFromGroup(idGroup, idMemberToDelete);
    }

    public int getId(String name) {
        return groupDAO.getId(name);
    }

    public boolean update(Group group) {
        return groupDAO.update(group);
    }

    public boolean remove(int id) {
        return groupDAO.remove(id);
    }
}