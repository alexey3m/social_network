package com.getjavajob.training.web1803.service;

import com.getjavajob.training.web1803.common.Group;
import com.getjavajob.training.web1803.common.enums.GroupRole;
import com.getjavajob.training.web1803.common.enums.GroupStatus;
import com.getjavajob.training.web1803.dao.GroupDAO;
import com.getjavajob.training.web1803.dao.exceptions.DaoNameException;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class GroupService {
    private GroupDAO groupDAO;

    @Autowired
    public GroupService(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

    public boolean create(String name, InputStream photo, String photoFileName, String createDate, String info,
                          int userCreatorId) throws DaoNameException {
        return groupDAO.create(name, photo, photoFileName, createDate, info, userCreatorId);
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

    public List<Group> getAllById(int userId) {
        return groupDAO.getAllById(userId);
    }

    public GroupRole getRoleMemberInGroup(int groupId, int memberId) {
        return groupDAO.getRoleMemberInGroup(groupId, memberId);
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
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean remove(int id) {
        groupDAO.remove(id);
        return true;
    }
}