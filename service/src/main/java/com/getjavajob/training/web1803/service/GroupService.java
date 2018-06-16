package com.getjavajob.training.web1803.service;

import com.getjavajob.training.web1803.common.*;
import com.getjavajob.training.web1803.dao.GroupDAO;
import com.getjavajob.training.web1803.dao.exceptions.DaoException;
import com.getjavajob.training.web1803.dao.exceptions.DaoNameException;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

public class GroupService {
    private GroupDAO groupDAO;

    public GroupService() {
        try {
            groupDAO = new GroupDAO();
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    //Constructor for tests
    public GroupService(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }


    public boolean create(String name, InputStream photo, String photoFileName, String createDate, String info, int userCreatorId) throws DaoNameException {
        try {
            return groupDAO.create(name, photo, photoFileName, createDate, info, userCreatorId);
        } catch (DaoException e) {
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

    public List<Group> getAllById(int userId) {
        try {
            return groupDAO.getAllById(userId);
        } catch (DaoException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public GroupRole getRoleMemberInGroup(int groupId, int memberId) {
        try {
            return groupDAO.getRoleMemberInGroup(groupId, memberId);
        } catch (DaoException e) {
            e.printStackTrace();
            return null;
        }
    }

    public GroupStatus getStatusMemberInGroup(int groupId, int memberId) {
        try {
            return groupDAO.getStatusMemberInGroup(groupId, memberId);
        } catch (DaoException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean addPendingMemberToGroup(int idGroup, int idNewMember) {
        try {
            return groupDAO.addPendingMemberToGroup(idGroup, idNewMember);
        } catch (DaoException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean setStatusMemberInGroup(int idGroup, int idMember, GroupStatus status) {
        try {
            return groupDAO.setStatusMemberInGroup(idGroup, idMember, status);
        } catch (DaoException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean setRoleMemberInGroup(int idGroup, int idMember, GroupRole role) {
        try {
            return groupDAO.setRoleMemberInGroup(idGroup, idMember, role);
        } catch (DaoException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeMemberFromGroup(int idGroup, int idMemberToDelete) {
        try {
            return groupDAO.removeMemberFromGroup(idGroup, idMemberToDelete);
        } catch (DaoException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getId(String name) {
        try {
            return groupDAO.getId(name);
        } catch (DaoException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public boolean update(String name, InputStream photo, String photoFileName, String info, List<Integer> acceptedMembersId,
                          List<Integer> pendingMembersId, List<Integer> adminsId) {
        try {
            int id = groupDAO.getId(name);

            Group group = new Group();
            group.setId(id);
            group.setName(name);
            group.setPhoto(IOUtils.toByteArray(photo));
            group.setPhotoFileName(photoFileName);
            group.setInfo(info);
            group.setAcceptedMembersId(acceptedMembersId);
            group.setPendingMembersId(pendingMembersId);
            group.setAdminsId(adminsId);
            return groupDAO.update(group);
        } catch (DaoException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean remove(int id) {
        try {
            return groupDAO.remove(id);
        } catch (DaoException e) {
            e.printStackTrace();
            return false;
        }
    }
}