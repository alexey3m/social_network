package com.getjavajob.training.web1803.service.test;

import com.getjavajob.training.web1803.common.Group;
import com.getjavajob.training.web1803.common.enums.GroupRole;
import com.getjavajob.training.web1803.common.enums.GroupStatus;
import com.getjavajob.training.web1803.dao.GroupDAO;
import com.getjavajob.training.web1803.dao.exceptions.DaoNameException;
import com.getjavajob.training.web1803.service.GroupService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GroupServiceTest {

    @Mock
    private GroupDAO groupDAO;

    @InjectMocks
    private GroupService groupService;

    @Test
    public void createTest() throws DaoNameException {
        Group group = new Group(0, "Group 3", null, "2018-06-13",
                "Info 3", 2, null, null, null);
        when(groupDAO.create(group)).thenReturn(true);
        assertTrue(groupService.create(group));
    }

    @Test
    public void getTest() {
        List<Integer> acceptedMembersId1 = new ArrayList<>();
        acceptedMembersId1.add(1);
        acceptedMembersId1.add(3);
        List<Integer> pendingMembersId1 = new ArrayList<>();
        List<Integer> adminsId1 = new ArrayList<>();
        adminsId1.add(1);
        Group group = new Group(1, "Group 1", null, "2018-06-07",
                "Info 1", 1, acceptedMembersId1, pendingMembersId1, adminsId1);
        when(groupDAO.get(1)).thenReturn(group);
        assertEquals(group, groupService.get(1));
    }

    @Test
    public void getAllTest() {
        List<Integer> acceptedMembersId1 = new ArrayList<>();
        acceptedMembersId1.add(1);
        acceptedMembersId1.add(3);
        List<Integer> pendingMembersId1 = new ArrayList<>();
        List<Integer> adminsId1 = new ArrayList<>();
        adminsId1.add(1);
        Group group1 = new Group(1, "Group 1", null, "2018-06-07",
                "Info 1", 1, acceptedMembersId1, pendingMembersId1, adminsId1);
        List<Integer> acceptedMembersId2 = new ArrayList<>();
        acceptedMembersId2.add(2);
        List<Integer> pendingMembersId2 = new ArrayList<>();
        List<Integer> adminsId2 = new ArrayList<>();
        adminsId2.add(2);
        Group group2 = new Group(2, "Group 2", null, "2018-06-09",
                "Info 2", 2, acceptedMembersId2, pendingMembersId2, adminsId2);
        List<Group> expected = new ArrayList<>();
        expected.add(group1);
        expected.add(group2);
        when(groupDAO.getAll()).thenReturn(expected);
        assertEquals(expected, groupService.getAll());
    }

    @Test
    public void searchByStringTest() {
        List<Integer> acceptedMembersId1 = new ArrayList<>();
        acceptedMembersId1.add(1);
        acceptedMembersId1.add(3);
        List<Integer> pendingMembersId1 = new ArrayList<>();
        List<Integer> adminsId1 = new ArrayList<>();
        adminsId1.add(1);
        Group group1 = new Group(1, "Group 1", null, "2018-06-07",
                "Info 1", 1, acceptedMembersId1, pendingMembersId1, adminsId1);
        List<Group> expected = new ArrayList<>();
        expected.add(group1);
        when(groupDAO.searchByString("1")).thenReturn(expected);
        assertEquals(expected, groupService.searchByString("1"));
    }

    @Test
    public void getAllById() {
        List<Integer> acceptedMembersId1 = new ArrayList<>();
        acceptedMembersId1.add(1);
        acceptedMembersId1.add(3);
        List<Integer> pendingMembersId1 = new ArrayList<>();
        List<Integer> adminsId1 = new ArrayList<>();
        adminsId1.add(1);
        Group group1 = new Group(1, "Group 1", null, "2018-06-07",
                "Info 1", 1, acceptedMembersId1, pendingMembersId1, adminsId1);
        List<Group> expected = new ArrayList<>();
        expected.add(group1);
        when(groupDAO.getAllById(1)).thenReturn(expected);
        assertEquals(expected, groupService.getAllById(1));
    }

    @Test
    public void getRoleMemberInGroupTest() {
        when(groupDAO.getRoleMemberInGroup(1, 1)).thenReturn(GroupRole.ADMIN);
        assertEquals(GroupRole.ADMIN, groupService.getRoleMemberInGroup(1, 1));
    }

    @Test
    public void getStatusMemberInGroupTest() {
        when(groupDAO.getStatusMemberInGroup(1, 1)).thenReturn(GroupStatus.ACCEPTED);
        assertEquals(GroupStatus.ACCEPTED, groupService.getStatusMemberInGroup(1, 1));
    }

    @Test
    public void addPendingMemberToGroupTest() {
        when(groupDAO.addPendingMemberToGroup(1, 1)).thenReturn(true);
        assertTrue(groupService.addPendingMemberToGroup(1, 1));
    }

    @Test
    public void setRoleMemberInGroupTest() {
        when(groupDAO.setRoleMemberInGroup(1, 1, GroupRole.USER)).thenReturn(true);
        assertTrue(groupService.setRoleMemberInGroup(1, 1, GroupRole.USER));
        when(groupDAO.getRoleMemberInGroup(1, 1)).thenReturn(GroupRole.USER);
        assertEquals(GroupRole.USER, groupService.getRoleMemberInGroup(1, 1));
    }

    @Test
    public void removeMemberFromGroupTest() {
        when(groupDAO.removeMemberFromGroup(1, 1)).thenReturn(true);
        assertTrue(groupService.removeMemberFromGroup(1, 1));
    }

    @Test
    public void getIdTest() {
        when(groupDAO.getId("Group 1")).thenReturn(1);
        assertEquals(1, groupService.getId("Group 1"));
    }

    @Test
    public void updateTest() {
        Group group = new Group(1, "Group 1", null, null, "new info", 0,
                null, null, null);
        when(groupDAO.update(group)).thenReturn(true);
        assertTrue(groupService.update(group));
    }

    @Test
    public void removeTest() {
        when(groupDAO.remove(1)).thenReturn(true);
        assertTrue(groupService.remove(1));
    }
}