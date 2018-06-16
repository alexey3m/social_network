import com.getjavajob.training.web1803.dao.AccountDAO;
import com.getjavajob.training.web1803.dao.RelationshipDAO;

import static org.mockito.Mockito.mock;

public class RelationshipServiceTest {

    private RelationshipDAO relationshipDAO = mock(RelationshipDAO.class);
    private AccountDAO accountDAO = mock(AccountDAO.class);
//
//    @InjectMocks
//    private RelationshipService relationshipService = new RelationshipService(relationshipDAO, accountDAO);
//
//    @Test
//    public void addFriendTest() throws DaoException {
//        when(relationshipDAO.addFriend(1, 4)).thenReturn(true);
//        assertTrue(relationshipService.addFriend(1, 4));
//        assertTrue(relationshipService.addFriend(4, 1));
//    }
//
//    @Test
//    public void removeFriendTest() throws DaoException {
//        when(relationshipDAO.removeFriend(1, 4)).thenReturn(true);
//        assertTrue(relationshipService.removeFriend(1, 4));
//        assertTrue(relationshipService.removeFriend(4, 1));
//    }
//
//    @Test
//    public void getFriendsListTest() throws DaoException {
//        List<Integer> friends = new ArrayList<>();
//        friends.add(2);
//        friends.add(3);
//        when(relationshipDAO.getFriendsIdList(1)).thenReturn(friends);
//        Account account2 = new Account(2, "ivan1", "1234", "Ivan", "Ivanov", "Alexeyevich",
//                "27.01.1993", "+79230000001", "+739121231212", "Moskow", "Moskow 2",
//                "a2@a.ru", 1234500000, "bbbb", "extra information", null, null);
//        Account account3 = new Account(3, "sergey1", "12345", "Sergey", "Nosov", "Ivanovych",
//                "02.09.1987", "+79230000002", "+739121231212", "Sankt Petersburg", "Sankt Petersburg 2",
//                "a3@a.ru", 1234500001, "ccccc", "extra information", null, null);
//        when(accountDAO.get(2)).thenReturn(account2);
//        when(accountDAO.get(3)).thenReturn(account3);
//        List<Account> expected = new ArrayList<>();
//        expected.add(account2);
//        expected.add(account3);
//
//        assertEquals(expected, relationshipService.getFriendsList(1));
//    }
}