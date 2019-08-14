package com.ershov.socialnet.service.test;

import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RelationshipServiceTest {

//    @Mock
//    private RelationshipDAO relationshipDAO;
//    @Mock
//    private AccountDAO accountDAO;
//
//    @InjectMocks
//    private RelationshipService phoneService;
//
//    @Test
//    public void addQueryFriendTest() {
//        when(relationshipDAO.createQueryFriend(new Relationship(1, 2, Status.PENDING, 1))).thenReturn(true);
//        assertTrue(phoneService.addQueryFriend(1, 2));
//    }
//
//    @Test
//    public void acceptFriendTest() {
//        when(relationshipDAO.updateQueryFriend(new Relationship(1, 2, Status.ACCEPTED, 1))).thenReturn(true);
//        assertTrue(phoneService.acceptFriend(1, 2));
//    }
//
//    @Test
//    public void declineFriendTest() {
//        when(relationshipDAO.updateQueryFriend(new Relationship(1, 2, Status.DECLINE, 1))).thenReturn(true);
//        assertTrue(phoneService.declineFriend(1, 2));
//    }
//
//    @Test
//    public void removeFriendTest() {
//        when(relationshipDAO.removeFriend(new Relationship(1, 2, null, 0))).thenReturn(true);
//        assertTrue(phoneService.removeFriend(1, 2));
//    }
//
//    @Test
//    public void getStatusTest() {
//        when(relationshipDAO.getStatus(new Relationship(1, 2, null, 0))).thenReturn(Status.ACCEPTED);
//        assertEquals(Status.ACCEPTED, phoneService.getStatus(1, 2));
//    }
//
//    @Test
//    public void getPendingRequestToMeTest() {
//        when(relationshipDAO.getPendingRequestToMe(new Relationship(1, 2, null, 1))).thenReturn(Status.PENDING);
//        assertEquals(Status.PENDING, phoneService.getPendingRequestToMe(1, 2));
//    }
//
//    @Test
//    public void getAcceptedFriendsListTest() {
//        List<Integer> friendsId = new ArrayList<>();
//        friendsId.add(2);
//        friendsId.add(3);
//        Account account2 = new Account(2, "b@b.ru", "123", "Sergey", "Semenov",
//                null, "1990-01-01", null, "bbbbb", "0",
//                "2018-06-13", Role.ROLE_USER, new ArrayList<>());
//        Account account3 = new Account(3, "c@c.ru", "123", "Ivan", "Ivanov",
//                "Ivanovich", "1970-05-29", null, "ccccc", "12345",
//                "2018-06-13", Role.ROLE_USER, new ArrayList<>());
//        List<Account> expected = new ArrayList<>();
//        expected.add(account2);
//        expected.add(account3);
//        when(relationshipDAO.getFriendsIdList(1)).thenReturn(friendsId);
//        when(accountDAO.get(2)).thenReturn(account2);
//        when(accountDAO.get(3)).thenReturn(account3);
//        assertEquals(expected, phoneService.getAcceptedFriendsList(1));
//    }
//
//    @Test
//    public void getPendingRequestsToIdTest() {
//        List<Integer> friendsId = new ArrayList<>();
//        List<Account> expected = new ArrayList<>();
//        when(relationshipDAO.getPendingRequestToId(1)).thenReturn(friendsId);
//        assertEquals(expected, phoneService.getPendingRequestsToId(1));
//    }
//
//    @Test
//    public void getFriendRequestsFromIdTest() {
//        List<Integer> friendsId = new ArrayList<>();
//        List<Account> expected = new ArrayList<>();
//        when(relationshipDAO.getFriendRequestsFromId(1)).thenReturn(friendsId);
//        assertEquals(expected, phoneService.getFriendRequestsFromId(1));
//    }
}