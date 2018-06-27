package com.getjavajob.training.web1803.service.test;

public class GroupServiceTest {

//    private GroupDAO groupDAO = mock(GroupDAO.class);
//
//    @InjectMocks
//    private GroupService groupService = new GroupService(groupDAO);
//
//    @Test
//    public void createTest() throws DaoException, DaoNameException {
//        when(groupDAO.create("Group", null, null, "2018-06-07", "Info 1", 1)).thenReturn(true);
//        assertTrue(groupService.create("Group", null, null, "2018-06-07", "Info 1", 1));
//    }
//
//    @Test
//    public void getTest() throws DaoException {
//        List<Integer> acceptedMembersId1 = new ArrayList<>();
//        acceptedMembersId1.add(1);
//        acceptedMembersId1.add(3);
//        List<Integer> pendingMembersId1 = new ArrayList<>();
//        List<Integer> adminsId1 = new ArrayList<>();
//        adminsId1.add(1);
//        Group group = new Group(1, "Group 1", null, null, "2018-06-07",
//                "Info 1", 1, acceptedMembersId1, pendingMembersId1, adminsId1);
//        when(groupDAO.get(1)).thenReturn(group);
//        assertEquals(group, groupService.get(1));
//    }
//
//    @Test
//    public void getAllTest() throws DaoException {
//        List<Integer> acceptedMembersId1 = new ArrayList<>();
//        acceptedMembersId1.add(1);
//        acceptedMembersId1.add(3);
//        List<Integer> pendingMembersId1 = new ArrayList<>();
//        List<Integer> adminsId1 = new ArrayList<>();
//        adminsId1.add(1);
//        Group group1 = new Group(1, "Group 1", null, null, "2018-06-07",
//                "Info 1", 1, acceptedMembersId1, pendingMembersId1, adminsId1);
//        List<Integer> acceptedMembersId2 = new ArrayList<>();
//        acceptedMembersId2.add(2);
//        List<Integer> pendingMembersId2 = new ArrayList<>();
//        List<Integer> adminsId2 = new ArrayList<>();
//        adminsId2.add(2);
//        Group group2 = new Group(2, "Group 2", null, null, "2018-06-09",
//                "Info 2", 2, acceptedMembersId2, pendingMembersId2, adminsId2);
//        List<Group> expected = new ArrayList<>();
//        expected.add(group1);
//        expected.add(group2);
//        when(groupDAO.getAll()).thenReturn(expected);
//        assertEquals(expected, groupService.getAll());
//    }
//
//    @Test
//    public void searchByStringTest() throws DaoException {
//        List<Integer> acceptedMembersId1 = new ArrayList<>();
//        acceptedMembersId1.add(1);
//        acceptedMembersId1.add(3);
//        List<Integer> pendingMembersId1 = new ArrayList<>();
//        List<Integer> adminsId1 = new ArrayList<>();
//        adminsId1.add(1);
//        Group group1 = new Group(1, "Group 1", null, null, "2018-06-07",
//                "Info 1", 1, acceptedMembersId1, pendingMembersId1, adminsId1);
//        List<Group> expected = new ArrayList<>();
//        expected.add(group1);
//        when(groupDAO.searchByString("1")).thenReturn(expected);
//        assertEquals(expected, groupService.searchByString("1"));
//    }
//
//    @Test
//    public void getAllById() throws DaoException {
//        List<Integer> acceptedMembersId1 = new ArrayList<>();
//        acceptedMembersId1.add(1);
//        acceptedMembersId1.add(3);
//        List<Integer> pendingMembersId1 = new ArrayList<>();
//        List<Integer> adminsId1 = new ArrayList<>();
//        adminsId1.add(1);
//        Group group1 = new Group(1, "Group 1", null, null, "2018-06-07",
//                "Info 1", 1, acceptedMembersId1, pendingMembersId1, adminsId1);
//        List<Group> expected = new ArrayList<>();
//        expected.add(group1);
//        when(groupDAO.getAllById(1)).thenReturn(expected);
//        assertEquals(expected, groupService.getAllById(1));
//    }
//
//    @Test
//    public void getRoleMemberInGroupTest() throws DaoException {
//        when(groupDAO.getRoleMemberInGroup(1, 1)).thenReturn(GroupRole.ADMIN);
//        assertEquals(GroupRole.ADMIN, groupService.getRoleMemberInGroup(1, 1));
//    }
//
//    @Test
//    public void getStatusMemberInGroupTest() throws DaoException {
//        when(groupDAO.getStatusMemberInGroup(1, 1)).thenReturn(GroupStatus.ACCEPTED);
//        assertEquals(GroupStatus.ACCEPTED, groupService.getStatusMemberInGroup(1, 1));
//    }
//
//    @Test
//    public void addPendingMemberToGroupTest() throws DaoException {
//        when(groupDAO.addPendingMemberToGroup(1, 1)).thenReturn(true);
//        assertTrue(groupService.addPendingMemberToGroup(1, 1));
//    }
//
//    @Test
//    public void setRoleMemberInGroupTest() throws DaoException {
//        when(groupDAO.setRoleMemberInGroup(1, 1, GroupRole.USER)).thenReturn(true);
//        assertTrue(groupService.setRoleMemberInGroup(1, 1, GroupRole.USER));
//        when(groupDAO.getRoleMemberInGroup(1, 1)).thenReturn(GroupRole.USER);
//        assertEquals(GroupRole.USER, groupService.getRoleMemberInGroup(1, 1));
//    }
//
//    @Test
//    public void removeMemberFromGroupTest() throws DaoException {
//        when(groupDAO.removeMemberFromGroup(1, 1)).thenReturn(true);
//        assertTrue(groupService.removeMemberFromGroup(1, 1));
//    }
//
//    @Test
//    public void getIdTest() throws DaoException {
//        when(groupDAO.getId("Group 1")).thenReturn(1);
//        assertEquals(1, groupService.getId("Group 1"));
//    }
//
//    @Test
//    public void updateTest() throws DaoException {
//        Group group = new Group(1, "Group 1", null, null, null,
//                "new info", 0, null, null, null);
//        when(groupDAO.update(group)).thenReturn(true);
//        when(groupDAO.getId("Group 1")).thenReturn(1);
//        assertTrue(groupService.update("Group 1", null, null, "new info"));
//    }
//
//    @Test
//    public void removeTest() throws DaoException {
//        when(groupDAO.remove(1)).thenReturn(true);
//        assertTrue(groupService.remove(1));
//    }
}