package com.getjavajob.training.web1803.service.test;

import com.getjavajob.training.web1803.common.Account;
import com.getjavajob.training.web1803.common.enums.Role;
import com.getjavajob.training.web1803.common.enums.Status;
import com.getjavajob.training.web1803.dao.AccountDAO;
import com.getjavajob.training.web1803.dao.Pool;
import com.getjavajob.training.web1803.dao.RelationshipDAO;
import com.getjavajob.training.web1803.dao.exceptions.DaoException;
import com.getjavajob.training.web1803.service.RelationshipService;
import org.junit.Test;
import org.mockito.InjectMocks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RelationshipServiceTest {
    private RelationshipDAO relationshipDAO = mock(RelationshipDAO.class);
    private AccountDAO accountDAO = mock(AccountDAO.class);
    private Pool connectionPool = mock(Pool.class);

    @InjectMocks
    private RelationshipService phoneService = new RelationshipService(relationshipDAO, accountDAO, connectionPool);

    @Test
    public void addQueryFriendTest() throws DaoException {
        when(relationshipDAO.createQueryFriend(1, 2, 1)).thenReturn(true);
        assertTrue(phoneService.addQueryFriend(1, 2));
    }

    @Test
    public void acceptFriendTest() throws DaoException {
        when(relationshipDAO.updateQueryFriend(1, 2, 1, 1)).thenReturn(true);
        assertTrue(phoneService.acceptFriend(1, 2));
    }

    @Test
    public void declineFriendTest() throws DaoException {
        when(relationshipDAO.updateQueryFriend(1, 2, 2, 1)).thenReturn(true);
        assertTrue(phoneService.declineFriend(1, 2));
    }

    @Test
    public void removeFriendTest() throws DaoException {
        when(relationshipDAO.removeFriend(1, 2)).thenReturn(true);
        assertTrue(phoneService.removeFriend(1, 2));
    }

    @Test
    public void getStatusTest() throws DaoException {
        when(relationshipDAO.getStatus(1, 2)).thenReturn(Status.ACCEPTED);
        assertEquals(Status.ACCEPTED, phoneService.getStatus(1, 2));
    }

    @Test
    public void getPendingRequestToMeTest() throws DaoException {
        when(relationshipDAO.getPendingRequestToMe(1, 2, 1)).thenReturn(Status.PENDING);
        assertEquals(Status.PENDING, phoneService.getPendingRequestToMe(1, 2));
    }

    @Test
    public void getAcceptedFriendsListTest() throws DaoException {
        List<Integer> friendsId = new ArrayList<>();
        friendsId.add(2);
        friendsId.add(3);
        Account account2 = new Account(2, "b@b.ru", "123", "Sergey", "Semenov",
                null, "1990-01-01", null, null, "bbbbb", 0,
                "2018-06-13", Role.USER, new HashMap<>());
        Account account3 = new Account(3, "c@c.ru", "123", "Ivan", "Ivanov",
                "Ivanovich", "1970-05-29", null, null, "ccccc", 12345,
                "2018-06-13", Role.USER, new HashMap<>());
        List<Account> expected = new ArrayList<>();
        expected.add(account2);
        expected.add(account3);
        when(relationshipDAO.getFriendsIdList(1)).thenReturn(friendsId);
        when(accountDAO.get(2)).thenReturn(account2);
        when(accountDAO.get(3)).thenReturn(account3);
        assertEquals(expected, phoneService.getAcceptedFriendsList(1));
    }

    @Test
    public void getPendingRequestsToIdTest() throws DaoException {
        List<Integer> friendsId = new ArrayList<>();
        List<Account> expected = new ArrayList<>();
        when(relationshipDAO.getPendingRequestToId(1)).thenReturn(friendsId);
        assertEquals(expected, phoneService.getPendingRequestsToId(1));
    }

    @Test
    public void getFriendRequestsFromIdTest() throws DaoException {
        List<Integer> friendsId = new ArrayList<>();
        List<Account> expected = new ArrayList<>();
        when(relationshipDAO.getFriendRequestsFromId(1)).thenReturn(friendsId);
        assertEquals(expected, phoneService.getFriendRequestsFromId(1));
    }
}