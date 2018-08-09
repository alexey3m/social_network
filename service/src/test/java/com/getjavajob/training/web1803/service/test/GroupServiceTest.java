package com.getjavajob.training.web1803.service.test;

//import com.getjavajob.training.web1803.common.AccountInGroup;
//import com.getjavajob.training.web1803.common.Group;
//import com.getjavajob.training.web1803.common.enums.GroupRole;
//import com.getjavajob.training.web1803.common.enums.GroupStatus;
//import com.getjavajob.training.web1803.dao.GroupRepository;
//import com.getjavajob.training.web1803.dao.exceptions.DaoNameException;
//import com.getjavajob.training.web1803.service.GroupService;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static junit.framework.TestCase.assertTrue;
//import static org.junit.Assert.assertEquals;
//
//@RunWith(MockitoJUnitRunner.class)
public class GroupServiceTest {

//    @Mock
//    private GroupRepository repository;
//
//    @InjectMocks
//    private GroupService groupService;
//
//    @Test
//    public void createTest() throws DaoNameException {
//        List<AccountInGroup> accounts = new ArrayList<>();
//        accounts.add(new AccountInGroup(1, 1, GroupRole.ADMIN, GroupStatus.ACCEPTED));
//        Group group = new Group(0, "Group 3", null, "2018-06-13",
//                "Info 3", 2, accounts);
//        assertTrue(groupService.create(group));
//    }
//
//    @Test
//    public void getTest() {
//        List<AccountInGroup> accounts = new ArrayList<>();
//        accounts.add(new AccountInGroup(1, 1, GroupRole.ADMIN, GroupStatus.ACCEPTED));
//        accounts.add(new AccountInGroup(2, 3, GroupRole.USER, GroupStatus.ACCEPTED));
//        Group group = new Group(1, "Group 1", null, "2018-06-07",
//                "Info 1", 1, accounts);
//        assertEquals(group, groupService.get(1));
//    }
//
//    @Test
//    public void getAllTest() {
//        List<AccountInGroup> accounts1 = new ArrayList<>();
//        accounts1.add(new AccountInGroup(1, 1, GroupRole.ADMIN, GroupStatus.ACCEPTED));
//        accounts1.add(new AccountInGroup(2, 3, GroupRole.USER, GroupStatus.ACCEPTED));
//        Group group1 = new Group(1, "Group 1", null, "2018-06-07",
//                "Info 1", 1, accounts1);
//        List<AccountInGroup> accounts2 = new ArrayList<>();
//        accounts2.add(new AccountInGroup(1, 2, GroupRole.ADMIN, GroupStatus.ACCEPTED));
//        Group group2 = new Group(2, "Group 2", null, "2018-06-09",
//                "Info 2", 2, accounts2);
//        List<Group> expected = new ArrayList<>();
//        expected.add(group1);
//        expected.add(group2);
//        assertEquals(expected, groupService.getAll());
//    }
//
//    @Test
//    public void searchByStringTest() {
//        List<AccountInGroup> accounts = new ArrayList<>();
//        accounts.add(new AccountInGroup(1, 1, GroupRole.ADMIN, GroupStatus.ACCEPTED));
//        accounts.add(new AccountInGroup(2, 3, GroupRole.USER, GroupStatus.ACCEPTED));
//        Group group = new Group(1, "Group 1", null, "2018-06-07",
//                "Info 1", 1, accounts);
//        List<Group> expected = new ArrayList<>();
//        expected.add(group);
//        assertEquals(expected, groupService.searchByString("1", 1));
//    }
//
//    @Test
//    public void getAllByUserId() {
//        List<AccountInGroup> accounts = new ArrayList<>();
//        accounts.add(new AccountInGroup(1, 1, GroupRole.ADMIN, GroupStatus.ACCEPTED));
//        accounts.add(new AccountInGroup(2, 3, GroupRole.USER, GroupStatus.ACCEPTED));
//        Group group = new Group(1, "Group 1", null, "2018-06-07",
//                "Info 1", 1, accounts);
//        List<Group> expected = new ArrayList<>();
//        expected.add(group);
//        assertEquals(expected, groupService.getAllByUserId(1));
//    }
//
//    @Test
//    public void getRoleMemberInGroupTest() {
//        assertEquals(GroupRole.ADMIN, groupService.getRoleMemberInGroup(1, 1));
//    }
//
//    @Test
//    public void getStatusMemberInGroupTest() {
//        assertEquals(GroupStatus.ACCEPTED, groupService.getStatusMemberInGroup(1, 1));
//    }
//
//    @Test
//    public void addPendingMemberToGroupTest() {
//        assertTrue(groupService.addPendingMemberToGroup(1, 1));
//    }
//
//    @Test
//    public void setRoleMemberInGroupTest() {
//        assertTrue(groupService.setRoleMemberInGroup(1, 1, GroupRole.USER));
//        assertEquals(GroupRole.USER, groupService.getRoleMemberInGroup(1, 1));
//    }
//
//    @Test
//    public void removeMemberFromGroupTest() {
//        assertTrue(groupService.removeMemberFromGroup(1, 1));
//    }
//
//    @Test
//    public void getIdTest() {
//        assertEquals(1, groupService.getId("Group 1"));
//    }
//
//    @Test
//    public void updateTest() {
//        Group group = new Group(1, "Group 1", null, null, "new info", 0, null);
//        assertTrue(groupService.update(group));
//    }
//
//    @Test
//    public void removeTest() {
//        assertTrue(groupService.remove(1));
//    }
}